import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Initialises and sorts the boroughs with their listings.
 * @author Cosmo Colman (K21090628)
 * @version 26.03.2022
 */
public class BoroughMap {

    private static ArrayList<Borough> boroughs;
    private static HashMap<Borough, ArrayList<NewAirbnbListing>> boroughListings;

    static{
        ArrayList<NewAirbnbListing> listings = RuntimeDetails.getNewAirbnbListings();

        boroughs = initialiseBoroughs(listings);
        boroughListings = initialiseBoroughArrayList(listings, boroughs);
    }

    public BoroughMap() {

    }

    public static HashMap<Borough, ArrayList<NewAirbnbListing>> getBoroughListings() {
        return boroughListings;
    }

    public static ArrayList<Borough> getBoroughs() {
        return boroughs;
    }

    /**
     * Initialise the Boroughs from all listings. The values in the borough objects are calculated from the properties of the listing.
     * @param allListings The list of all properties.
     * @return The list of all the created Borough objects.
     */
    private static ArrayList<Borough> initialiseBoroughs(ArrayList<NewAirbnbListing> allListings){
        boroughListings = new HashMap<>();

        HashMap<String, ArrayList<Double>> boroughStats = new HashMap<>();

        for (NewAirbnbListing listing : allListings){
            String boroughName = listing.getNeighbourhoodCleansed();

            if (!boroughStats.containsKey(boroughName)) {
                ArrayList<Double> stats = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));  // Average Price, Total Price, Total Count, Total Available (excluding 0 356), Total Available * days available
                boroughStats.put(boroughName, stats);
            }

            ArrayList<Double> stats = boroughStats.get(boroughName);
            stats.set(1, stats.get(1) + listing.getPrice());                // Total Price
            stats.set(2, stats.get(2) + 1);                                 // Total Count
            if(listing.getAvailability365() != 0){
                stats.set(3, stats.get(3) + 1);                             // Total Available (excluding 0 356)
            }
            stats.set(4, stats.get(4) + listing.getAvailability365());      // Total Available * days available
        }

        for (String name : boroughStats.keySet()){
            ArrayList<Double> stats = boroughStats.get(name);
            stats.set(0, (stats.get(1)/stats.get(2)));
        }

        ArrayList<Borough> boroughs = new ArrayList<>();

        for (String name : boroughStats.keySet()){
            ArrayList<Double> stats = boroughStats.get(name);
            int avgPrice = (int)Math.ceil(stats.get(0));
            int count = (int)Math.round(stats.get(2));
            int available = (int)Math.round(stats.get(3));
            int availableDays = (int)Math.round(stats.get(4));

            boroughs.add(new Borough(name, avgPrice, count, available, availableDays));
        }

        return  boroughs;
    }

    /**
     * Initialised which listings belong to what Borough.
     * @param allListings The list of all properties.
     * @param allBoroughs The list of all Boroughs.
     * @return The HashMap which connects the list of properties in a borough to the Borough.
     */
    private static HashMap<Borough, ArrayList<NewAirbnbListing>> initialiseBoroughArrayList(ArrayList<NewAirbnbListing> allListings, ArrayList<Borough> allBoroughs) {
        HashMap<Borough, ArrayList<NewAirbnbListing>> boroughAirbnbs = new HashMap<>();
        HashMap<String,Borough> boroughLookUp = new HashMap<>();

        for (Borough borough : allBoroughs){
            boroughAirbnbs.put(borough, new ArrayList<>());
            boroughLookUp.put(borough.getName(), borough);
        }

        for (NewAirbnbListing listing : allListings){
            boroughAirbnbs.get(boroughLookUp.get(listing.getNeighbourhoodCleansed())).add(listing);
        }

        return boroughAirbnbs;
    }
}
