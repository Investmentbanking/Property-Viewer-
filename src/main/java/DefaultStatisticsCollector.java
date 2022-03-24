
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
     * @return The size of the listings in the old CSV file.
     */
    public static int getTotalAvailableProperties(){
        return Statistics.getOldListings().size();
    }

    /**
     * @return borough The borough with the most expensive property.
     */
    public static String getMostExpensiveBorough() {
        int mostExpensivePrice = 0;
        String borough = "";
        for(OldAirbnbListing listing: Statistics.getOldListings()) {
            int listingTotal = listing.getPrice() * listing.getMinimumNights();
            if(listingTotal > mostExpensivePrice) {
                listingTotal = mostExpensivePrice;
                borough = listing.getNeighbourhood();
            }
        }
        return borough;
    }

    /**
     * @return count The total number of non-private rooms.
     */
    public static int getNumberOfNonPrivateRooms() {
        int count = 0;
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            if(!listing.getRoom_type().equals("Private Room")) {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @return
     */
    public static int getAverageListings(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            count += listing.getHostListings();
        }
        return count/Statistics.getNewListings().size();
    }

    /**
     *
     * @return
     */
    public static String getCheapestBorough() {
        double cheapestPrice = Statistics.getOldListings().get(0).getPrice() * Statistics.getOldListings().get(0).getMinimumNights();
        String borough = Statistics.getOldListings().get(0).getNeighbourhood();
        for(OldAirbnbListing listing : Statistics.getOldListings()) {
            double listingTotal = listing.getPrice() * listing.getMinimumNights();
            if(listingTotal < cheapestPrice) {
                listingTotal = cheapestPrice;
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
    public static double getHighestReviewsPerMonth(){
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
