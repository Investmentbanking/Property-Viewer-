import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

/**
 * A pane of a circle that represents a borough. The size represents the amount of available listings and the colour represents the average price.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class MenuCircle extends StackPane {

    private Borough borough;
    private ArrayList<NewAirbnbListing> listings;

    private Circle circle;
    private Text text;

    private double circleSize;
    private Color circleColor;

    public static final Color CHEAP_COLOR = Color.rgb(46, 50, 177);
    public static final Color EXPENSIVE_COLOR = Color.rgb(242, 63, 63);

    private static final int MIN_SIZE = 35;
    private static final int MAX_SIZE = 150;

    private static final int MAX_FONT_SIZE = 35;
    private static final int MIN_FONT_SIZE = 10;

    private int fontSize;

    private FillTransition filltCircleIn, filltCircleOut;
    private ScaleTransition stCircleIn, stCircleOut, stTextIn, stTextOut;

    /**
     * Constructor for a Menu-Circle which represents a specific borough and contains the listing of all properties in that borough. Clicking this object will open the pane specific to that borough.
     * @param borough The borough the object represents.
     * @param listings The listings of that borough.
     */
    public MenuCircle(Borough borough, ArrayList<NewAirbnbListing> listings) {
        this.borough = borough;
        this.listings = listings;

        circleSize = calculateSize();
        circleColor = calculateColor();

        int tempFontSize = MAX_FONT_SIZE - MIN_FONT_SIZE;
        fontSize = (int)(((circleSize/MAX_SIZE) * tempFontSize) + MIN_FONT_SIZE);

        circle = new Circle(circleSize, circleColor);
        circle.setOnMouseClicked(this::openInspectionWindow);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(200);
        dropShadow.setColor(Color.color(0,0,0, 0.5));
        circle.setEffect(dropShadow);

        text = new Text(borough.getName());
        text.setMouseTransparent(true);

        filltCircleIn = new FillTransition(Duration.millis(100), circle, circleColor, circleColor.darker());
        filltCircleOut = new FillTransition(Duration.millis(100), circle, circleColor.darker(), circleColor);

        final double CIRCLE_SIZE_SCALE = 1.1;
        stCircleIn = new ScaleTransition(Duration.millis(100), circle);
        stCircleIn.setToX(CIRCLE_SIZE_SCALE);
        stCircleIn.setToY(CIRCLE_SIZE_SCALE);
        stCircleOut = new ScaleTransition(Duration.millis(100), circle);
        stCircleOut.setToX(1);
        stCircleOut.setToY(1);

        final double TEXT_SIZE_SCALE = 1.4;
        stTextIn = new ScaleTransition(Duration.millis(100), text);
        stTextIn.setToX(TEXT_SIZE_SCALE);
        stTextIn.setToY(TEXT_SIZE_SCALE);
        stTextOut = new ScaleTransition(Duration.millis(100), text);
        stTextOut.setToX(1);
        stTextOut.setToY(1);

        circle.setOnMouseEntered(this::mouseEnter);
        circle.setOnMouseExited(this::mouseLeave);
        getChildren().addAll(circle);

        do {
            Font newFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, fontSize--);
            text.setFont(newFont);
        }while (text.getLayoutBounds().getWidth() > circleSize*2);

        text.setFill(Color.WHITE);

        getChildren().add(text);
        setPickOnBounds(false); // Lets you click through the StackPane to the components below.
    }

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
     * Calculates what the size of the circle should be based on how many available listings there are.
     * @return the calculated size of the circle.
     */
    private int calculateSize(){
        double min = Borough.availableStorage.min();
        double max = Borough.availableStorage.max();

        double calc;
        calc = (borough.getAvailable() - min) / (max - min);
        calc *= MAX_SIZE - MIN_SIZE;
        calc += MIN_SIZE;

        return (int)calc;
    }

    /**
     * Makes the cursor a hand and plays transitions on mouse enter.
     * @param event MouseEvent call.
     */
    private void mouseEnter(MouseEvent event){
        toFront();

        setCursor(Cursor.HAND);
        filltCircleIn.play();
        stCircleIn.play();
        stTextIn.play();
    }

    /**
     * Makes the cursor default and reverts transitions on mouse exit.
     * @param event MouseEvent call.
     */
    private void mouseLeave(MouseEvent event){
        setCursor(Cursor.DEFAULT);
        filltCircleOut.play();
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

    /**
     * Get the circle object of the object.
     * @return The circle object of the object.
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Get the radius of the circle.
     * @return the radius of the circle.
     */
    public double getRadius(){
        return circle.getRadius();
    }

    /**
     * Get the value that represents the size of the circle. (The average price in the borough)
     * @return the value that represents the size of the circle. (The average price in the borough)
     */
    public int getSizeValue(){
        return borough.getAvgPrice();
    }

    /**
     * Set the position of both X and Y of the object.
     * @param x X position to be set.
     * @param y Y position to be set.
     */
    public void setPosition(int x, int y){
        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * Set the position of both X and Y of the object from the centre of the object.
     * @param x X centre position to be set.
     * @param y Y centre position to be set.
     */
    public void setPositionFromCentre(int x, int y){
        setLayoutX(x - getRadius());
        setLayoutY(y - getRadius());
    }

    /**
     * Get the centre X of the object.
     * @return the centre X of the object.
     */
    public double getLayoutCentreX(){
        return getLayoutX() + getRadius();
    }

    /**
     * Get the centre Y of the object.
     * @return the centre Y of the object.
     */
    public double getLayoutCentreY(){
        return getLayoutY() + getRadius();
    }
}
