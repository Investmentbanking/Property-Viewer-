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
    private final double bathrooms;                    // the number of bathrooms
    private final int bedrooms;                     // the number of bedrooms
    private final int beds;                         // the number of beds
    private final ArrayList<String> amenities;      // A list of all the amenities in the property
    private final double price;                        // The price per night's stay
    private final int minimumNights;                // The minimum number of nights the listed property must be booked for
    private final int maximumNights;                // the maximum number of nights the listed property can be booked for
    private final int numberOfReviews;              // The number of reviews for this property
    private final int availability365;              // The total number of days in the year that the property is available for
    private final int reviewScoresRating;           // The review score rating
    private final int reviewScoresCleanliness;      // The cleanliness score
    private final int reviewScoresCommunication;    // The communication score
    private final int reviewScoresLocation;         // the location score
    private final double reviewScoresValue;         // the value score


    public NewAirbnbListing(String id, String name, String neighborhood_overview,
                         URL picture_url, String host_id, String host_name, String host_response_time, URL host_picture_url,
                         int host_listings, String neighbourhood_cleansed, double latitude, double longitude, String property_type,
                         int accommodates, double bathrooms, int bedrooms, int beds, ArrayList<String> amenities, double price, int minimumNights,
                         int maximumNights, int availability365, int numberOfReviews, int review_scores_rating, int review_scores_cleanliness,
                         int review_scores_communication, int review_scores_location, double review_scores_value) {
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
        this.availability365 = availability365;
        this.numberOfReviews = numberOfReviews;
        this.reviewScoresRating = review_scores_rating;
        this.reviewScoresCleanliness = review_scores_cleanliness;
        this.reviewScoresCommunication = review_scores_communication;
        this.reviewScoresLocation = review_scores_location;
        this.reviewScoresValue = review_scores_value;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHostID() {
        return hostID;
    }

    public String getHostName() {
        return hostName;
    }

    public String getNeighbourhoodCleansed() {
        return neighbourhoodCleansed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public double getPrice() {
        return price;
    }

    public int getMinimumNights() {
        return minimumNights;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public int getAvailability365() {
        return availability365;
    }

    public String getNeighbourhoodOverview() {
        return neighbourhoodOverview;
    }

    public URL getPictureURL() {
        return pictureURL;
    }

    public String getHostResponseTime() {
        return hostResponseTime;
    }

    public URL getHostPictureURL() {
        return hostPictureURL;
    }

    public int getHostListings() {
        return hostListings;
    }

    public int getAccommodates() {
        return accommodates;
    }

    public double getBathrooms() {
        return bathrooms;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBeds() {
        return beds;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public int getMaximumNights() {
        return maximumNights;
    }

    public int getReviewScoresRating() {
        return reviewScoresRating;
    }

    public int getReviewScoresCleanliness() {
        return reviewScoresCleanliness;
    }

    public int getReviewScoresCommunication() {
        return reviewScoresCommunication;
    }

    public int getReviewScoresLocation() {
        return reviewScoresLocation;
    }

    public double getReviewScoresValue() {
        return reviewScoresValue;
    }

    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + hostID + '\'' +
                ", host_name='" + hostName + '\'' +
                ", neighbourhood='" + neighbourhoodCleansed + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + propertyType + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", availability365=" + availability365 +
                '}';
    }
}
