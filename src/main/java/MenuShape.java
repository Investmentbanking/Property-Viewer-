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
 * @version 23.03.2022
 */
public abstract class MenuShape extends StackPane {

    protected Borough borough;
    protected ArrayList<NewAirbnbListing> listings;

    protected Shape shape;
    protected Text text;
    protected Color colour;

    protected static final Color CHEAP_COLOR = Color.rgb(46, 50, 177);
    public static final Color EXPENSIVE_COLOR = Color.rgb(242, 63, 63);

    protected int fontSize;

    protected static double shapeSizeScale = 1.1;
    protected static double textSizeScale = 1.4;

    private FillTransition filltShapeIn, filltShapeOut;
    private ScaleTransition stShapeIn, stCircleOut, stTextIn, stTextOut;

    /**
     * Constructor for a Menu-Shape which represents a specific borough and contains the listing of all properties in that borough. Clicking this object will open the pane specific to that borough.
     * @param borough The borough the object represents.
     * @param listings The listings of that borough.
     */
    public MenuShape(Borough borough, ArrayList<NewAirbnbListing> listings, Shape shape) {
        this.borough = borough;
        this.listings = listings;
        this.shape = shape;

        colour = calculateColor();

        this.shape.setOnMouseClicked(this::openInspectionWindow);
        this.shape.setFill(colour);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(200);
        dropShadow.setColor(Color.color(0,0,0, 0.5));
        this.shape.setEffect(dropShadow);

        text = new Text(this.borough.getName());
        text.setMouseTransparent(true);

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
     * Get the shape object of the instance.
     * @return The shape object of the instance.
     */
    abstract Shape getBoroughShape();

    /**
     * Calculates what the colour of the circle should be based on the price.
     * @return the calculated colour of the circle.
     */
    private Color calculateColor(){
        double min = Borough.avgPriceStorage.min();
        double max = Borough.avgPriceStorage.max();

        double calc = (borough.getAvgPrice() - min) / (max - min);

        double r = EXPENSIVE_COLOR.getRed() - CHEAP_COLOR.getRed();
        double g = EXPENSIVE_COLOR.getGreen() - CHEAP_COLOR.getGreen();
        double b = EXPENSIVE_COLOR.getBlue() - CHEAP_COLOR.getBlue();

        r = (r * calc) + CHEAP_COLOR.getRed();
        g = (g * calc) + CHEAP_COLOR.getGreen();
        b = (b * calc) + CHEAP_COLOR.getBlue();

        return new Color(r, g, b, 1.0);
    }

    /**
     * Set the position of both X and Y of the object.
     * @param x X position to be set.
     * @param y Y position to be set.
     */
    abstract void setXY(double x, double y);

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
