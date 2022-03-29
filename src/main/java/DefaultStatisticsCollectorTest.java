import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the default statistics, by setting price ranges used,
 * and testing manually calculated values to class calculated values.
 * As the dataset is constant, testing is done from
 * calculating the values wanted from the statistics manually and comparing
 * to the values that the class methods' calculates.
 * The price values tested are 3 different ranges.
 *
 * Only valid prices are tested, as checking if inputs are valid are handled by
 * the MainController class, alerting and not setting invalid values
 *
 * @Author Burhan Tekcan k21013451
 * @version 1.0
 */
public class DefaultStatisticsCollectorTest {

    /**
     * Runs before any tests are done.
     * Sets the initial runtime details needed to create statistics
     * Sets listings needed to create Statistics
     */
    @BeforeClass
    public static void setUp() throws Exception {
        RuntimeDetails.setNewAirbnbListings(AirbnbDataLoader.loadNewDataSet());     // Loads the new dataset being used for this project.
        RuntimeDetails.setOldAirbnbListings(AirbnbDataLoader.loadOldDataSet());     // Loads the old dataset being used for this project.
    }

    /**
     * Tests the average reviews for the properties with three ranges
     */
    @Test
    public void getAverageReviewsPerProperty() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        int expected = 12;
        int actual = DefaultStatisticsCollector.getAverageReviewsPerProperty();
        assertEquals(expected,actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        int expected2 = 0;
        int actual2 = DefaultStatisticsCollector.getAverageReviewsPerProperty();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        int expected3 = 11;
        int actual3 = DefaultStatisticsCollector.getAverageReviewsPerProperty();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests the number of available properties
     */
    @Test
    public void getTotalAvailableProperties() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        int expected = 53904;
        int actual = DefaultStatisticsCollector.getTotalAvailableProperties();
        assertEquals(expected, actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        int expected2 = 8;
        int actual2 = DefaultStatisticsCollector.getTotalAvailableProperties();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        int expected3 = 19033;
        int actual3 = DefaultStatisticsCollector.getTotalAvailableProperties();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests if the range changes the most expensive borough correctly
     */
    @Test
    public void getMostExpensiveBorough() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        String expected = "Richmond upon Thames";
        String actual = DefaultStatisticsCollector.getMostExpensiveBorough();
        assertEquals(expected, actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        String expected2 = "Hackney";
        String actual2 = DefaultStatisticsCollector.getMostExpensiveBorough();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        String expected3 = "Westminster";
        String actual3 = DefaultStatisticsCollector.getMostExpensiveBorough();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests if the number of private properties are calculated correctly
     */
    @Test
    public void getNonPrivateProperties() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        int expected = 27885;
        int actual = DefaultStatisticsCollector.getNonPrivateProperties();
        assertEquals(expected, actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        int expected2 = 7;
        int actual2 = DefaultStatisticsCollector.getNonPrivateProperties();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        int expected3 = 1475;
        int actual3 = DefaultStatisticsCollector.getNonPrivateProperties();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests the average number of host listings for each price range
     */
    @Test
    public void getAverageListings() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        int expected = 14;
        int actual = DefaultStatisticsCollector.getAverageListings();
        assertEquals(expected,actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        int expected2 = 3;
        int actual2 = DefaultStatisticsCollector.getAverageListings();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        int expected3 = 4;
        int actual3 = DefaultStatisticsCollector.getAverageListings();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests the cheapest borough for a given price range
     */
    @Test
    public void getCheapestBorough() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        String expected = "Croydon";
        String actual = DefaultStatisticsCollector.getCheapestBorough();
        assertEquals(expected,actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        String expected2 = "Richmond upon Thames";
        String actual2 = DefaultStatisticsCollector.getCheapestBorough();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        String expected3 = "Croydon";
        String actual3 = DefaultStatisticsCollector.getCheapestBorough();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests the average availability of properties in a given price range
     */
    @Test
    public void getAverageAvailability() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        int expected = 113;
        int actual = DefaultStatisticsCollector.getAverageAvailability();
        assertEquals(expected,actual);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        int expected2 = 244;
        int actual2 = DefaultStatisticsCollector.getAverageAvailability();
        assertEquals(expected2, actual2);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        int expected3 = 103;
        int actual3 = DefaultStatisticsCollector.getAverageAvailability();
        assertEquals(expected3, actual3);
    }

    /**
     * Tests the highest number of reviews per month for the price ranges
     */
    @Test
    public void getHighestReviewsPerMonth() {
        // largest possible range
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(10000);
        Statistics.reloadListings();
        double expected = 16.87;
        double actual = DefaultStatisticsCollector.getHighestReviewsPerMonth();
        assertEquals(expected, actual,0);
        // the two largest valid options
        RuntimeDetails.setMinimumPrice(5000);
        Statistics.reloadListings();
        double expected2 = 0.08;
        double actual2 = DefaultStatisticsCollector.getHighestReviewsPerMonth();
        assertEquals(expected2, actual2,0);
        // two smallest options
        RuntimeDetails.setMinimumPrice(0);
        RuntimeDetails.setMaximumPrice(50);
        Statistics.reloadListings();
        double expected3 = 16.87;
        double actual3 = DefaultStatisticsCollector.getHighestReviewsPerMonth();
        assertEquals(expected3, actual3,0);
    }
}