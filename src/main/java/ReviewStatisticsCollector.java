
/**
 * Creates the review related statistics using the new data set for the statistics panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 30.03.2022
 */
public class ReviewStatisticsCollector {

    /**
     * Increments the count each time listing review score checkin is higher
     * or equal to 0.
     * Increments the total each time listing review score checkin is equal to 10.
     * Divides total by count then * 100 to find the percentage.
     *
     * @return the percentage rounded to the nearest integer.
     */
    public static double getPercentage10ReviewScoreCheckin() {
        double count = 0;
        double total = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresCheckin() >= 0) {
                count++;
                if(listing.getReviewScoresCheckin() == 10){
                    total++;
                }
            }
        }
        return Math.round((total/count) * 100);
    }

    /**
     * Checks if review score location rating is higher or equal to 0, if so,
     * adds the rating to the count and increments the size.
     *
     * @return review score location rating score total divided by number of listings.
     */
    public static int getAverageReviewLocationScore() {
        int count = 0;
        int size = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresLocation() >= 0) {
                count += listing.getReviewScoresLocation();
                size++;
            }
        }
        return count/size;
    }

    /**
     * Checks if review score rating for listing is larger or equal
     * to 0, if so, checks if rating is lower than previous lowest score.
     *
     * @return the lowest review score rating.
     */
    public static int getLowestReviewScoreRating(){
        int lowestScore = Statistics.getNewListings().get(0).getReviewScoresRating();
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getReviewScoresRating() >= 0) {
                int listingScore = listing.getReviewScoresRating();
                if (listingScore < lowestScore) {
                    lowestScore = listingScore;
                }
            }
        }
        return lowestScore;
    }

    /**
     * Checks if review score rating for each listing is equal to 100.
     * If so, increments the count.
     *
     * @return total number of listings with review score ratings of 100.
     */
    public static int get100ReviewRating(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresRating() == 100) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if review score value for listing is bigger or equal
     * to 0, if so, adds the review score value to count.
     * Increments the size each time.
     *
     * @return the total review score values divided by size of listings.
     */
    public static int getAverageReviewValueScore(){
        int count = 0;
        int size = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresValue() >= 0) {
                count += listing.getReviewScoresValue();
                size++;
            }
        }
        return count/size;
    }

    /**
     * Checks if listing review score rating is bigger or equal
     * to 0, if so, adds the review score rating to count.
     * Increments the size each time.
     * @return the total review score ratings divided by size by listings.
     */
    public static int getAverageReviewScoreRating(){
        int count = 0;
        int size = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getReviewScoresRating() >= 0){
                count += listing.getReviewScoresRating();
                size++;
            }
        }
        return count/size;
    }

    /**
     * Checks if review score cleanliness is bigger or equal to 0,
     * if so, adds review value of listing to count.
     * Increments the size each time.
     *
     * @return the total review score cleanliness values divided by size of listings.
     */
    public static int getAverageCleanlinessScore() {
        int count = 0;
        int size = 0;
        for (NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getReviewScoresCleanliness() >= 0) {
                count += listing.getReviewScoresCleanliness();
                size++;
            }

        }
        return count/size;
    }

    /**
     * Checks if review score checkin is bigger or equal to 0,
     * if so, adds the value to count and increments the size
     * each time.
     *
     * @return the total review scores checkin divided by size of listings.
     */
    public static int getAverageCheckinScore() {
        int count = 0;
        int size = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresCheckin() >= 0) {
                count += listing.getReviewScoresCheckin();
                size++;
            }
        }
        return count/size;
    }

    /**
     * Increments the count if review score communication of listing
     * is equal to 10.
     *
     * @return the total number of listings with review score communication
     * values of 10.
     */
    public static int get10RatedCommunicationReviewScore() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresCommunication() == 10) {
                count++;
            }
        }
        return count;
    }

}
