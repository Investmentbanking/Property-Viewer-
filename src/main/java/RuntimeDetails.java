import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The users entered search details
 * stores start and end dates, price range
 * validity flags, property listings and total nights
 * stored in static fields in this class
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
    // and set valid prices flag to true
    static{
        minimumPrice = 0;
        maximumPrice = 10000;
        validPrices = true;
    }


    /**
     * A simple getter method to return the arraylist of new listings
     * @return the new arraylist of listings
     */
    public static ArrayList<NewAirbnbListing> getNewAirbnbListings() {
        return newAirbnbListings;
    }

    /**
     * A simple getter method to return the arraylist of old listings
     * @return the old arraylist of listings
     */
    public static ArrayList<OldAirbnbListing> getOldAirbnbListings() {
        return oldAirbnbListings;
    }

    /**
     * A simple getter method to return the startDate field
     * @return selected start date
     */
    public static LocalDate getStartDate(){
        return startDate;
    }

    /**
     * A simple getter method to return the date the users selected end date
     * @return selected end date
     */
    public static LocalDate getEndDate() {
        return endDate;
    }

    /**
     * A simple getter method to return the validity flag for dates
     * @return true if dates are valid
     */
    public static boolean isValidDates() {
        return validDates;
    }

    /**
     * A simple getter method to return the validity flag for prices
     * @return true if prices are valid
     */
    public static boolean isValidPrices() {
        return validPrices;
    }

    /**
     * A simple getter method to return the maximum price selected by the user
     * @return the maximum price selected
     */
    public static int getMaximumPrice() {
        return maximumPrice;
    }

    /**
     * A simple getter method to return the minimum price selected by the user
     * @return the minimum price selected
     */
    public static int getMinimumPrice() {
        return minimumPrice;
    }

    /**
     * A simple getter method to return the total nights from start to end dates
     * @return the amount of nights
     */
    public static long getTotalNights() {
        return totalNights;
    }

    /**
     * A simple setter method to set the input to the new airbnb listings to the arraylist
     * @param newAirbnbListings The new arraylist of listings
     */
    public static void setNewAirbnbListings(ArrayList<NewAirbnbListing> newAirbnbListings) {
        RuntimeDetails.newAirbnbListings = newAirbnbListings;
    }

    /**
     * A simple setter method to set the input to the old airbnb listings arraylist
     * @param oldAirbnbListings The old arraylist of listings
     */
    public static void setOldAirbnbListings(ArrayList<OldAirbnbListing> oldAirbnbListings) {
        RuntimeDetails.oldAirbnbListings = oldAirbnbListings;
    }

    /**
     * A simple setter method to set the end date selected by the user as a localDate
     * @param endDate the end date selected by the user for bookings
     */
    public static void setEndDate(LocalDate endDate) {
        RuntimeDetails.endDate = endDate;
    }

    /**
     * A simple setter method to set the maximum price selected by the user as an integer
     * @param maximumPrice the maximum price of properties shown to the user
     */
    public static void setMaximumPrice(int maximumPrice) {
        RuntimeDetails.maximumPrice = maximumPrice;
    }

    /**
     * A simple setter method to set the minimum price selected by the user as an integer
     * @param minimumPrice the minimum price of properties shown to the user
     */
    public static void setMinimumPrice(int minimumPrice) {
        RuntimeDetails.minimumPrice = minimumPrice;
    }

    /**
     * A simple setter method to set the start date for booking a property
     * @param startDate start date for bookings
     */
    public static void setStartDate(LocalDate startDate) {
        RuntimeDetails.startDate = startDate;
    }

    /**
     * A simple setter method to set the total nights the user will stay,
     * calculated from the selected start dates and end dates.
     * @param totalNights total nights the user wants to stay
     */
    public static void setTotalNights(long totalNights) {
        RuntimeDetails.totalNights = totalNights;
    }

    /**
     * A simple setter method for the boolean flag representing the dates' validity
     * @param validDates true if dates are valid
     */
    public static void setValidDates(boolean validDates) {
        RuntimeDetails.validDates = validDates;
    }

    /**
     * A simple setter method for the boolean flag representing the prices' validity
     * @param validPrices sets flag true if prices are valid
     */
    public static void setValidPrices(boolean validPrices) {
        RuntimeDetails.validPrices = validPrices;
    }

    /**
     * A simple setter method for the boolean flag representing if both prices and dates are valid
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
     * A string used to output the selected price range inputted by the user,
     * in the form "'min price' to 'max price'"
     * @return the price range as string
     */
    public static String getPriceRange()
    {
        return (minimumPrice + " to " + maximumPrice);
    }
}
