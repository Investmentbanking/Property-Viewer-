import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Creates the borough and listing related statistics using the new data set for the statistics panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 30.03.2022
 */
public class BoroughAndListingsStatisticsCollector {

    /**
     * Iterates through each listing and finds the latitude and longitude.
     * Uses Pythagorean theorem to find the shortest distance to the
     * London Eye. Checks if the calculated distance is smaller than
     * previous distance, if so, gets the ID and description of the listing.
     *
     * @return the ID and description of the property closest to the London Eye.
     */
    public static String getPropertyClosestLondonEye() {

        double londonEyeLatitude =  51.5033;
        double londonEyeLongitude = 0.1196;
        double smallest = Double.POSITIVE_INFINITY; // assigns the positive infinity of type double to smallest
        String id = "";
        String description = "";

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
           double listingLatitude =  listing.getLatitude();
           double listingLongitude = listing.getLongitude();
           double a = londonEyeLatitude - listingLatitude;
           double b = londonEyeLongitude - listingLongitude;

           double square = Math.pow(a, 2) + Math.pow(b, 2); // finds the square of a and adds to the square of b

           double doubleTotalDistance = Math.sqrt(square);  // finds the square root

           if(doubleTotalDistance < smallest) {
               smallest = doubleTotalDistance;
               id = listing.getId();
               description = listing.getName();
           }
        }
        return "ID: " + id + "\n\n" + "Description: " + description;
    }

    /**
     * Checks the room type for each listing and checks if its equal
     * to 'Hotel', if so, increments the count.
     *
     * @return the total number of Hotel properties.
     */
    public static int getNumberHotelListings() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getPropertyType().equals("Hotel")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if listing is in Central London by checking if it matches any of the
     * specified properties, if so, increments count.
     *
     * @return the total number of listings in central london.
     */
    public static int getListingsCentralLondon() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getNeighbourhoodCleansed().matches("Camden|Islington|Kensington and Chelsea|Lambeth|Southwark|Westminster|City of London")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the borough with the highest average of beds based on its listings by
     * dividing the total number of beds for each borough by the total number of
     * listings in that borough.
     *
     * @return the borough with the highest average of beds.
     */
    public static String getBoroughMostBeds() {
        double mostBeds = 0;
        String borough = "";
        HashMap<String, Integer> listingsBorough = new HashMap<>();
        HashMap<String, Integer> listingsTotalBeds = new HashMap<>();

        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingBeds = listing.getBeds();

            int count  = listingsBorough.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + 1;
            listingsBorough.put(listing.getNeighbourhoodCleansed(), count);

            int total = listingsTotalBeds.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + listingBeds;
            listingsTotalBeds.put(listing.getNeighbourhoodCleansed(), total);
        }

        for (String key : listingsTotalBeds.keySet()) {
            double value1 = listingsBorough.get(key);
            double value2 = listingsTotalBeds.get(key);
            double division = value2 / value1;
            if (division > mostBeds) {
                mostBeds = division;
                borough = key;
            }
        }
        return borough;
    }

    /**
     * First finds the first listing which has the highest number of bathrooms.
     * Then transverses through the list again to find the values that match (if any).
     * Gets the borough of those listings and if it wasn't already in the list,
     * adds it to the list.
     *
     * @return borough(s) which contain a listing with the highest number of bathrooms.
     */
    public static Collection<String> getBoroughsMostBathrooms() {
        String borough = "";
        double highest = 0.0;
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            double listingBathrooms = listing.getBathrooms();
            if(listingBathrooms > highest) {
                highest = listingBathrooms;
                borough = listing.getNeighbourhoodCleansed();
            }
        }
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            double listingBathrooms = listing.getBathrooms();
            if(listingBathrooms == highest) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){
                    list.add(borough);
                }
            }
        }
        return list;
    }

    /**
     * Finds the borough with the highest availability by dividing the total
     * availability of each borough by the number of listings in that borough.
     * If this average is higher than the previous value, gets the borough.
     *
     * @return the borough with the highest average 365 availability.
     */
    public static String getBoroughHighestAvailability() {
        double highestAvailability = 0;
        String borough = "";
        HashMap<String, Integer> listingsBorough = new HashMap<>();
        HashMap<String, Integer> listingsTotalAvailability = new HashMap<>();

        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingAvailability = listing.getAvailability365();

            int listingsCount  = listingsBorough.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + 1;
            listingsBorough.put(listing.getNeighbourhoodCleansed(), listingsCount);

            int availabilityCount = listingsTotalAvailability.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + listingAvailability;
            listingsTotalAvailability.put(listing.getNeighbourhoodCleansed(), availabilityCount);
        }

        for (String key : listingsTotalAvailability.keySet()) {
            int value1 = listingsBorough.get(key);
            int value2 = listingsTotalAvailability.get(key);
            double division = value2 / value1;
            if (division > highestAvailability) {
                highestAvailability = division;
                borough = key;
            }
        }
        return borough;
    }

    /**
     * Iterates through listings and finds the first listing with the
     * highest number of bedrooms and assigns this to highest and gets the ID
     * of that listing. Then, transverses through the list again to find the listings
     * equal to the highest and gets the ID of that listing.
     * Adds id(s) to the list.
     *
     * @return the list containing the listing ID(s) of the highest number of bedrooms.
     */
    public static Collection<String> getListingsIdMostBedrooms() {
        int highest = 0;
        String id = "";
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingBedrooms = listing.getBedrooms();
            if(listingBedrooms > highest) {
                highest = listingBedrooms;
                id = listing.getId();
            }
        }
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingBedrooms = listing.getBedrooms();
            if(listingBedrooms == highest) {
                id = listing.getId();
                list.add(id);
            }
        }
        return list;
    }

    /**
     * Returns the borough which has the most listings using a
     * HashMap to store the number of listings for each borough.
     *
     * @return the borough with the most listings.
     */
    public static String getBoroughMostListings() {
        HashMap<String, Integer> boroughFrequency = new HashMap<>();
        int highestFrequency = 0;
        String borough = "";

        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            int count = boroughFrequency.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + 1;
            boroughFrequency.put(listing.getNeighbourhoodCleansed(), count);
        }
        for (String key : boroughFrequency.keySet()) {
            int value = boroughFrequency.get(key);
            if (value > highestFrequency) {
                highestFrequency = value;
                borough = key;
            }
        }
        return borough;
    }
}