
/**
 * Creates amenities relation statistics using the new data set.
 *
 * @author Ayesha Dorani
 */
public class AmenitiesStatisticsCollector {

    public static int getBedroomProperties(){
        int count = 0;
        for(NewAirbnbListing listing : Statistics.getNewListings()) {
            if(listing.getBedrooms() > 1) {
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
                    System.out.println(listing.getAmenities());
                    System.out.println("oven: " + count);
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
}
