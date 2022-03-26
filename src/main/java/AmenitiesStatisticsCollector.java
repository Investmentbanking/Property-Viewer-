
/**
 * Creates amenities related statistics using the new data set.
 *
 * @author Ayesha Dorani
 */
public class AmenitiesStatisticsCollector {



    public static int getGardenOrBackyardProperties(){
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
     *
     * @return
     */
    public static int getMoreThan3BedroomProperties(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBedrooms() > 3) {
                count++;
            }
        }
        return count;
    }

    public static int getMoreThan1BedProperties(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBeds() >= 2) {
                count++;
            }
        }
        return count;
    }

    public static int getMoreThan2Bathrooms(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBeds() > 2) {
                count++;
            }
        }
        return count;
    }

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



    public static int mostAmenitiesID(){
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
