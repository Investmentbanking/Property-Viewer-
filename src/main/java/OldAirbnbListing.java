/**
 * Represents one listing of a property for rental on Airbnb for the old CSV file.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */
public class OldAirbnbListing {

    /**
     * The id and name of the individual property
     */
    private final String id;
    private final String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private final String host_id;
    private final String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private final String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private final double latitude;
    private final double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private final String room_type;

    /**
     * The price per night's stay
     */
    private final int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private final int minimumNights;
    private final int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private final String lastReview;
    private final double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private final int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private final int availability365;

    /**
     * The main constructor of the airbnb listing
     *
     * @param id the id
     * @param name the name
     * @param host_id the id of the host
     * @param host_name the name of the host
     * @param neighbourhood the neighbourhood
     * @param latitude the latitude
     * @param longitude the longitude
     * @param room_type the type of the room
     * @param price the price of the property
     * @param minimumNights the minimum nights allowed in the property
     * @param numberOfReviews the total number of reviews
     * @param lastReview the last review of this property
     * @param reviewsPerMonth the total of reviews for this property per month
     * @param calculatedHostListingsCount the total listings of the host of the property.
     * @param availability365 the availability of the property 365 days a year
     */
    public OldAirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }

    /**
     * A simple getter method to get the id field
     *
     * @return the String representation of the id
     */
    public String getId() {
        return id;
    }

    /**
     * A simple getter method to get the name field
     *
     * @return the String representation of the name
     */
    public String getName() {
        return name;
    }

    /**
     * A simple getter method to get the field neighbourhood
     *
     * @return the String representation of the neighbourhood
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * A simple getter method to get the field room_type
     *
     * @return the String representation of the type of the property
     */
    public String getRoom_type() {
        return room_type;
    }

    /**
     * A simple getter method to get the field price
     *
     * @return the double representation of the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * A simple getter method to get the field minimumNights
     *
     * @return the int representation of the minimum nights allowed.
     */
    public int getMinimumNights() {
        return minimumNights;
    }

    /**
     * A simple getter method to get the field numberOfReviews
     *
     * @return the int representation of the number of reviews
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * A simple getter method to get the field reviewsPerMonth
     *
     * @return the double representation of the reviews per month
     */
    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    /**
     * A simple getter method to get the field availability365
     *
     * @return the int representation of the availability all year a number between 0 and 365
     */
    public int getAvailability365() {
        return availability365;
    }

    /**
     * Overriding the method toString to produce a readable version of the property
     *
     * @return the string representation of the property
     */
    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}
