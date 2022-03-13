import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AirbnbDataLoader {

    private final ArrayList<AirbnbListing> listings;

    private static int minPrice;
    private static int maxPrice;

    public AirbnbDataLoader() {
        this.listings = new ArrayList<>();
    }

    /**
     * Filters the properties within the given price range.
     * @return the new ArrayList with the specific AirbnbListings.
     */
    public static ArrayList<AirbnbListing> loadRange() {
        ArrayList<AirbnbListing> list = load();
        list.removeIf(n -> (n.getPrice() < minPrice));
        list.removeIf(n -> (n.getPrice() > maxPrice));
        return list;
    }

    public static void setMin(int minPrice) {
        AirbnbDataLoader.minPrice = minPrice;
    }

    public static void setMax(int maxPrice) {
        AirbnbDataLoader.maxPrice = maxPrice;
    }

    /**
     * Converts string Array from csv reader to airbnb listing.
     * @param line String array which contains the listing data.
     * @return the AirbnbListing object.
     * @throws MalformedURLException
     */
    private static AirbnbListing createListing(String[] line) throws MalformedURLException {
            String id = line[0];
            String name = line[1];
            String neighborhood_overview = line[2];
            URL picture_url = convertURL(line[3]);
            String host_id = line[4];
            String host_name = line[5];
            String host_response_time = line[6];
            URL host_picture_url = convertURL(line[7]);
            int host_listings = convertInt(line[8]);
            String neighbourhood_cleansed = line[9];
            double latitude = convertDouble(line[10]);
            double longitude = convertDouble(line[11]);
            String property_type = line[12];
            int accommodates = convertInt(line[13]);
            double bathrooms = convertDouble(line[14]);
            int bedrooms = convertInt(line[15]);
            int beds = convertInt(line[16]);
            ArrayList<String> amenities = convertArrayListOfString(line[17]);
            double price = convertMoney(line[18]);
            int minimumNights = convertInt(line[19]);
            int maximumNights = convertInt(line[20]);
            int availability365 = convertInt(line[21]);
            int numberOfReviews = convertInt(line[22]);
            int review_scores_rating = convertInt(line[23]);
            int review_scores_cleanliness = convertInt(line[24]);
            int review_scores_communication = convertInt(line[25]);
            int review_scores_location = convertInt(line[26]);
            double review_scores_value = convertDouble(line[27]);

            AirbnbListing listing = new AirbnbListing(id, name, neighborhood_overview,
                    picture_url, host_id, host_name, host_response_time, host_picture_url,
                    host_listings, neighbourhood_cleansed, latitude, longitude, property_type,
                    accommodates, bathrooms, bedrooms, beds, amenities, price, minimumNights, maximumNights,
                    availability365, numberOfReviews, review_scores_rating, review_scores_cleanliness,
                    review_scores_communication, review_scores_location, review_scores_value
            );
            return listing;
    }

    /** 
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     * @return
     */
    public static ArrayList<AirbnbListing> load() {
        System.out.print("Begin loading Airbnb london dataset...");
            ArrayList<AirbnbListing> listings = new ArrayList<>();
        try{
            URL url = AirbnbDataLoader.class.getResource("listings.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();

            while ((line = reader.readNext()) != null)
            {listings.add(createListing(line));}
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

    private static double convertMoney(String money) {
        money = money.substring(1, money.indexOf("."));
        money = money.replace(",", "");
        return Double.parseDouble(money);
    }


    public ArrayList<AirbnbListing> getListings() {
        return listings;
    }

    public String getListingById(String id){
        for(AirbnbListing listing : listings){
            if(id.equals(listing.getId())){
                return listing.toString();
            }
        }
        return "this listing was not found";
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private static Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private static Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

    /**
     * Convert a string into an url
     * @param urlString The string to be converted into an url
     * @return The url or null if the link is invalid.
     * @throws MalformedURLException if the link is invalid
     */
    private static URL convertURL(String urlString) throws MalformedURLException {
        if (urlString != null && !urlString.trim().equals("")) {
            return new URL(urlString);
        }
        return null;
    }


    private static ArrayList<String> convertArrayListOfString(String amenities) {
        if(amenities != null && !amenities.trim().equals("")) {
            amenities = amenities.replace("\"", ""); // Remove the double quotes
            String[] amenitiesArray = amenities.split("\\s*,\\s*");
            return new ArrayList<>(Arrays.asList(amenitiesArray));
        }
        return null;
    }

    //public static void main(String[] args) {
       // AirbnbDataLoader a = new AirbnbDataLoader();
       // a.load();
       // System.out.println(a.getListings().size());
//        System.out.println(a.getListingById("13712199"));
//        System.out.println("test works");
    //}
}
