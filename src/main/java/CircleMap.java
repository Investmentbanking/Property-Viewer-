import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.*;

/**
 * The pane which creates and holds the circles which represent a Borough, and launch the window to inspect the listings of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class CircleMap extends Pane {

    private final double GAP = 1.0;
    private final int ANGLE_OFFSET = 0;

    private ArrayList<MenuCircle> menuCircles;

    private ArrayList<Borough> boroughs;
    private HashMap<Borough, ArrayList<NewAirbnbListing>> boroughListings;

    /**
     * Constructor for the map that contains circles for each Borough.
     * @param listings The listing for all Boroughs.
     */
    public CircleMap(ArrayList<NewAirbnbListing> listings) {
        boroughs = initialiseBoroughs(listings);
        boroughListings = initialiseBoroughArrayList(listings, boroughs);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        menuCircles = new ArrayList<>();
        for (Borough borough : boroughs) {
            MenuCircle newCircle = new MenuCircle(borough, boroughListings.get(borough));
            menuCircles.add(newCircle);
        }
        menuCircles.sort(Comparator.comparing(MenuCircle::getSizeValue)); // Sorts by circle size
        Collections.reverse(menuCircles);

        getChildren().addAll(menuCircles);

        layout();   // Required to get correct bounds.
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
     * @param circles All the circles to be arranged.
     */
    private void arrangeCircles(ArrayList<MenuCircle> circles) {
        MenuCircle pivotCircle = circles.get(0);
        MenuCircle prev;

        pivotCircle.setXY(500,500);

        for (MenuCircle next : circles) {
            prev = next;
            if (circles.indexOf(next) != 0) {

                boolean hasCollision = true;
                double angle = 0;
                while (hasCollision){
                    if (angle >= 360) {
                        angle = 0;
                        int newPivot = circles.indexOf(prev) - 1;
                        prev = circles.get(newPivot);
                        pivotCircle = circles.get(newPivot);
                    }

                    setPositionFrom(pivotCircle, next, angle);

                    hasCollision = isColliding(next);
                    if (hasCollision) {
                        angle += 1;
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

    /**
     * Initialise the Boroughs from all listings. The values in the borough objects are calculated from the properties of the listing.
     * @param allListings The list of all properties.
     * @return The list of all the created Borough objects.
     */
    private ArrayList<Borough> initialiseBoroughs(ArrayList<NewAirbnbListing> allListings){
        boroughListings = new HashMap<>();

        HashMap<String, ArrayList<Double>> boroughStats = new HashMap<>();

        for (NewAirbnbListing listing : allListings){
            String boroughName = listing.getNeighbourhoodCleansed();

            if (!boroughStats.containsKey(boroughName)) {
                ArrayList<Double> stats = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));  // Average Price, Total Price, Total Count, Total Available (excluding 0 356), Total Available * days available
                boroughStats.put(boroughName, stats);
            }

            ArrayList<Double> stats = boroughStats.get(boroughName);
            stats.set(1, stats.get(1) + listing.getPrice());                // Total Price
            stats.set(2, stats.get(2) + 1);                                 // Total Count
            if(listing.getAvailability365() != 0){
                stats.set(3, stats.get(3) + 1);                             // Total Available (excluding 0 356)
            }
            stats.set(4, stats.get(4) + listing.getAvailability365());      // Total Available * days available
        }

        for (String name : boroughStats.keySet()){
            ArrayList<Double> stats = boroughStats.get(name);
            stats.set(0, (stats.get(1)/stats.get(2)));
        }

        ArrayList<Borough> boroughs = new ArrayList<>();

        for (String name : boroughStats.keySet()){
            ArrayList<Double> stats = boroughStats.get(name);
            int avgPrice = (int)Math.ceil(stats.get(0));
            int count = (int)Math.round(stats.get(2));
            int available = (int)Math.round(stats.get(3));
            int availableDays = (int)Math.round(stats.get(4));

            boroughs.add(new Borough(name, avgPrice, count, available, availableDays));
        }

        return  boroughs;
    }

    /**
     * Initialised which listings belong to what Borough.
     * @param allListings The list of all properties.
     * @param allBoroughs The list of all Boroughs.
     * @return The HashMap which connects the list of properties in a borough to the Borough.
     */
    private HashMap<Borough, ArrayList<NewAirbnbListing>> initialiseBoroughArrayList(ArrayList<NewAirbnbListing> allListings, ArrayList<Borough> allBoroughs) {
        HashMap<Borough, ArrayList<NewAirbnbListing>> boroughAirbnbs = new HashMap<>();
        HashMap<String,Borough> boroughLookUp = new HashMap<>();

        for (Borough borough : allBoroughs){
            boroughAirbnbs.put(borough, new ArrayList<>());
            boroughLookUp.put(borough.getName(), borough);
        }

        for (NewAirbnbListing listing : allListings){
            boroughAirbnbs.get(boroughLookUp.get(listing.getNeighbourhoodCleansed())).add(listing);
        }

        return boroughAirbnbs;
    }
}
