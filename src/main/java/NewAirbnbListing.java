import java.net.URL;
import java.util.ArrayList;

/**
 * Represents one listing of a property for rental on Airbnb for the new CSV file.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */ 

public class NewAirbnbListing {

    private final String id;                        // the id of the property
    private final String name;                      // the name of the property
    private final String neighbourhoodOverview;     // A description of the neighborhood
    private final URL pictureURL;                   // A picture of the property
    private final String hostID;                    // the id of the host
    private final String hostName;                  // the name of the host
    private final String hostResponseTime;          // the host's response time
    private final URL hostPictureURL;               // the host's picture
    private final int hostListings;                 // the number of the host's listings
    private final String neighbourhoodCleansed;     // the borough of london
    private final double latitude;                  // the latitude of the property
    private final double longitude;                 // the longitude of the property
    private final String propertyType;              // The type of property, either "Private room" or "Entire Home/apt".
    private final int accommodates;                 // the number of people allowed in the property
    private final double bathrooms;                 // the number of bathrooms
    private final int bedrooms;                     // the number of bedrooms
    private final int beds;                         // the number of beds
    private final ArrayList<String> amenities;      // A list of all the amenities in the property
    private final double price;                     // The price per night's stay
    private final int minimumNights;                // The minimum number of nights the listed property must be booked for
    private final int maximumNights;                // the maximum number of nights the listed property can be booked for
    private final int numberOfReviews;              // the number of reviews for this specific property
    private final int availability365;              // The total number of days in the year that the property is available for
    private final int reviewScoresRating;           // The review score rating
    private final int reviewScoresCleanliness;      // The cleanliness score
    private final int reviewScoresCheckin;          // The checkin score
    private final int reviewScoresCommunication;    // The communication score
    private final int reviewScoresLocation;         // the location score
    private final int reviewScoresValue;            // the value score

