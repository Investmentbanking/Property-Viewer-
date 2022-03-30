import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * Statistics class generates different types of statistics which are the:
 * default stats, review stats, borough and listings stats and amenities stats.
 * This class also handles which stats are displayed on the Panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 30.03.2022
 */
public class Statistics {

    private static ArrayList<OldAirbnbListing> oldListings = loadOldRange();
    private static ArrayList<NewAirbnbListing> newListings = loadNewRange();
    private static final ArrayList<String> stats = new ArrayList<>();

    /**
     * Returns the old listings within the price range.
     *
     * @return the old listings.
     */
    public static ArrayList<OldAirbnbListing> getOldListings() {
        return oldListings;
    }

    /**
     * Returns the new listings within the price range.
     *
     * @return the new listings
     */
    public static ArrayList<NewAirbnbListing> getNewListings() {
        return newListings;
    }

    /**
     * Clones the NewAirbnbListing ArrayList and filters the properties within
     * the price range.
     *
     * @return the new ArrayList with the specific AirbnbListings.
     */
    public static ArrayList<NewAirbnbListing> loadNewRange() {
        ArrayList<NewAirbnbListing> newListingsInBound = (ArrayList<NewAirbnbListing>) RuntimeDetails.getNewAirbnbListings().clone();
        // removes listing if listing is lower than the minimum price or higher than the maximum price
        newListingsInBound.removeIf(listing -> (listing.getPrice() < RuntimeDetails.getMinimumPrice() || listing.getPrice() > RuntimeDetails.getMaximumPrice()));
        return newListingsInBound;
    }

    /**
     * Clones the OldAirbnbListing ArrayList and filters the properties within,
     * the price range.
     *
     * @return the new ArrayList with the specific AirbnbListings.
     */
    public static ArrayList<OldAirbnbListing> loadOldRange() {
        ArrayList<OldAirbnbListing> oldListingsInBound = (ArrayList<OldAirbnbListing>) RuntimeDetails.getOldAirbnbListings().clone();
        // removes listing if listing is lower than the minimum price or higher than the maximum price
        oldListingsInBound.removeIf(listing -> (listing.getPrice() <RuntimeDetails.getMinimumPrice() ||listing.getPrice()>RuntimeDetails.getMaximumPrice()));
        return oldListingsInBound;
    }

    /**
     * Clears all previous stats added.
     * Generates the default statistics and adds them to the stats ArrayList.
     */
    public static void createStats() {
        stats.clear();

        stats.add("Average number of reviews per property:" + "\n\n" + DefaultStatisticsCollector.getAverageReviewsPerProperty());
        stats.add("Total number of available properties:" + "\n\n" + DefaultStatisticsCollector.getTotalAvailableProperties());
        stats.add("Number of non-private rooms:" + "\n\n" + DefaultStatisticsCollector.getNonPrivateProperties());
        stats.add("The most expensive borough:" + "\n\n" + DefaultStatisticsCollector.getMostExpensiveBorough());

        stats.add("No. listings with reviews:" + "\n\n" + DefaultStatisticsCollector.getListingsWithReviews());
        stats.add("The cheapest borough:" + "\n\n" + DefaultStatisticsCollector.getCheapestBorough());
        stats.add("Average availability per year:" + "\n\n" + DefaultStatisticsCollector.getAverageAvailability());
        stats.add("Highest number of reviews per month:" + "\n\n" + DefaultStatisticsCollector.getHighestReviewsPerMonth());

    }

