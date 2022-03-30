/**
 * An information storage class which stores info for a singular borough as well as all the boroughs.
 * The info includes:
 *      - Name of the borough
 *      - The average price of all listings in the borough
 *      - The total amount of listings for the borough
 *      - he total amount of available listings for the borough (Where 365 Availability isn't 0)
 *      - The total amount of 365 days available (The amount of days the listing is available per listing)
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class Borough {

    private final String name;        // Name of the borough
    private final int avgPrice;       // The average price of all listings in the borough
    private final int count;          // The total amount of listings for the borough
    private final int available;      // The total amount of available listings for the borough (Where 365 Availability isn't 0)
    private final int availableDays;  // The total amount of 365 days available (The amount of days the listing is available per listing)

    /**
     * Storage container for the average price of all Boroughs.
     * Ready to be accessed to retrieve the desired methods.
     */
    public static final MinMaxTotalStorage avgPriceStorage = new MinMaxTotalStorage();

    /**
     * Storage container for the total amount of properties for of all Boroughs.
     * Ready to be accessed to retrieve the desired methods.
     */
    public static final MinMaxTotalStorage countStorage = new MinMaxTotalStorage();

    /**
     * Storage container for the total amount of available properties for of all Boroughs.
     * Ready to be accessed to retrieve the desired methods.
     */
    public static final MinMaxTotalStorage availableStorage = new MinMaxTotalStorage();

    /**
     * Storage container for the total value of available days of all properties for of all Boroughs.
     * Ready to be accessed to retrieve the desired methods.
     */
    public static final MinMaxTotalStorage availableDaysStorage = new MinMaxTotalStorage();

    /**
     * The constructor for a Borough which stores certain values of it based off input and calculation of the inputs.
     *
     * @param name Name of the Borough.
     * @param avgPrice The average price for all the properties in the Borough.
     * @param count The total amount of properties in the Borough.
     * @param available The total amount of available properties in the Borough.
     * @param availableDays The total amount of available days of each property added together.
     */
    public Borough(String name, int avgPrice, int count, int available, int availableDays) {
        this.name = name;

        this.avgPrice = avgPrice;
        avgPriceStorage.set(avgPrice);

        this.count = count;
        countStorage.set(count);

        this.available = available;
        availableStorage.set(available);

        this.availableDays = availableDays;
        availableDaysStorage.set(availableDays);
    }

    /**
     * Get the name of the Borough.
     *
     * @return The name of the Borough.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the average price of the Borough.
     *
     * @return The average price of the Borough.
     */
    public int getAvgPrice() {
        return avgPrice;
    }

    /**
     * Get the total count of properties in the Borough.
     *
     * @return The total count of properties in the Borough
     */
    public int getCount() {
        return count;
    }

    /**
     * Get the total amount of available properties in the Borough.
     *
     * @return the total amount of available properties in the Borough.
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Get the total of available days from all the properties in the Borough.
     *
     * @return The total of available days from all the properties in the Borough.
     */
    public int getAvailableDays() {
        return availableDays;
    }
}
