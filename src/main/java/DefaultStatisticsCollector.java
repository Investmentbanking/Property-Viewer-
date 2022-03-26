
/*
 * Creates the default statistics for the statistics panel.
 * @author Ayesha Dorani
 */
public class DefaultStatisticsCollector {

    /**
     * @return The total number of reviews divided by the number of listings in old CSV file.
     */
    public static int getAverageReviewsPerProperty() {
        int total = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            total += listing.getNumberOfReviews();
        }
        return total / Statistics.getOldListings().size();
    }

    /**
     * Returns the total number of available properties,
     * based off the price range.
     * @return The size of the listings in the old CSV file.
     */
    public static int getTotalAvailableProperties(){
        return Statistics.getOldListings().size();
    }

    /**
     * Returns the borough with the most expensive listing,
     * based off the listing price * minimum price.
     * @return borough The borough with the most expensive property.
     */
    public static String getMostExpensiveBorough() {
        int highestPrice = 0; // highest price = price * min number of nights
        String borough = "";
        for (OldAirbnbListing listing : Statistics.getOldListings()) {
            int listingPrice = listing.getPrice() * listing.getMinimumNights();
            if (listingPrice > highestPrice) {
                highestPrice = listingPrice;
                borough = listing.getNeighbourhood();
            }
        }
        return borough;
    }

    /**
     * @return count The total number of non-private rooms.
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
     * Returns the average number of listings.
     * @return
     */
    public static int getAverageListings(){
        int count = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            count += listing.getCalculatedHostListingsCount();
        }
        return count/Statistics.getOldListings().size();
    }

    /**
     * Returns the borough with the cheapest listing,
     * based off the listing price * minimum nights.
     * @return borough The cheapest borough.
     */
    public static String getCheapestBorough() {
        int lowestPrice = Statistics.getOldListings().get(0).getPrice() * Statistics.getOldListings().get(0).getMinimumNights(); // use first property as initial price
        String borough = Statistics.getOldListings().get(0).getNeighbourhood();
        for (OldAirbnbListing listing : Statistics.getOldListings()) {
            int listingPrice = listing.getPrice() * listing.getMinimumNights();
            if (listingPrice < lowestPrice) {
                lowestPrice = listingPrice;
                borough = listing.getNeighbourhood();
            }
        }
        return borough;
    }

    /**
     *
     * @return
     */
    public static int getAverageAvailability(){
        int count = 0;
        for(NewAirbnbListing listing  : Statistics.getNewListings()){
            count += listing.getAvailability365();
        }
        return count/Statistics.getNewListings().size();
    }

    /**
     * 
     * @return
     */
    public static double getHighestReviewsPerMonth() {
        double highest = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            double listingReviews = listing.getReviewsPerMonth();
            if(listingReviews > highest) {
                highest = listingReviews;
            }
        }
        return highest;
    }
    
}
