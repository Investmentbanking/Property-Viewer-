import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class InspectBoxController implements Initializable {

    private static NewAirbnbListing listing = AirbnbDataLoader.loadNewDataSet().get(0);

    @FXML private VBox root;

    @FXML private Label rating;
    @FXML private Label id, name;




    @FXML private StackPane image_holder;

    @FXML private ImageView picture_url;
    @FXML private Label property_type;

    @FXML private Circle host_picture_url;
    @FXML private Label host_id, host_name, price;

    @FXML private Button visit_button;
    @FXML private Label neighbourhood_cleansed;

    @FXML private Label neighbourhood_overview;

    @FXML private Label accommodates, bathrooms, bedrooms, beds, minimum_nights, maximum_nights, availability_365;
    @FXML private ListView<String> amenities;

    @FXML private ProgressBar review_scores_cleanliness, review_scores_checkin, review_scores_communication, review_scores_location, review_scores_value;

    private DoubleProperty size = new SimpleDoubleProperty();


    private static final Image DEFAULT_IMAGE = new Image("imagePlaceholder.jpg");


    /**
     * Assigns a new listing and reassigns values.
     * @param newListing Listing you want to set the values of.
     */
    public void setListing(NewAirbnbListing newListing){
        listing = newListing;
        assign();
        root.setVisible(true);
    }

    /**
     * Assigns all the values to the panel.
     */
    private void assign(){
        // 1
        int ratingValue = listing.getReviewScoresRating();
        if (ratingValue == -1){rating.setText("?");}
        else{rating.setText(ratingValue + "");}
        Color reviewColour = ListingBox.calculateRatingColour(listing.getReviewScoresRating());
        rating.setBackground(new Background(new BackgroundFill(reviewColour, new CornerRadii(15), Insets.EMPTY)));
        rating.setPadding(new Insets(10, 20, 10, 20));

        id.setText(listing.getId());
        name.setText(listing.getName());

        // 2
        Image pictureImage = loadImage(listing.getPictureURL().toString());
        double constant = pictureImage.getHeight()/pictureImage.getWidth();

        picture_url.setImage(pictureImage);
        property_type.setText(listing.getPropertyType());

        image_holder.maxHeightProperty().bind(image_holder.minHeightProperty());
        image_holder.maxWidthProperty().bind(image_holder.minWidthProperty());

        image_holder.minWidthProperty().bind(root.widthProperty().subtract(50));
        image_holder.minHeightProperty().bind(image_holder.minWidthProperty().multiply(constant));

        picture_url.fitWidthProperty().bind(image_holder.widthProperty());
        picture_url.fitHeightProperty().bind(image_holder.heightProperty());

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(picture_url.fitWidthProperty());
        clip.heightProperty().bind(picture_url.fitHeightProperty());

        clip.setArcHeight(50);
        clip.setArcWidth(50);
        picture_url.setClip(clip);

        // 3
        host_picture_url.setFill(new ImagePattern(loadImage(listing.getHostPictureURL().toString())));
        host_id.setText(listing.getHostID());
        host_name.setText(listing.getHostName());
        price.setText("$" + listing.getPrice() + "0");

        // 4
        neighbourhood_cleansed.setText(listing.getNeighbourhoodCleansed());

        // 5
        String overview = listing.getNeighbourhoodOverview();
        if (overview.equals("")){neighbourhood_overview.setText("EMPTY DESCRIPTION");}
        else {neighbourhood_overview.setText(overview);}

        // 6
        accommodates.setText(listing.getAccommodates() + "");
        bathrooms.setText(listing.getBathrooms() + "");
        bedrooms.setText(listing.getBedrooms() + "");
        beds.setText(listing.getBeds() + "");
        minimum_nights.setText(listing.getMinimumNights() + "");
        maximum_nights.setText(listing.getMaximumNights() + "");
        availability_365.setText(listing.getAvailability365() + "/365");
        if(listing.getAmenities().size() != 0) {
            amenities.getItems().addAll(listing.getAmenities());
        }

        // 7
        double cleanliness = ((double)listing.getReviewScoresCleanliness())/10;
        review_scores_cleanliness.setProgress(cleanliness);
        review_scores_cleanliness.setId(calcReviewID(cleanliness));

        double checkin = ((double)listing.getReviewScoresCheckin())/10;
        review_scores_checkin.setProgress(checkin);
        review_scores_checkin.setId(calcReviewID(checkin));

        double communication = ((double)listing.getReviewScoresCommunication())/10;
        review_scores_communication.setProgress(communication);
        review_scores_communication.setId(calcReviewID(communication));

        double location = ((double)listing.getReviewScoresLocation())/10;
        review_scores_location.setProgress(location);
        review_scores_location.setId(calcReviewID(location));

        double value = ((double)listing.getReviewScoresValue())/10;
        review_scores_value.setProgress(value);
        review_scores_value.setId(calcReviewID(value));
    }

    private Image loadImage(String url){
        Image image = new Image(url);
        if (image.isError()){
            image = DEFAULT_IMAGE;
        }
        return image;
    }

    /**
     * Calculated what the colour of the progress bar should be and returns the IF of the colour.
     * @param review The progress bar value. Max 1.0.
     * @return The ID of the colour.
     */
    private String calcReviewID(double review){
        if (review == 1){
            return "green";
        }
        else if (review >= 0.6){
            return "orange";
        }
        else{
            return "red";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setVisible(false);
    }

    @FXML
    private void openGoogleMaps(ActionEvent event) throws IOException{
        double latitude = listing.getLatitude();
        double longitude = listing.getLongitude();

        URI uri;
        try {
            uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void bindAllToScene(Pane root){
        for (Node node : root.getChildrenUnmodifiable()){
            if (node instanceof Pane){
                bindAllToScene((Pane)node);
            }

            // epic code to bind the node to the scene so everything scales
        }
    }

}