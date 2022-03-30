import java.util.HashMap;

/*
 * Creates the default statistics using the old data set for the statistics panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 30.02.2022
 */
public class DefaultStatisticsCollector {

    /**
     * Adds up all the reviews for each property then gets the average.
     *
     * @return The total number of reviews divided by the number of listings.
     */
    public static int getAverageReviewsPerProperty() {
        int total = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            total += listing.getNumberOfReviews();
        }
        return total / Statistics.getOldListings().size();
    }

    /**
     * Returns the total number of available properties.
     *
     * @return The size of the listings.
     */
    public static int getTotalAvailableProperties() {
        return Statistics.getOldListings().size();
    }

    /**
     * Checks which listings are not a 'Private Room'.
     * Increments count each time listing is not a 'Private Room'.
     *
     * @return The total number of non-private rooms.
     */
    public static int getNonPrivateProperties() {
        int count = 0;
        for (OldAirbnbListing listing : Statistics.getOldListings()) {
            if (!listing.getRoom_type().equals("Private room")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if number of reviews for each listing is bigger than 0.
     * Increments count each time.
     *
     * @return the total number of listings with reviews.
     */
    public static int getListingsWithReviews() {
        int count = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            if(listing.getNumberOfReviews() > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Creates two HashMaps, one stores the number of listings in each borough,
     * the other stores the total price of listings in a borough. Finds the highest
     * average and checks if this value is higher than the previous value, if so,
     * gets the listing borough and returns this.
     *
     * @return the most expensive borough based on the highest average.
     */
    public static String getMostExpensiveBorough() {
        int highestPrice = 0;
        String borough = "";
        HashMap<String, Integer> listingsBorough = new HashMap<>(); // stores the number of listings for each borough
        HashMap<String, Integer> listingsPrice = new HashMap<>(); // stores the total price for all listings in each borough

        for (OldAirbnbListing listing : Statistics.getOldListings()) {
            int listingPrice = listing.getPrice() * listing.getMinimumNights(); // price = price * minimum nights

            int count  = listingsBorough.getOrDefault(listing.getNeighbourhood(), 0) + 1;
            listingsBorough.put(listing.getNeighbourhood(), count);

            int total = listingsPrice.getOrDefault(listing.getNeighbourhood(), 0) + listingPrice;
            listingsPrice.put(listing.getNeighbourhood(), total);
        }
        double sum = 0;

        for (String key : listingsPrice.keySet()) {

            double value1 = listingsBorough.get(key);
            double value2 = listingsPrice.get(key);
            double division = value2 / value1;
            if (division > sum) {
                sum = division;
                borough = key;
        }
        }
        return borough;
    }

    /**
     * Creates two HashMaps, one stores the number of listings in each borough,
     * the other stores the total price of listings in a borough. Finds the smallest
     * average and checks if this value is smaller than the previous value, if so,
     * gets the listing borough and returns this.
     *
     * @return the cheapest borough based off the smaller average.
     */
    public static String getCheapestBorough() {

        double finalAverage = Double.POSITIVE_INFINITY; // holding the positive infinity of type double.
        String borough = "";
        HashMap<String, Integer> listingsBorough = new HashMap<>();
        HashMap<String, Integer> listingsPrice = new HashMap<>();

        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            int listingPrice = listing.getPrice() * listing.getMinimumNights();
            int count  = listingsBorough.getOrDefault(listing.getNeighbourhood(), 0) + 1;
            listingsBorough.put(listing.getNeighbourhood(), count);

            int counts = listingsPrice.getOrDefault(listing.getNeighbourhood(), 0) + listingPrice;
            listingsPrice.put(listing.getNeighbourhood(), counts);
        }

        for (String key : listingsPrice.keySet()) {
            double value1 = listingsBorough.get(key);
            double value2 = listingsPrice.get(key);
            double division = value2 / value1;
            if (division < finalAverage) {
                finalAverage = division;
                borough = key;
            }
        }
        return borough;
    }

    /**
     * Adds up the availability for each listing to variable count,
     * then finds the average.
     *
     * @return the total availability divided by the size of listings.
     */
    public static int getAverageAvailability() {
        int count = 0;
        for(OldAirbnbListing listing  : Statistics.getOldListings()){
            count += listing.getAvailability365();
        }
        return count/Statistics.getOldListings().size();
    }

    /**
     * Gets the reviews per month for each listing and checks
     * if value is higher than the previous value, if so, assigns
     * this value to highest.
     *
     * @return the highest number of reviews per month.
     */
    public static double getHighestReviewsPerMonth() {
        double highest = 0;
        for (OldAirbnbListing listing : Statistics.getOldListings()) {
            double listingReviews = listing.getReviewsPerMonth();
            if (listingReviews > highest) {
                highest = listingReviews;
            }
        }
        return highest;
    }
}
