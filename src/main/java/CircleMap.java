import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The pane which creates and holds the circles which represent a Borough, and launch the window to inspect the listings of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 26.03.2022
 */
public class CircleMap extends Pane {

    private final double GAP = 1.0; // Don't make below 1.0.
    private final int ANGLE_OFFSET = 0;

    private final ArrayList<MenuCircle> menuCircles;

    /**
     * Constructor for the map that contains circles for each Borough.
     */
    public CircleMap() {
        menuCircles = new ArrayList<>();
        for (Borough borough : BoroughMap.getBoroughs()) {
            MenuCircle newCircle = new MenuCircle(borough, BoroughMap.getBoroughListings().get(borough));
            menuCircles.add(newCircle);
        }
        menuCircles.sort(Comparator.comparing(MenuCircle::getSizeValue)); // Sorts by circle size
        Collections.reverse(menuCircles);
        getChildren().addAll(menuCircles);

        layout();   // Required to get correct bounds.
        arrangeCircles(menuCircles);
        fixAlignment(menuCircles);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setPrefWidth(Region.USE_COMPUTED_SIZE);
        setMinHeight(Region.USE_PREF_SIZE);
        setMinWidth(Region.USE_PREF_SIZE);
        setMaxHeight(Region.USE_PREF_SIZE);
        setMaxWidth(Region.USE_PREF_SIZE);
    }

    /**
     * Removes empty space on the top and left of the pane.
     * @param circles All the circles on the menu.
     */
    private void fixAlignment(ArrayList<MenuCircle> circles){
        double smallestX = Double.MAX_VALUE;
        double smallestY = Double.MAX_VALUE;

        for (MenuCircle circle : circles){
            smallestX = Math.min(circle.getLayoutX() , smallestX);
            smallestY = Math.min(circle.getLayoutY() , smallestY);
        }

        for (MenuCircle circle : circles){
            circle.setLayoutX(circle.getLayoutX() - smallestX);
            circle.setLayoutY(circle.getLayoutY() - smallestY);
        }
    }

    /**
     * Arranges the circles in a collective formation.
     * @param circles All the circles to be arranged.
     */
    private void arrangeCircles(ArrayList<MenuCircle> circles) {
        MenuCircle pivotCircle = circles.get(0);
        MenuCircle prev;

        pivotCircle.setXY(500,500);

        int increment = 1;
        for (MenuCircle next : circles) {
            prev = next;
            if (circles.indexOf(next) != 0) {

                boolean hasCollision = true;
                double angle = 0;
                while (hasCollision){
                    if (angle >= 360 || angle <= -360) {
                        angle = 0;
                        increment = -increment; // Flips rotation
                        int newPivot = circles.indexOf(prev) - 1;
                        prev = circles.get(newPivot);
                        pivotCircle = circles.get(newPivot);
                    }

                    setPositionFrom(pivotCircle, next, angle);

                    hasCollision = isColliding(next);
                    if (hasCollision) {
                        angle += increment;
                    }
                }
            }
        }
    }

    /**
     * Sets a circles from another circle ar a specific angle.
     * @param circle1 Circle to act as the pivot.
     * @param circle2 Circle you want to set from the first.
     * @param angle The angle at which you want the circle to be placed from.
     */
    private void setPositionFrom(MenuCircle circle1, MenuCircle circle2, double angle){
        double rad = Math.toRadians(angle - ANGLE_OFFSET);
        double distance = circle1.getRadius() + circle2.getRadius() + GAP;

        double centreX1 = circle1.getLayoutCentreX();
        double centreY1 = circle1.getLayoutCentreY();

        double centreX2 = centreX1 - (distance * Math.sin(rad));
        double centreY2 = centreY1 - (distance * Math.cos(rad));

        circle2.setPositionFromCentre((int)centreX2, (int)centreY2);
    }

    /**
     * Checks if a circle is intersecting any other circle.
     * @param circle Circle to check if intersecting.
     * @return True if circle is intersecting another.
     * @author https://stackoverflow.com/a/15014709/11245518
     */
    private boolean isColliding(MenuCircle circle) {
        boolean collisionDetected = false;
        for (MenuCircle static_circle : menuCircles) {
            if (static_circle != circle) {
                Shape intersect = Shape.intersect(circle.getBoroughShape(), static_circle.getBoroughShape());

                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            }
        }
        return collisionDetected;
    }
}
