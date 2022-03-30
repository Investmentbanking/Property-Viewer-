
/**
 * Creates amenities related statistics using the new data set for the statistics panel.
 *
 * @author Ayesha Dorani (K2036136)
 * @version 30.03.2022
 */
public class AmenitiesStatisticsCollector {

    /**
     * Checks if amenities cell for listing is not blank,
     * if not, adds the size of amenities of each listing to count.
     *
     * @return the total number of amenities.
     */
    public static int getTotalAmenities() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getAmenities() != null) {
                count += listing.getAmenities().size();
            }
        }
        return count;
    }

    /**
     * Checks if amenities cell for listing is not blank,
     * if not, increments count each time amenities contains
     * 'Garden or backyard'.
     *
     * @return the total number of listings with 'Garden or backyard'.
     */
    public static int getGardenOrBackyardProperties() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getAmenities() != null) {
                if (listing.getAmenities().contains("Garden or backyard")) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if number of bedrooms for each listing is more than 3,
     * if so, increments the count.
     *
     * @return the total number of properties with more than 3 bedrooms.
     */
    public static int getMoreThan3BedroomProperties() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBedrooms() > 3) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if number of beds for each listing is more than or
     * equal to 2, if so, increments the count.
     *
     * @return the total number of properties with 2 or more beds.
     */
    public static int getMoreThan1BedProperties() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBeds() >= 2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if number of bathrooms for each listing is more than 2,
     * if so, increments the count.
     *
     * @return total number of properties with more than 2 bathrooms.
     */
    public static int getMoreThan2Bathrooms() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBathrooms() > 2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if amenities cell for listing is not blank,
     * if not, increments count each time listing contains
     * an oven.
     *
     * @return the total number of properties containing an oven.
     */
    public static int getPropertiesContainingOven() {
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getAmenities() != null) {
                if (listing.getAmenities().contains("Oven")) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if cell amenities for listing is not blank,
     * if not, checks the size of amenities for listing.
     * If this value is higher than previous value, get the id
     * of the listing.
     *
     * @return the id of the listing with the most amenities.
     */
    public static int mostAmenitiesID() {
        int most = Statistics.getNewListings().get(0).getAmenities().size();
        int id = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if (listing.getAmenities() != null) {
                int total = listing.getAmenities().size();
                if (total > most) {
                    most = total;
                    id = Integer.parseInt(listing.getId());
                }
            }
        }
        return id;
    }

    /**
     * Checks if amenities cell for listing is not blank,
     * if not, adds the number of amenities of each listing to the total.
     * Increments the size each time.
     *
     * @return the total number of amenities divided by the number of listings.
     */
    public static int averageNumberOfAmenities() {
        int total = 0;
        int size = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getAmenities() != null) {
                total += listing.getAmenities().size();
                size++;
            }
        }
        return total/size;
    }
}