    /**
     * Clears all previous stats added.
     * Generates the review based statistics and adds them to the stats ArrayList.
     */
    public static void createReviewStats() {
        stats.clear();

        stats.add("The percentage of listings which have checkin review scores of 10:" + "\n\n" + ReviewStatisticsCollector.getPercentage10ReviewScoreCheckin());
        stats.add("Average location review score: " + "\n\n"  + ReviewStatisticsCollector.getAverageReviewLocationScore());
        stats.add("Number of listings with '100' review score ratings: " + "\n\n" + ReviewStatisticsCollector.get100ReviewRating());
        stats.add("Average review score rating: " + "\n\n" + ReviewStatisticsCollector.getAverageReviewScoreRating());

        stats.add("Average review score on cleanliness" + "\n\n" + ReviewStatisticsCollector.getAverageCleanlinessScore());
        stats.add("No. of listings with communication review scores of '10':" + "\n\n" + ReviewStatisticsCollector.get10RatedCommunicationReviewScore());
        stats.add("Average value review score: " + "\n\n" + ReviewStatisticsCollector.getAverageReviewValueScore());
        stats.add("Lowest review score rating: " + "\n\n" + ReviewStatisticsCollector.getLowestReviewScoreRating());
    }

    /**
     * Clears all previous stats added.
     * Generates the borough and listing related statistics and adds them to the stats ArrayList.
     */
    public static void createBoroughAndListingsStats(){
        stats.clear();

        stats.add("Property closest to london eye: " + "\n\n" + BoroughAndListingsStatisticsCollector.getPropertyClosestLondonEye());
        stats.add("Borough with most properties:" + "\n\n" + BoroughAndListingsStatisticsCollector.getBoroughMostListings());
        stats.add("Number of 'Hotel' listings: " + "\n\n" + BoroughAndListingsStatisticsCollector.getNumberHotelListings());
        stats.add("Property ID(s) with highest no. of bedrooms:" + "\n\n" + BoroughAndListingsStatisticsCollector.getListingsIdMostBedrooms());

        stats.add("Borough with highest average 365 availability:" + "\n\n" + BoroughAndListingsStatisticsCollector.getBoroughHighestAvailability());
        stats.add("Borough(s) which contain a listing with one of the highest no. of bathrooms: " + "\n\n" + BoroughAndListingsStatisticsCollector.getBoroughsMostBathrooms());
        stats.add("Number of properties in Central London:" + "\n\n" + BoroughAndListingsStatisticsCollector.getListingsCentralLondon());
        stats.add("Borough with highest average of beds:" + "\n\n" + BoroughAndListingsStatisticsCollector.getBoroughMostBeds());
    }

    /**
     * Clears all previous stats added.
     * Generates the amenities based statistics and adds them to the stats ArrayList.
     */
    public static void createAmenitiesStats() {
        stats.clear();

        stats.add("Average number of amenities:" + "\n\n" + AmenitiesStatisticsCollector.averageNumberOfAmenities());
        stats.add("Number of properties containing more than 2 bathrooms:" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan2Bathrooms());
        stats.add("Number of properties with a Garden or backyard:" + "\n\n" + AmenitiesStatisticsCollector.getGardenOrBackyardProperties());
        stats.add("Total number of amenities:" + "\n\n" + AmenitiesStatisticsCollector.getTotalAmenities());

        stats.add("Number of properties that contain an oven:" + "\n\n" + AmenitiesStatisticsCollector.getPropertiesContainingOven());
        stats.add("Number of properties containing more than 1 bedroom:" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan3BedroomProperties());
        stats.add("ID of apartment with most amenities:" + "\n\n" + AmenitiesStatisticsCollector.mostAmenitiesID());
        stats.add("Number of properties containing more than 2 beds:" + "\n\n" + AmenitiesStatisticsCollector.getMoreThan1BedProperties());
    }

    /**
     * Changes which stat is displayed when a mouse event is received from
     * a button in the statistics panel.
     * @param node The node (button) that was pressed.
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
     * @return the first stat as a string.
     */
    public static String getNextStat() {
        String stat = stats.get(0);
        stats.remove(0);
        return stat;
    }

    /**
     * Reloads the listings based on newly inputted price ranges.
     */
    public static void reloadListings() {
        oldListings = loadOldRange();
        newListings = loadNewRange();
    }
}