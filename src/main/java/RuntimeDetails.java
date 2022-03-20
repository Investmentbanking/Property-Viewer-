import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RuntimeDetails {

    private static LocalDate startDate;
    private static LocalDate endDate;
    private static long totalNights;
    private static boolean validPrices;
    private static boolean validDates;
    private static int minimumPrice;
    private static int maximumPrice;

    static{
        minimumPrice = 0;
        maximumPrice = 1000;

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

    public static boolean setTotalNights()
    {
        if(startDate != null && endDate != null)
        {
            totalNights = startDate.until(endDate, ChronoUnit.DAYS);
            System.out.println(totalNights);
            validDates = true;
            return true;
        }
        else {
            return false;
        }
    }

    public static String getPriceRange()
    {
        return (minimumPrice + " to " + maximumPrice);
    }
}
