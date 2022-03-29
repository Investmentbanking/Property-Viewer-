import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Abstract class for a pane of a shape that represents a borough. The size represents the amount of available listings and the colour represents the average price.
 *
 * @author Cosmo Colman (K21090628)
 * @version 29.03.2022
 */
public abstract class MenuShape extends StackPane {

    protected Borough borough;
    protected ArrayList<NewAirbnbListing> listings;

    protected Shape shape;
    protected Text text;
    protected Color colour;

    public static final Color LOW_COLOR = Color.rgb(46, 50, 177);
    public static final Color HIGH_COLOR = Color.rgb(242, 63, 63);
    public static final Color OUT_OF_RANGE_COLOUR = Color.GRAY;

    protected int fontSize;

    protected double shapeSizeScale = 1.1;
    protected double textSizeScale = 1.4;

    private FillTransition filltShapeIn, filltShapeOut;
    private ScaleTransition stShapeIn, stCircleOut, stTextIn, stTextOut;

    protected int inRangeListingCount;
    private static int minInRangeListings = Integer.MAX_VALUE;
    private static int maxInRangeListings = 0;

    /**
     * Constructor for a Menu-Shape which represents a specific borough and contains the listing of all properties in that borough. Clicking this object will open the pane specific to that borough.
     * @param borough The borough the object represents.
     * @param listings The listings of that borough.
     */
    public MenuShape(Borough borough, ArrayList<NewAirbnbListing> listings, Shape shape) {
        this.borough = borough;
        this.listings = listings;
        this.shape = shape;

        colour = OUT_OF_RANGE_COLOUR;   // Placeholder Colour

        this.shape.setOnMouseClicked(this::openInspectionWindow);
        this.shape.setFill(colour);

        text = new Text(this.borough.getName());
        text.setMouseTransparent(true);

        // Transitions and Aesthetics
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(200);
        dropShadow.setColor(Color.color(0,0,0, 0.5));
        this.shape.setEffect(dropShadow);

        filltShapeIn = new FillTransition(Duration.millis(100), this.shape, colour, colour.darker());
        filltShapeOut = new FillTransition(Duration.millis(100), this.shape, colour.darker(), colour);

        stShapeIn = new ScaleTransition(Duration.millis(100), this.shape);
        stShapeIn.setToX(shapeSizeScale);
        stShapeIn.setToY(shapeSizeScale);
        stCircleOut = new ScaleTransition(Duration.millis(100), this.shape);
        stCircleOut.setToX(1);
        stCircleOut.setToY(1);

        stTextIn = new ScaleTransition(Duration.millis(100), text);
        stTextIn.setToX(textSizeScale);
        stTextIn.setToY(textSizeScale);
        stTextOut = new ScaleTransition(Duration.millis(100), text);
        stTextOut.setToX(1);
        stTextOut.setToY(1);


        this.shape.setOnMouseEntered(this::mouseEnter);
        this.shape.setOnMouseExited(this::mouseLeave);
        getChildren().addAll(this.shape);

        text.setFill(Color.WHITE);

        getChildren().add(text);
        setPickOnBounds(false); // Lets you click through the StackPane to the components below.
    }

    /**
     * Get the number of listings within the price range.
     */
    private int inRangeListings(){
        ArrayList<NewAirbnbListing> newList = (ArrayList<NewAirbnbListing>) listings.clone();
        newList.removeIf(listing -> listing.getAvailability365() == 0);     // Remove 0 availability.
        newList.removeIf(listing -> (listing.getPrice() < RuntimeDetails.getMinimumPrice() || listing.getPrice() > RuntimeDetails.getMaximumPrice()));      // Remove out of range.
        return newList.size();
    }

    /**
     * Resets the min and max values to default so they can be reassigned.
     */
    public static void resetMinMax(){
        minInRangeListings = Integer.MAX_VALUE;
        maxInRangeListings = 0;
    }

    /**
     * Reloads the min and max values which determine the colour of the shape.
     */
    public void reloadMinMax(){
        inRangeListingCount = inRangeListings();
        if(inRangeListingCount != 0) {
            minInRangeListings = Math.min(minInRangeListings, inRangeListingCount);
            maxInRangeListings = Math.max(maxInRangeListings, inRangeListingCount);
        }
    }

    /**
     * Reloads the colour of the shape.
     */
    public void reloadColour(){
        if (inRangeListingCount == 0){
            colour = OUT_OF_RANGE_COLOUR;
        }
        else {
            colour = calculateColor();
        }
        shape.setFill(colour);
        filltShapeIn = new FillTransition(Duration.millis(100), this.shape, colour, colour.darker());
        filltShapeOut = new FillTransition(Duration.millis(100), this.shape, colour.darker(), colour);
    }

    /**
     * Set the multiplier for the shape's scale on mouse hover.
     * @param multiplier The multiplier for the shape's scale on mouse hover.
     */
    protected void setShapeScaleTransitionMultiplier(double multiplier){
        stShapeIn = new ScaleTransition(Duration.millis(100), this.shape);
        stShapeIn.setToX(multiplier);
        stShapeIn.setToY(multiplier);
    }

    /**
     * Set the multiplier for the text's scale on mouse hover.
     * @param multiplier The multiplier for the text's scale on mouse hover.
     */
    protected void setTextScaleTransitionMultiplier(double multiplier){
        stTextIn = new ScaleTransition(Duration.millis(100), text);
        stTextIn.setToX(multiplier);
        stTextIn.setToY(multiplier);
    }

    /**
     * Get the shape object of the instance.
     * @return The shape object of the instance.
     */
    abstract Shape getBoroughShape();

    /**
     * Set the position of both X and Y of the object.
     * @param x X position to be set.
     * @param y Y position to be set.
     */
    abstract void setXY(double x, double y);

    /**
     * Calculates what the colour of the circle should be based on the price.
     * @return the calculated colour of the circle.
     */
    private Color calculateColor(){
        double min = minInRangeListings;
        double max = maxInRangeListings;

        double calc = (inRangeListingCount - min) / (max - min);

        double r = HIGH_COLOR.getRed() - LOW_COLOR.getRed();
        double g = HIGH_COLOR.getGreen() - LOW_COLOR.getGreen();
        double b = HIGH_COLOR.getBlue() - LOW_COLOR.getBlue();

        r = (r * calc) + LOW_COLOR.getRed();
        g = (g * calc) + LOW_COLOR.getGreen();
        b = (b * calc) + LOW_COLOR.getBlue();

        return new Color(r, g, b, 1.0);
    }

    /**
     * Makes the cursor a hand and plays transitions on mouse enter.
     * @param event MouseEvent call.
     */
    private void mouseEnter(MouseEvent event){
        toFront();

        setCursor(Cursor.HAND);
        filltShapeIn.play();
        stShapeIn.play();
        stTextIn.play();
    }

    /**
     * Makes the cursor default and reverts transitions on mouse exit.
     * @param event MouseEvent call.
     */
    private void mouseLeave(MouseEvent event){
        setCursor(Cursor.DEFAULT);
        filltShapeOut.play();
        stCircleOut.play();
        stTextOut.play();
    }

    /**
     * Opens the inspection window specific to the borough and its listings.
     * @param event MouseEvent call.
     */
    private void openInspectionWindow(MouseEvent event){
        InspectMenuController.create(listings, borough.getName());
    }
}
