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
public class MapPane extends Pane {

    private final double GAP = 1.0;
    private final int ANGLE_OFFSET = 0;

    private ArrayList<MenuCircle> menuCircles;

    private ArrayList<Borough> boroughs;
    private HashMap<Borough, ArrayList<NewAirbnbListing>> boroughListings;

    /**
     * Constructor for the map that contains circles for each Borough.
     * @param listings The listing for all Boroughs.
     */
    public MapPane(ArrayList<NewAirbnbListing> listings) {
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

        MenuCircle.setAllCircles(menuCircles);

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

        pivotCircle.setPosition(500,500);

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
                Shape intersect = Shape.intersect(circle.getCircle(), static_circle.getCircle());

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

    /**
     * Only for Testing purposes. If the dataset is unavailable, these values will determine a pattern for the map.
     * @param boroughs Borough list to add to.
     */
    private void addBoroughs(ArrayList<Borough> boroughs) {
        boroughs.add(new Borough("Enfield", 57, 301, 258, 66777));
        boroughs.add(new Borough("Westminster", 147, 5361, 4397, 813720));
        boroughs.add(new Borough("Hillingdon", 58, 277, 250, 55740));
        boroughs.add(new Borough("Havering", 61, 99, 91, 23219));
        boroughs.add(new Borough("Wandsworth", 97, 2748, 2077, 413623));
        boroughs.add(new Borough("Lewisham", 63, 1502, 1159, 253472));
        boroughs.add(new Borough("Tower Hamlets", 78, 5613, 4272, 851791));
        boroughs.add(new Borough("Hounslow", 93, 647, 528, 115199));
        boroughs.add(new Borough("Redbridge", 59, 383, 339, 87985));
        boroughs.add(new Borough("Southwark", 86, 3359, 2561, 460096));
        boroughs.add(new Borough("Camden", 118, 3761, 2927, 551240));
        boroughs.add(new Borough("Bromley", 57, 391, 339, 80109));
        boroughs.add(new Borough("Lambeth", 84, 3276, 2492, 480479));
        boroughs.add(new Borough("Kensington and Chelsea", 156, 3476, 2801, 531683));
        boroughs.add(new Borough("Islington", 95, 3583, 2559, 448128));
        boroughs.add(new Borough("Barnet", 71, 1012, 838, 199716));
        boroughs.add(new Borough("Richmond upon Thames", 122, 872, 684, 154401));
        boroughs.add(new Borough("Kingston upon Thames", 75, 316, 261, 60952));
        boroughs.add(new Borough("Harrow", 58, 266, 253, 63963));
        boroughs.add(new Borough("Sutton", 53, 147, 129, 31145));
        boroughs.add(new Borough("Haringey", 69, 1493, 1101, 228187));
        boroughs.add(new Borough("Brent", 80, 1609, 1319, 287962));
        boroughs.add(new Borough("Bexley", 49, 115, 104, 26059));
        boroughs.add(new Borough("Hackney", 83, 4688, 3221, 613468));
        boroughs.add(new Borough("Greenwich", 74, 917, 766, 175916));
        boroughs.add(new Borough("Hammersmith and Fulham", 105, 2806, 2202, 434952));
        boroughs.add(new Borough("Waltham Forest", 60, 911, 755, 178678));
        boroughs.add(new Borough("Merton", 94, 820, 645, 142139));
        boroughs.add(new Borough("Croydon", 53, 553, 500, 117044));
        boroughs.add(new Borough("Newham", 70, 1151, 923, 199022));
        boroughs.add(new Borough("Ealing", 82, 1003, 804, 174079));
        boroughs.add(new Borough("City of London", 149, 306, 264, 47240));
        boroughs.add(new Borough("Barking and Dagenham", 48, 142, 122, 32743));
    }
}
