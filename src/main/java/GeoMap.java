import com.google.common.primitives.Doubles;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The pane which creates and holds the polygons which represent a Borough, and launch the window to inspect the listings of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 24.03.2022
 */
public class GeoMap extends Pane {

    private ArrayList<MenuPolygon> menuPolygons;

    private ArrayList<Borough> boroughs;
    private HashMap<Borough, ArrayList<NewAirbnbListing>> boroughListings;

    /**
     * Constructor for the map that contains polygons for each Borough.
     * @param listings The listing for all Boroughs.
     */
    public GeoMap(ArrayList<NewAirbnbListing> listings) {
        boroughs = initialiseBoroughs(listings);
        boroughListings = initialiseBoroughArrayList(listings, boroughs);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        Polygon THAMES = new Polygon(160.0, 381.0, 182.0, 380.0, 205.0, 398.0, 209.0, 351.0, 202.0, 318.0, 199.0, 302.0, 228.0, 283.0, 252.0, 291.0, 264.0, 274.0, 280.0, 280.0, 283.0, 296.0, 303.0, 302.0, 311.0, 288.0, 333.0, 282.0, 345.0, 279.0, 360.0, 250.0, 392.0, 248.0, 423.0, 249.0, 434.0, 273.0, 444.0, 272.0, 442.0, 255.0, 458.0, 250.0, 474.0, 258.0, 503.0, 260.0, 521.0, 242.0, 550.0, 238.0, 571.0, 245.0, 590.0, 266.0, 619.0, 265.0, 615.0, 276.0, 586.0, 276.0, 565.0, 254.0, 549.0, 245.0, 526.0, 250.0, 506.0, 271.0, 471.0, 268.0, 456.0, 260.0, 452.0, 263.0, 454.0, 280.0, 427.0, 283.0, 422.0, 271.0, 417.0, 259.0, 366.0, 259.0, 351.0, 288.0, 319.0, 296.0, 308.0, 314.0, 277.0, 306.0, 272.0, 287.0, 265.0, 285.0, 255.0, 301.0, 229.0, 293.0, 208.0, 305.0, 219.0, 352.0, 213.0, 409.0, 208.0, 412.0, 180.0, 389.0, 163.0, 388.0);
        THAMES.setFill(Color.AQUA.brighter());
        getChildren().add(THAMES);

        menuPolygons = new ArrayList<>();
        for (Borough borough : boroughs) {
            MenuPolygon newPolygon = new MenuPolygon(borough, boroughListings.get(borough));
            menuPolygons.add(newPolygon);
        }

        getChildren().addAll(menuPolygons);

        layout();   // Required to get correct bounds.
        arrangePolygons(menuPolygons);
    }

    /**
     * Arranges the polygons in the formation of London.
     * @param polygons All the polygons to be arranged
     */
    private void arrangePolygons(ArrayList<MenuPolygon> polygons) {
        for (MenuPolygon menuPolygon: polygons){
            double[] points = Doubles.toArray(menuPolygon.getPolygon().getPoints());

            Polygon tempPolygon = new Polygon(points);
            getChildren().add(tempPolygon);
            menuPolygon.setXY(tempPolygon.getLayoutBounds().getMinX(), tempPolygon.getLayoutBounds().getMinY());
            getChildren().remove(tempPolygon);
        }
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