    /**
     * The main constructor of the airbnb listing
     *
     * @param id the id
     * @param name the name
     * @param neighborhood_overview the overview of the neighborhood
     * @param picture_url the picture of the property (URL format)
     * @param host_id the id of the host
     * @param host_name the name of the host
     * @param host_response_time the avg response time of the host
     * @param host_picture_url the picture of the host
     * @param host_listings the other listings that the host has
     * @param neighbourhood_cleansed the borough of the property
     * @param latitude the latitude
     * @param longitude the longitude
     * @param property_type the type of the property aka (house, apartment etc...)
     * @param accommodates the number of people allowed in the property
     * @param bathrooms the number of bathrooms
     * @param bedrooms the number of bedrooms
     * @param beds the number of beds
     * @param amenities the amenities in the property
     * @param price the price of the property
     * @param minimumNights the minimum nights allowed in the property
     * @param maximumNights the max nights allowed in the property
     * @param numberOfReviews the total number of reviews
     * @param availability365 the availability of the property 365 days a year
     * @param review_scores_rating the rating score
     * @param review_scores_cleanliness the cleanliness score
     * @param review_scores_checkin the checkin score
     * @param review_scores_communication the communication score
     * @param review_scores_location the location score
     * @param review_scores_value the value score
     */
    public NewAirbnbListing(String id, String name, String neighborhood_overview,
                         URL picture_url, String host_id, String host_name, String host_response_time, URL host_picture_url,
                         int host_listings, String neighbourhood_cleansed, double latitude, double longitude, String property_type,
                         int accommodates, double bathrooms, int bedrooms, int beds, ArrayList<String> amenities, double price, int minimumNights,
                         int maximumNights, int numberOfReviews , int availability365, int review_scores_rating, int review_scores_cleanliness, int review_scores_checkin,
                         int review_scores_communication, int review_scores_location, int review_scores_value) {
        this.id = id;
        this.name = name;
        this.neighbourhoodOverview = neighborhood_overview;
        this.pictureURL = picture_url;
        this.hostID = host_id;
        this.hostName = host_name;
        this.hostResponseTime = host_response_time;
        this.hostPictureURL = host_picture_url;
        this.hostListings = host_listings;
        this.neighbourhoodCleansed = neighbourhood_cleansed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.propertyType = property_type;
        this.accommodates = accommodates;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.beds = beds;
        this.amenities = amenities;
        this.price = price;
        this.minimumNights = minimumNights;
        this.maximumNights = maximumNights;
        this.numberOfReviews = numberOfReviews;
        this.availability365 = availability365;
        this.reviewScoresRating = review_scores_rating;
        this.reviewScoresCleanliness = review_scores_cleanliness;
        this.reviewScoresCheckin = review_scores_checkin;
        this.reviewScoresCommunication = review_scores_communication;
        this.reviewScoresLocation = review_scores_location;
        this.reviewScoresValue = review_scores_value;
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
     * A simple getter method to get the field hostID
     *
     * @return the String representation of the hostId
     */
    public String getHostID() {
        return hostID;
    }

    /**
     * A simple getter method to get the field hostName
     *
     * @return the String representation of the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * A simple getter method to get the field neighbourhoodCleansed
     *
     * @return the String representation of the borough
     */
    public String getNeighbourhoodCleansed() {
        return neighbourhoodCleansed;
    }

    /**
     * A simple getter method to get the field latitude
     *
     * @return the double representation of the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * A simple getter method to get the field longitude
     *
     * @return the double representation of the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * A simple getter method to get the field propertyType
     *
     * @return the String representation of the type of the property
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * A simple getter method to get the field price
     *
     * @return the double representation of the price
     */
    public double getPrice() {
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
     * A simple getter method to get the field availability365
     *
     * @return the int representation of the availability all year a number between 0 and 365
     */
    public int getAvailability365() {
        return availability365;
    }

    /**
     * A simple getter method to get the field neighbourhoodOverview
     *
     * @return the String representation of neighbourhood overview
     */
    public String getNeighbourhoodOverview() {
        return neighbourhoodOverview;
    }

    /**
     * A simple getter method to get the field pictureURL
     *
     * @return the url representation of picture
     */
    public URL getPictureURL() {
        return pictureURL;
    }

    /**
     * A simple getter method to get the hostResponseTime
     *
     * @return the String representation of host response time
     */
    public String getHostResponseTime() {
        return hostResponseTime;
    }

    /**
     * A simple getter method to get the field hostPictureURL
     *
     * @return the url representation of picture
     */
    public URL getHostPictureURL() {
        return hostPictureURL;
    }

    /**
     * A simple getter method to get the field hostListings
     *
     * @return the int representation of the number of listings per host
     */
    public int getHostListings() {
        return hostListings;
    }

    /**
     * A simple getter method to get the field accommodates
     *
     * @return the int representation of the accommodates
     */
    public int getAccommodates() {
        return accommodates;
    }

    /**
     * A simple getter method to get the field bathrooms
     *
     * @return the int representation of the number of bathrooms
     */
    public double getBathrooms() {
        return bathrooms;
    }

    /**
     * A simple getter method to get the field bedrooms
     *
     * @return the int representation of the number of bedrooms
     */
    public int getBedrooms() {
        return bedrooms;
    }

    /**
     * A simple getter method to get the field beds
     *
     * @return the int representation of the number of beds
     */
    public int getBeds() {
        return beds;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    /**
     * A simple getter method to get the field maximumNights
     *
     * @return the int representation of the number of nights allowed
     */
    public int getMaximumNights() {
        return maximumNights;
    }

    /**
     * A simple getter method to get the field numberOfReviews
     *
     * @return the int representation of the number of reviews
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public int getReviewScoresRating() {
        return reviewScoresRating;
    }

    /**
     * A simple getter method to get the field reviewScoresCleanliness
     *
     * @return the int representation of the cleanliness score
     */
    public int getReviewScoresCleanliness() {
        return reviewScoresCleanliness;
    }

    /**
     * A simple getter method to get the field reviewScoresCheckin
     *
     * @return the int representation of the checkin score
     */
    public int getReviewScoresCheckin() {
        return reviewScoresCheckin;
    }

    /**
     * A simple getter method to get the field reviewScoresCommunication
     *
     * @return the int representation of the communication score
     */
    public int getReviewScoresCommunication() {
        return reviewScoresCommunication;
    }

    /**
     * A simple getter method to get the field reviewScoresLocation
     *
     * @return the int representation of the location score
     */
    public int getReviewScoresLocation() {
        return reviewScoresLocation;
    }

    /**
     * A simple getter method to get the field reviewScoresValue
     *
     * @return the int representation of the review score
     */
    public int getReviewScoresValue() {
        return reviewScoresValue;
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
                ", neighbourhood overview='" + neighbourhoodOverview + '\'' +
                ", picture url='" + pictureURL + '\'' +
                ", host id='" + hostID + '\'' +
                ", host name='" + hostName + '\'' +
                ", host response time='" + hostResponseTime + '\'' +
                ", host's picture (url)='" + hostPictureURL + '\'' +
                ", host's listings='" + hostListings + '\'' +
                ", borough ='" + neighbourhoodCleansed + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + propertyType + '\'' +
                ", accommodates =" + accommodates +
                ", bathrooms =" + bathrooms +
                ", bedrooms =" + bedrooms +
                ", beds =" + beds +
                ", amenities =" + amenities +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", maxNights=" + maximumNights +
                ", number of reviews=" + numberOfReviews +
                ", availability365=" + availability365 +
                ", reviewScoresRating=" + reviewScoresRating +
                ", reviewScoresCleanliness=" + reviewScoresCleanliness +
                ", reviewScoresCheckin=" + reviewScoresCheckin +
                ", reviewScoresCommunication=" + reviewScoresCommunication +
                ", reviewScoresLocation=" + reviewScoresLocation +
                ", reviewScoresValue=" + reviewScoresValue +
                '}';
    }

}
