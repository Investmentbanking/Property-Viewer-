import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A pane class which is a box representing a specific listing. The box will display the image of the
 * property when called, and the colour represents the rating value.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class ListingBox extends StackPane {

    private NewAirbnbListing listing;

    private Rectangle clip;         // Clips rating
    private Image image = null;     // Put in imageView

    private boolean isImageLoaded;

    // Visible components
    private Rectangle rating;
    private ImageView imageView;

    private Color ratingColour, boxColour;

    public static final Color VERY_HIGH_REVIEW_COLOUR = Color.AQUA;
    public static final Color HIGH_REVIEW_COLOUR = Color.GREEN;
    public static final Color MEDIUM_REVIEW_COLOUR = Color.YELLOW;
    public static final Color LOW_REVIEW_COLOUR = Color.RED;
    public static final Color VERY_LOW_REVIEW_COLOUR = Color.BLUE;
    public static final Color UNKNOWN_REVIEW_COLOUR = Color.PURPLE;
    public static final Color OUT_OF_RANGE_COLOUR = Color.DARKGRAY;
    private static final double OVERLAY_TRANSPARENCY = 0.5;

    private static final int HIGH_REVIEW_BOUND = 100;
    private static final int LOW_REVIEW_BOUND = 80;

    private static final Image DEFAULT_IMAGE = new Image("imagePlaceholder.jpg");

    private FillTransition filltRatingIn, filltRatingOut;
    private ScaleTransition stImageIn, stImageOut, stRatingIn, stRatingOut;
    private FadeTransition ftRatingIn, ftRatingOut;

    /**
     * Constructor for a listing box for a specific listing, it will display the property image and be the colour of the rating.
     * @param listing The listing to be represented by the object.
     */
    public ListingBox(NewAirbnbListing listing) {
        this.listing = listing;

        isImageLoaded = false;

        ratingColour = calculateRatingColour(listing.getReviewScoresRating());
        boxColour = ratingColour;

        clip = new Rectangle();//20, 20, boxColour);
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());

        clip.setArcWidth(40);
        clip.setArcHeight(40);

        imageView = new ImageView(DEFAULT_IMAGE);
        imageView.fitHeightProperty().bind(clip.heightProperty());
        imageView.fitWidthProperty().bind(clip.heightProperty());
        imageView.setClip(clip);

        // Full cover colour
        rating = new Rectangle(20, 20, boxColour);
        rating.widthProperty().bind(widthProperty());
        rating.heightProperty().bind(heightProperty());
        rating.setArcWidth(40);
        rating.setArcHeight(40);

        maxHeightProperty().bind(minWidthProperty());
        maxWidthProperty().bind(minWidthProperty());
        minHeightProperty().bind(minWidthProperty());

        getChildren().addAll(imageView, rating);//, text);

        setOnMouseEntered(this::highlight);
        setOnMouseExited(this::unhighlight);

        filltRatingIn = new FillTransition(Duration.millis(100), rating, boxColour, boxColour.darker());
        filltRatingOut = new FillTransition(Duration.millis(100), rating, boxColour.darker(), boxColour);

        stImageIn = new ScaleTransition(Duration.millis(100), imageView);
        stImageIn.setToX(1.2);
        stImageIn.setToY(1.2);
        stImageOut = new ScaleTransition(Duration.millis(100), imageView);
        stImageOut.setToX(1);
        stImageOut.setToY(1);

        stRatingIn = new ScaleTransition(Duration.millis(100), rating);
        stRatingIn.setToX(1.2);
        stRatingIn.setToY(1.2);
        stRatingOut = new ScaleTransition(Duration.millis(100), rating);
        stRatingOut.setToX(1);
        stRatingOut.setToY(1);

        ftRatingIn = new FadeTransition(Duration.millis(100), rating);
        ftRatingIn.setFromValue(1);
        ftRatingIn.setToValue(0);
        ftRatingOut = new FadeTransition(Duration.millis(100), rating);
        ftRatingOut.setFromValue(0);
        ftRatingOut.setToValue(1);
    }

    /**
     * Get the listing representing the box.
     * @return the listing representing the box.
     */
    public NewAirbnbListing getListing() {
        return listing;
    }

    /**
     * Get if the image is loaded or is currently loading.
     * @return True if image has been set to load.
     */
    public boolean isImageLoaded() {
        return isImageLoaded;
    }

    /**
     * Load the image in the box.
     */
    public void loadImage(){
        if (!isImageLoaded) {
            isImageLoaded = true;
            image = new Image(listing.getPictureURL().toString(), true);
            imageView.setImage(image);
        }
    }

    /**
     * Unload the image in the box. If the image is still loading it will cancel it. Default image will be restored.
     */
    public void unloadImage(){
        if (isImageLoaded) {
            isImageLoaded = false;
            image.cancel();
            imageView.setImage(DEFAULT_IMAGE);
        }
    }

    /**
     * Cancel the loading of the image.
     */
    public void cancelLoad(){
        image.cancel();
    }

    /**
     * Highlight the box on mouse enter.
     * @param event MouseEvent call.
     */
    private void highlight(MouseEvent event){
        filltRatingIn.play();
        stImageIn.play();
        stRatingIn.play();
        ftRatingIn.play();
    }

    /**
     * Remove highlight of the box on mouse exit.
     * @param event MouseEvent call.
     */
    private void unhighlight(MouseEvent event){
        filltRatingOut.play();
        stImageOut.play();
        stRatingOut.play();
        ftRatingOut.play();
    }

    /**
     * Set if the box should have an invalid out-of-bound colour.
     * @param isInvalid True if the box should have an invalid colour.
     */
    public void setInvalidColour(boolean isInvalid){
        if (isInvalid){
            Color c = OUT_OF_RANGE_COLOUR;
            boxColour = new Color(c.getRed(), c.getGreen(), c.getBlue(), OVERLAY_TRANSPARENCY);
        }
        else {
            boxColour = ratingColour;
        }
        filltRatingIn = new FillTransition(Duration.millis(100), rating, boxColour, boxColour.darker());
        filltRatingOut = new FillTransition(Duration.millis(100), rating, boxColour.darker(), boxColour);
        rating.setFill(boxColour);
    }

    /**
     * Calculate the colour that should be representing the rating of the listing.
     * @param rating The rating of the listing.
     * @return The colour of the box from the rating.
     */
    public static Color calculateRatingColour(double rating){
        if (rating == -1){
            Color c = UNKNOWN_REVIEW_COLOUR;
            return new Color(c.getRed(), c.getGreen(), c.getBlue(), OVERLAY_TRANSPARENCY);    // Colour if lower than low bound
        }
        else if (rating >= HIGH_REVIEW_BOUND){
            Color c = VERY_HIGH_REVIEW_COLOUR;
            return new Color(c.getRed(), c.getGreen(), c.getBlue(), OVERLAY_TRANSPARENCY);    // Colour if max rating
        }
        else if (rating < LOW_REVIEW_BOUND){
            Color c = VERY_LOW_REVIEW_COLOUR;
            return new Color(c.getRed(), c.getGreen(), c.getBlue(), OVERLAY_TRANSPARENCY);    // Colour if lower than low bound
        }

        double position = (rating - (double)LOW_REVIEW_BOUND) / ((double)HIGH_REVIEW_BOUND - (double)LOW_REVIEW_BOUND);

        Color upper, lower;

        if (position <= 0.5){
            position = position / 0.5;
            upper = MEDIUM_REVIEW_COLOUR;
            lower = LOW_REVIEW_COLOUR;
        }
        else {
            position = (position - 0.5) / 0.5;
            upper = HIGH_REVIEW_COLOUR;
            lower = MEDIUM_REVIEW_COLOUR;
        }

        double r = upper.getRed() - lower.getRed();
        double g = upper.getGreen() - lower.getGreen();
        double b = upper.getBlue() - lower.getBlue();

        r = (r * position) + lower.getRed();
        g = (g * position) + lower.getGreen();
        b = (b * position) + lower.getBlue();

        return new Color(r, g, b, OVERLAY_TRANSPARENCY);
    }
}
