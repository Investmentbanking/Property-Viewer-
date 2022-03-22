import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The users entered search details
 * stores start and end dates, price range
 * validity flags, and total nights
 * @author Burhan Tekcan K21013451
 * @version 1.0
 */
public class RuntimeDetails {

    // The list of all the listings from the new data set.
    private static ArrayList<NewAirbnbListing> listings;
    //start date of when the user might want to book
    private static LocalDate startDate;
    // end date of when the user might want to book till
    private static LocalDate endDate;
    // time between start and end date
    private static long totalNights;
    // true if both prices are entered and are a valid range
    private static boolean validPrices;
    // true if both dates are in the future, and the start is before the end
    private static boolean validDates;
    // the starting price of properties the user is searching for
    private static int minimumPrice;
    // the maximum price of what the user is searching for
    private static int maximumPrice;

    // A static block to set minimum price and maximum price to the largest possible range
    static{
        minimumPrice = 0;
        maximumPrice = 1000;
    }



    /**
     * public static getter methods
     * returns the values
     */

    public static ArrayList<NewAirbnbListing> getListings() {
        return listings;
    }

    public static LocalDate getStartDate(){
        return startDate;
    }

    public static LocalDate getEndDate() {
        return endDate;
    }

    public static boolean isValidDates() {
        return validDates;
    }

    public static boolean isValidPrices() {
        return validPrices;
    }

    public static int getMaximumPrice() {
        return maximumPrice;
    }

    public static int getMinimumPrice() {
        return minimumPrice;
    }

    public static long getTotalNights() {
        return totalNights;
    }

    /**
     * public static setter methods
     * sets values as given inputs
     */

    public static void setListings(ArrayList<NewAirbnbListing> listings) {
        RuntimeDetails.listings = listings;
    }

    public static void setEndDate(LocalDate endDate) {
        RuntimeDetails.endDate = endDate;
    }

    public static void setMaximumPrice(int maximumPrice) {
        RuntimeDetails.maximumPrice = maximumPrice;
    }

    public static void setMinimumPrice(int minimumPrice) {
        RuntimeDetails.minimumPrice = minimumPrice;
    }

    public static void setStartDate(LocalDate startDate) {
        RuntimeDetails.startDate = startDate;
    }

    public static void setTotalNights(long totalNights) {
        RuntimeDetails.totalNights = totalNights;
    }

    public static void setValidDates(boolean validDates) {
        RuntimeDetails.validDates = validDates;
    }

    public static void setValidPrices(boolean validPrices) {
        RuntimeDetails.validPrices = validPrices;
    }

    public static boolean isValidDetails() {
        return (validDates && validPrices);
    }

    /**
     * sets the total nights if both the start date and end date are not null
     * calculates how many days from start to end and stores in total nights
     * sets validDates to true
     */
    public static void setTotalNights()
    {
        if(startDate != null && endDate != null)
        {
            totalNights = startDate.until(endDate, ChronoUnit.DAYS);
            validDates = true;
        }
    }

    /**
     * @return the price range as string
     */
    public static String getPriceRange()
    {
        return (minimumPrice + " to " + maximumPrice);
    }
}
