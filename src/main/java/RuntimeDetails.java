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
    private static ArrayList<NewAirbnbListing> newAirbnbListings;
    // The list of all the listings from the old data set.
    private static ArrayList<OldAirbnbListing> oldAirbnbListings;
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
     * @return selected start date
     */

    public static ArrayList<NewAirbnbListing> getNewAirbnbListings() {
        return newAirbnbListings;
    }

    public static ArrayList<OldAirbnbListing> getOldAirbnbListings() {
        return oldAirbnbListings;
    }

    public static LocalDate getStartDate(){
        return startDate;
    }

    /**
     * @return selected end date
     */
    public static LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return true if dates are valid
     */
    public static boolean isValidDates() {
        return validDates;
    }

    /**
     * @return true if prices are valid
     */
    public static boolean isValidPrices() {
        return validPrices;
    }

    /**
     * @return the maximum price selected
     */
    public static int getMaximumPrice() {
        return maximumPrice;
    }

    /**
     * @return the minimum price selected
     */
    public static int getMinimumPrice() {
        return minimumPrice;
    }

    /**
     * @return the amount of nights from start date to end date
     */
    public static long getTotalNights() {
        return totalNights;
    }


    /**
     * @param endDate the end date selected by the user for bookings
     */

    public static void setNewAirbnbListings(ArrayList<NewAirbnbListing> newAirbnbListings) {
        RuntimeDetails.newAirbnbListings = newAirbnbListings;
    }

    public static void setOldAirbnbListings(ArrayList<OldAirbnbListing> oldAirbnbListings) {
        RuntimeDetails.oldAirbnbListings = oldAirbnbListings;
    }

    public static void setEndDate(LocalDate endDate) {
        RuntimeDetails.endDate = endDate;
    }

    /**
     * @param maximumPrice the maximum price shown to the user
     */
    public static void setMaximumPrice(int maximumPrice) {
        RuntimeDetails.maximumPrice = maximumPrice;
    }

    /**
     * @param minimumPrice the minimum price of properties shown to the user
     */
    public static void setMinimumPrice(int minimumPrice) {
        RuntimeDetails.minimumPrice = minimumPrice;
    }

    /**
     * @param startDate sets the start date for bookings
     */
    public static void setStartDate(LocalDate startDate) {
        RuntimeDetails.startDate = startDate;
    }

    /**
     * @param totalNights sets the total nights the user wants to stay
     */
    public static void setTotalNights(long totalNights) {
        RuntimeDetails.totalNights = totalNights;
    }

    /**
     * @param validDates sets flag true if dates are valid
     */
    public static void setValidDates(boolean validDates) {
        RuntimeDetails.validDates = validDates;
    }

    /**
     * @param validPrices sets flag true if prices are valid
     */
    public static void setValidPrices(boolean validPrices) {
        RuntimeDetails.validPrices = validPrices;
    }

    /**
     * @return true if both dates and prices are valid
     */
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
