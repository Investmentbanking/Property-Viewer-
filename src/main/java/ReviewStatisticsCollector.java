
/**
 * Creates the review related statistics using the new data set.
 *
 * @author Ayesha Dorani
 */
public class ReviewStatisticsCollector {

    public static int getAverageReviewLocationScore(){
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

    public static int getLowestReviewScoreRating(){
        int lowestScore = Statistics.getNewListings().get(0).getReviewScoresRating();
        //String borough = Statistics.getNewListings().get(0).getNeighbourhoodCleansed();
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getReviewScoresRating() >= 0) {
                int listingScore = listing.getReviewScoresRating();
                if (listingScore < lowestScore) {
                    lowestScore = listingScore;
                    // borough = listing.getNeighbourhoodCleansed();
                }
            }
        }
        return lowestScore;
    }

    public static int get100ReviewRating(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresRating() == 100) {
                count++;
            }
        }
        return count;
    }



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

    public static int getAverageCheckinScore(){
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

    public static int get10RatedCommunicationReviewScore(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getReviewScoresCommunication() == 10) {
                count++;
            }
        }
        return count;
    }

}
