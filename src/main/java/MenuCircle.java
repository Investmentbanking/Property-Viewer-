import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

/**
 * A pane of a circle that represents a borough. The size represents the amount of available listings and the colour represents the average price.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class MenuCircle extends MenuShape {

    private final double circleSize;

    private static final int MIN_CIRCLE_SIZE = 28;
    private static final int MAX_CIRCLE_SIZE = 120;

    private static final int MAX_FONT_SIZE = 35;
    private static final int MIN_FONT_SIZE = 10;

    /**
     * Constructor for a Menu-Circle which represents a specific borough and contains the listing of all properties in that borough. Clicking this object will open the pane specific to that borough.
     * @param borough The borough the object represents.
     * @param listings The listings of that borough.
     */
    public MenuCircle(Borough borough, ArrayList<NewAirbnbListing> listings) {
        super(borough, listings, new Circle());

        circleSize = calculateSize();
        getBoroughShape().setRadius(circleSize);

        int tempFontSize = MAX_FONT_SIZE - MIN_FONT_SIZE;
        fontSize = (int)(((circleSize/ MAX_CIRCLE_SIZE) * tempFontSize) + MIN_FONT_SIZE);

        do {
            Font newFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, fontSize--);
            text.setFont(newFont);
        }while (text.getLayoutBounds().getWidth() > circleSize*2);

        setShapeScaleTransitionMultiplier(1.1);

        double zoomedFontSize = Math.max(12, (double)fontSize * 1.4);        // If normal font size is bigger, choose that * 1.4
        setTextScaleTransitionMultiplier(zoomedFontSize/(double)fontSize);
    }

    /**
     * Get the circle object of the object.
     * @return The circle object of the object.
     */
    public Circle getBoroughShape() {
        return (Circle)shape;
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
        calc *= MAX_CIRCLE_SIZE - MIN_CIRCLE_SIZE;
        calc += MIN_CIRCLE_SIZE;

        return (int)calc;
    }

    /**
     * Get the radius of the circle.
     * @return the radius of the circle.
     */
    public double getRadius(){
        return ((Circle)shape).getRadius();
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
    public void setXY(double x, double y){
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
