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

/**
 * A pane class which is a bod representing a specific listing. The box will display the image of the
 * property when called, and the colour represents the rating value.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class ListingBox extends StackPane {

    private NewAirbnbListing listing;
    private Rectangle clip;
    private Rectangle rating;
    private ImageView imageView;

    private Image image;

    private int size = 30;

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

    //Text text;

    public ListingBox(NewAirbnbListing listing) {
        this.listing = listing;

        image = null;


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
        rating = new Rectangle(size, size, boxColour);
        rating.widthProperty().bind(widthProperty());
        rating.heightProperty().bind(heightProperty());
        rating.setArcWidth(40);
        rating.setArcHeight(40);

        // Bottom right icon colour
//        rating = new Rectangle(size, size, boxColour);
//        setAlignment(rating, Pos.BOTTOM_RIGHT);
//        rating.setArcWidth(40);
//        rating.setArcHeight(40);




//        text = new Text("lol");
//        Font newFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15);
//        text.setFont(newFont);

        maxHeightProperty().bind(minWidthProperty());
        maxWidthProperty().bind(minWidthProperty());
        minHeightProperty().bind(minWidthProperty());

        getChildren().addAll(imageView, rating);//, square);//, text);



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

        setOnMouseEntered(this::highlight);
        setOnMouseExited(this::unhighlight);


        //showImage();

        //setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 255, 0.1), CornerRadii.EMPTY, Insets.EMPTY)));
        //setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }


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

    private void highlight(MouseEvent event){
        filltRatingIn.play();
        stImageIn.play();
        stRatingIn.play();
        ftRatingIn.play();
    }

    private void unhighlight(MouseEvent event){
        filltRatingOut.play();
        stImageOut.play();
        stRatingOut.play();
        ftRatingOut.play();
    }

    public NewAirbnbListing getListing() {
        return listing;
    }

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


    public void showImage(){
        if (image == null) {
            image = new Image(listing.getPictureURL().toString(), true);
            imageView.setImage(image);
        }
    }

    public void cancelLoad(){
        image.cancel();
    }

}
