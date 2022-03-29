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
 * @version 29.03.2022
 */
public class CircleMap extends Pane {

    private final double GAP = 1.0; // Don't make below 1.0.
    private final int ANGLE_OFFSET = 0;
    private final int ANGLE_INCREMENT = 1;

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
        mapReload();

        menuCircles.sort(Comparator.comparing(MenuCircle::getColourValue)); // Sorts by circle colour
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
     * Reloads the colour values of the elements.
     */
    public void mapReload() {
        MenuShape.resetMinMax();
        for (MenuCircle circle : menuCircles) {
            circle.reloadMinMax();
        }
        for (MenuCircle circle : menuCircles) {
            circle.reloadColour();
        }
    }

    /**
     * Recalls the recursive algorithm for placing the circles on the pane.
     */
    public void repositionCircles() {
        for (MenuCircle circle : menuCircles) {
            circle.setVisible(false);
        }
        menuCircles.sort(Comparator.comparing(MenuCircle::getColourValue));
        Collections.reverse(menuCircles);

        arrangeCircles(menuCircles);
        fixAlignment(menuCircles);
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
     * @param circles the circles to be arranged.
     */
    private void arrangeCircles(ArrayList<MenuCircle> circles) {
        if (circles.size() > 2) {
            MenuCircle pivot = circles.get(0);
            pivot.setXY(500, 500);
            MenuCircle toPlace = circles.get(1);

            pivot.setVisible(true);

            assignCirclePositions(circles, pivot, toPlace, 0, null);
        } else {
            System.err.println("List of circles too small, please have a list with a size of at least 2.");
        }
    }

    private int angleDirection = 1; // For the assignCirclePositions() recursive algorithm to change direction of angle.

    /**
     * Recursive method to be called by arrangeCircles(). The algorithm will place a circle around
     * a pivot circle and when there's an available location to place the circle it will place it
     * and move on to another. if there are no free spots then another pivot is selected.
     * @param allCircles All the circles to arrange.
     * @param pivot The circle that will act as a pivot where circles are places around it.
     * @param toPlace The circle to place around the pivot circle.
     * @param startingAngle The starting angle to place the circle at.
     * @param prevPivot Only relevant to the recursive algorithm. Set this to null.
     */
    private void assignCirclePositions(ArrayList<MenuCircle> allCircles, MenuCircle pivot, MenuCircle toPlace, double startingAngle, MenuCircle prevPivot) {
        toPlace.setVisible(true);
        boolean collisionDetected = true;
        double angle = startingAngle;
        while ((angle < startingAngle + 360) && (angle > startingAngle - 360)) {

            // Set the circle form the pivot at the set angle.
            setPositionFrom(pivot, toPlace, angle);
            collisionDetected = isCollidingWith(toPlace, allCircles);

            // If no collision, successful placement of circle.
            if (!collisionDetected) {
                angleDirection *= -1;
                prevPivot = toPlace;

                // Check If the last placed circle is the last one.
                int currentToPlaceIndex = allCircles.indexOf(toPlace);
                if (currentToPlaceIndex == allCircles.size() - 1) {
                    // If last, all finished.
                    return;
                } else {
                    // If not last, recursion with next circle.
                    MenuCircle newToPlace = allCircles.get(currentToPlaceIndex + 1);
                    assignCirclePositions(allCircles, pivot, newToPlace, angle, prevPivot);     // Continues from same angle.
                    return;
                }
            } else {
                // If collision, increment angle and try again.
                angle += ANGLE_INCREMENT * angleDirection;
            }
        }
        // No free spaces around pivot.
        if (prevPivot == null) {
            System.err.println("No free space to space the circle anywhere");
        } else {
            assignCirclePositions(allCircles, prevPivot, toPlace, 0, null);
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
     * @param collideCheck Circle to check if intersecting.
     * @param collidingWith Circles to check if intersecting.
     * @return True if circle is intersecting another.
     * @author https://stackoverflow.com/a/15014709/11245518
     */
    private boolean isCollidingWith(MenuCircle collideCheck, ArrayList<MenuCircle> collidingWith) {
        for (MenuCircle circle : collidingWith) {
            if (circle != collideCheck && circle.isVisible()) {
                Shape intersect = Shape.intersect(collideCheck.getBoroughShape(), circle.getBoroughShape());

                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return true;
                }
            }
        }
        return false;
    }
}
