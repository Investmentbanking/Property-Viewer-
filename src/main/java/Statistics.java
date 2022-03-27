import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Statistics class generates all the different statistics, which are the:
 * default stats, review stats, borough stats and amenities stats.
 * This class also handles which stats are displayed on the Panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 22.03.2022
 */
public class Statistics {

    public static ArrayList<OldAirbnbListing> getOldListings() {
        return oldListings;
    }
    public static ArrayList<NewAirbnbListing> getNewListings() {
        return newListings;
    }

    private static ArrayList<OldAirbnbListing> oldListings = inRangeOldListings();
    private static ArrayList<NewAirbnbListing> newListings = inRangeNewListings();

    //private static ArrayList<OldAirbnbListing> OldListings = RuntimeDetails.getOldAirbnbListings();
    //private static ArrayList<NewAirbnbListing> NewListings = RuntimeDetails.getNewAirbnbListings();
    private static final ArrayList<String> stats = new ArrayList<>();
    //private static final ArrayList<String> reviewStats = new ArrayList<>();
    //private static final ArrayList<String> boroughStats = new ArrayList<>();
    //private static final ArrayList<String> amenitiesStats = new ArrayList<>();

    /**
     * Filters the properties from the original CAV file within the given price range.
     * @return the new ArrayList with the specific AirbnbListings.
     */
    private static ArrayList<OldAirbnbListing> inRangeOldListings() {
        ArrayList<OldAirbnbListing> oldListingsInBound = (ArrayList<OldAirbnbListing>) RuntimeDetails.getOldAirbnbListings().clone();
        oldListingsInBound.removeIf(listing ->(listing.getPrice() <RuntimeDetails.getMinimumPrice() ||listing.getPrice()>RuntimeDetails.getMaximumPrice()));
        return oldListingsInBound;
    }

    /**
     * Filters the properties from the new CSV file within the given price range.
     * @return the new ArrayList with the specific AirbnbListings.
     */
    private static ArrayList<NewAirbnbListing> inRangeNewListings() {
        ArrayList<NewAirbnbListing> newListingsInBound = (ArrayList<NewAirbnbListing>) RuntimeDetails.getNewAirbnbListings().clone();
        newListingsInBound.removeIf(listing -> (listing.getPrice() < RuntimeDetails.getMinimumPrice() || listing.getPrice() > RuntimeDetails.getMaximumPrice()));
        return newListingsInBound;
    }

    /**
     * Clears all previous stats added.
     * Generates the default statistics and adds them to the stats ArrayList.
     */
    public static void createStats() {
        stats.clear();
        stats.add("Average number of reviews per property:" +"\n\n"  + DefaultStatisticsCollector.getAverageReviewsPerProperty());
        stats.add("Total number of available properties:" + "\n\n" + DefaultStatisticsCollector.getTotalAvailableProperties());
        stats.add("Number of non-private rooms:" + "\n\n" + DefaultStatisticsCollector.getNonPrivateProperties());
        stats.add("The most expensive borough:" + "\n\n" + DefaultStatisticsCollector.getMostExpensiveBorough());

        stats.add("The average number of listings per host:" + "\n\n" + DefaultStatisticsCollector.getAverageListings());
        stats.add("The cheapest borough:" + "\n\n" + DefaultStatisticsCollector.getCheapestBorough());
        stats.add("Average availability per year:" + "\n\n" + DefaultStatisticsCollector.getAverageAvailability());
        stats.add("Highest number of reviews per month" + "\n\n" + DefaultStatisticsCollector.getHighestReviewsPerMonth());

    }

