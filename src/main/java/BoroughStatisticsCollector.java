import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates the borough related statistics using the new data set.
 *
 * @author Ayesha Dorani
 * @version
 */
public class BoroughStatisticsCollector {

    /**
     * Returns the number of boroughs equal to Croydon and increments the count each time.
     * @return count The total number of Croydon listings.
     */
    public static int getCroydonBoroughs(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getNeighbourhoodCleansed().equals("Croydon")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the boroughs which contains the most beds.
     * @return list
     */
    public static Collection<String> getBoroughsMostBeds(){
        String borough = "";
        int highest = 0;
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingBeds = listing.getBeds();
            if(listingBeds > highest) {
                highest = listingBeds;
                borough = listing.getNeighbourhoodCleansed();
            }
        }
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            double listingBeds = listing.getBeds();
            if(listingBeds == highest) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){
                    list.add(borough);
                }
            }
        }
        return list;
    }

    /**
     *
     * @return
     */
    public static Collection<String> getBoroughsMostBedrooms(){
        String borough = "";
        int highest = 0;
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingBedrooms = listing.getBedrooms();
            if(listingBedrooms > highest) {
                highest = listingBedrooms;
                borough = listing.getNeighbourhoodCleansed();
            }
        }
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            double listingBedrooms = listing.getBedrooms();
            if(listingBedrooms == highest) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){
                    list.add(borough);
                }
            }
        }
        return list;
    }

    /**
     * Returns upto ten boroughs which have the highest number of bathrooms.
     * First finds the first listing which has the highest number of bathrooms.
     * Then transverses through the list again to find the values that match,
     * and adds these to the list.
     *
     * @return first10Elements The first 10 elements of the list.
     */
    public static Collection<String> getBoroughsMostBathrooms(){
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
        List<String> first10Elements = list.stream().limit(10).collect(Collectors.toList());
        return first10Elements;
    }

    /**
     * Returns upto ten boroughs which accommodates the highest number of people.
     * First iterates through all the new listings to find the first borough with the highest accommodates value.
     * Then transverses through the list again and adds those which match to the list.
     *
     * @return first10Elements The first 10 elements of the list.
     */
    public static Collection<String> getBoroughHighestAccommodates() {
        String borough = "";
        int highest = 0;
        List<String> list = null;
        list = new ArrayList<>();

        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingAccommodates = listing.getAccommodates();
            if (listingAccommodates > highest) {
                highest = listingAccommodates;
                borough = listing.getNeighbourhoodCleansed();
            }
        }
        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingAccommodates = listing.getAccommodates();
            if (listingAccommodates == highest) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){
                    list.add(borough);
                }
            }
        }
        List<String> first10Elements = list.stream().limit(10).collect(Collectors.toList());
        return first10Elements;
    }

    /**
     * Returns the number of boroughs which contain listings that are available
     * 365 days a year.
     * @return The size of the list that contains the boroughs.
     */
    public static int getBorough365Availability(){
        int count = 0;
        String borough = "";
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getAvailability365() == 365) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){ // if listing does not already contain the borough.
                    list.add(borough);
                }
            }
        }
        return list.size();
    }

    /**
     * Returns the borough which has the most listings using a
     * HashMap to store the number of listings for each borough.
     * @return the borough with the most listings.
     */
    public static String getBoroughMostListings(){
        HashMap<String, Integer> boroughFrequency = new HashMap<>();
        int highestFrequency = 0;
        String borough = "";

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int count = boroughFrequency.getOrDefault(listing.getNeighbourhoodCleansed(), 0) + 1;
            boroughFrequency.put(listing.getNeighbourhoodCleansed(), count);
            if (count > highestFrequency) {
                highestFrequency = count;
                borough = listing.getNeighbourhoodCleansed();
            }
        }
        return borough;
    }

    /**
     *
     * @return
     */
    public static Collection<String> getBoroughHighestReviewScoreRating(){
        int highest = 0;
        String borough = "";
        List<String> list = null;
        list = new ArrayList<>();

        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            int listingRating = listing.getReviewScoresRating();
            if(listingRating > highest){
                highest = listingRating;
                borough = listing.getNeighbourhoodCleansed();
            }

        }
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            double listingBathrooms = listing.getReviewScoresRating();
            if(listingBathrooms == highest) {
                borough = listing.getNeighbourhoodCleansed();
                if(!list.contains(borough)){
                    list.add(borough);
                }
            }
        }
        List<String> first10Elements = list.stream().limit(10).collect(Collectors.toList());
        return first10Elements;
    }
}