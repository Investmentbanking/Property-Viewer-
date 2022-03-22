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
 * @version 22.03.2022
 */
public class Borough {

    private String name;                                      // Name of the borough

    private int avgPrice;                                     // The average price of all listings in the borough
    private static MinMaxTotalStorage avgPriceStorage = new MinMaxTotalStorage();

    private int count;                                        // The total amount of listings for the borough
    private static MinMaxTotalStorage countStorage = new MinMaxTotalStorage();

    private int available;                                    // The total amount of available listings for the borough (Where 365 Availability isn't 0)
    private static MinMaxTotalStorage availableStorage = new MinMaxTotalStorage();

    private int availableDays;                                // The total amount of 365 days available (The amount of days the listing is available per listing)
    private static MinMaxTotalStorage availableDaysStorage = new MinMaxTotalStorage();

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

    public String getName() {
        return name;
    }

    public int getAvgPrice() {
        return avgPrice;
    }
    public MinMaxTotalStorage getAvgPriceStat() {
        return avgPriceStorage;
    }

    public int getCount() {
        return count;
    }
    public MinMaxTotalStorage getCountStat() {
        return countStorage;
    }

    public int getAvailable() {
        return available;
    }
    public MinMaxTotalStorage getAvailableStat() {
        return availableStorage;
    }

    public int getAvailableDays() {
        return availableDays;
    }
    public MinMaxTotalStorage getAvailableDaysStat() {
        return availableDaysStorage;
    }
}