    /**
     * Clears all previous stats added.
     * Generates the review based statistics and adds them to the stats ArrayList.
     */
    public static void createReviewStats(){
        stats.clear();

        stats.add("Average location review score: " + "\n\n"  + ReviewStatisticsCollector.getAverageReviewLocationScore());
        stats.add("Number of listings with '100' review score ratings: " + "\n\n" + ReviewStatisticsCollector.get100ReviewRating());
        stats.add("Average review score rating: " + "\n\n" + ReviewStatisticsCollector.getAverageReviewScoreRating());
        stats.add("Average review score on cleanliness" + "\n\n" + ReviewStatisticsCollector.getAverageCleanlinessScore());

        stats.add("Average review score on checkin" + "\n\n" + ReviewStatisticsCollector.getAverageCheckinScore());
        stats.add("Number of listings with '10' communication review scores: " + "\n\n" + ReviewStatisticsCollector.get10RatedCommunicationReviewScore());
        stats.add("Average value review score: " + "\n\n" + ReviewStatisticsCollector.getAverageReviewValueScore());
        stats.add("Lowest review score rating: " + "\n\n" + ReviewStatisticsCollector.getLowestReviewScoreRating());
    }

    /**
     * Clears all previous stats added.
     * Generates the borough based statistics and adds them to the stats ArrayList.
     */
    public static void createBoroughStats(){
        stats.clear();
        stats.add("A Borough with highest review score rating:" +"\n\n"  + BoroughStatisticsCollector.getBoroughHighestReviewScoreRating());
        stats.add("Borough with most listings:" + "\n\n" + BoroughStatisticsCollector.getBoroughMostListings());
        stats.add("Number of Boroughs with 365 day availability:" + "\n\n" + BoroughStatisticsCollector.getBorough365Availability());
        stats.add("Boroughs which accommodates highest number of people:" + "\n\n" + BoroughStatisticsCollector.getBoroughHighestAccommodates());

        stats.add("Borough(s) with the the most bathrooms: " + "\n\n" + BoroughStatisticsCollector.getBoroughsMostBathrooms());
        stats.add("Borough(s) with the most bedrooms:" + "\n\n" + BoroughStatisticsCollector.getBoroughsMostBedrooms());
        stats.add("Number of boroughs in Croydon (if any):" + "\n\n" + BoroughStatisticsCollector.getCroydonBoroughs());
        stats.add("Borough(s) with the most beds:" + "\n\n" + BoroughStatisticsCollector.getBoroughsMostBeds());

    }

    /**
     * Clears all previous stats added.
     * Generates the amenities based statistics and adds them to the stats ArrayList.
     */
    public static void createAmenitiesStats(){
        stats.clear();
        stats.add("Average number of amenities:" + "\n\n" + AmenitiesStatisticsCollector.averageNumberOfAmenities());
        stats.add("Number of properties containing more than 2 bathrooms:" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan2Bathrooms());
        stats.add("Number of properties with a Garden or backyard:" + "\n\n" + AmenitiesStatisticsCollector.getGardenOrBackyardProperties());
        stats.add("amenities 4");

        stats.add("Number of properties that contain an oven:" + "\n\n" + AmenitiesStatisticsCollector.getPropertiesContainingOven());
        stats.add("Number of properties containing more than 1 bedroom:" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan3BedroomProperties());
        stats.add("ID of apartment with most amenities " + "\n\n" + AmenitiesStatisticsCollector.mostAmenitiesID());
        stats.add("Number of properties containing more than 2 beds" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan1BedProperties());
    }

    /**
     * Changes which stat is displayed when a mouse event is received from
     * a button in the statistics panel.
     * @param node The node that was pressed.
     */
    public static void changeStats(Node node) {
        Scene scene = node.getScene();
        Text text = (Text) scene.lookup("#stat" + node.getId().charAt(0));
        char direction = node.getId().charAt(1); // represents direction to move, (0 = right, 1 = left)

        if(direction == '0') {
            stats.add(stats.size(), text.getText());
            text.setText(stats.get(0));
            stats.remove(0);
        }
        else {
            stats.add(0, text.getText());
            text.setText(stats.get(stats.size()-1));
            stats.remove(stats.size()-1);
        }
    }

    /**
     * Gets the first stat in the ArrayList,
     * should not be used after the panel is set up.
     * @return stat The first stat as a string.
     */
    public static String getNextStat() {
        String stat = stats.get(0);
        stats.remove(0);
        return stat;
    }
}
