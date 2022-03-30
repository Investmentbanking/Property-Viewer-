import com.google.common.primitives.Doubles;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * The pane which creates and holds the polygons which represent a Borough, and launch the window to inspect the listings of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 29.03.2022
 */
public class GeoMap extends Pane {

    private final Polygon THAMES = new Polygon(160.0, 381.0, 182.0, 380.0, 205.0, 398.0, 209.0, 351.0, 202.0, 318.0, 199.0, 302.0, 228.0, 283.0, 252.0, 291.0, 264.0, 274.0, 280.0, 280.0, 283.0, 296.0, 303.0, 302.0, 311.0, 288.0, 333.0, 282.0, 345.0, 279.0, 360.0, 250.0, 392.0, 248.0, 423.0, 249.0, 434.0, 273.0, 444.0, 272.0, 442.0, 255.0, 458.0, 250.0, 474.0, 258.0, 503.0, 260.0, 521.0, 242.0, 550.0, 238.0, 571.0, 245.0, 590.0, 266.0, 619.0, 265.0, 615.0, 276.0, 586.0, 276.0, 565.0, 254.0, 549.0, 245.0, 526.0, 250.0, 506.0, 271.0, 471.0, 268.0, 456.0, 260.0, 452.0, 263.0, 454.0, 280.0, 427.0, 283.0, 422.0, 271.0, 417.0, 259.0, 366.0, 259.0, 351.0, 288.0, 319.0, 296.0, 308.0, 314.0, 277.0, 306.0, 272.0, 287.0, 265.0, 285.0, 255.0, 301.0, 229.0, 293.0, 208.0, 305.0, 219.0, 352.0, 213.0, 409.0, 208.0, 412.0, 180.0, 389.0, 163.0, 388.0);

    private final ArrayList<MenuPolygon> menuPolygons;

    /**
     * Constructor for the map that contains polygons for each Borough.
     */
    public GeoMap() {
        menuPolygons = new ArrayList<>();
        for (Borough borough : BoroughMap.getBoroughs()) {
            MenuPolygon newPolygon = new MenuPolygon(borough, BoroughMap.getBoroughListings().get(borough));
            menuPolygons.add(newPolygon);
        }
        getChildren().addAll(menuPolygons);

        THAMES.setFill(Color.AQUA.brighter());
        getChildren().add(THAMES);

        layout();   // Required to get correct bounds.
        arrangePolygons(menuPolygons);
        fixAlignment(menuPolygons);

        mapReload();

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
    public void mapReload(){
        MenuShape.resetMinMax();
        for (MenuPolygon polygon : menuPolygons){
            polygon.reloadMinMax();
        }
        for (MenuPolygon polygon : menuPolygons){
            polygon.reloadColour();
        }
    }

    /**
     * Removes empty space on the top and left of the pane.
     *
     * @param polygons All the polygon on the menu.
     */
    private void fixAlignment(ArrayList<MenuPolygon> polygons){
        double smallestX = Double.MAX_VALUE;
        double smallestY = Double.MAX_VALUE;

        for (MenuPolygon polygon : polygons){
            smallestX = Math.min(polygon.getLayoutX() , smallestX);
            smallestY = Math.min(polygon.getLayoutY() , smallestY);
        }

        for (MenuPolygon polygon : polygons){
            polygon.setLayoutX(polygon.getLayoutX() - smallestX);
            polygon.setLayoutY(polygon.getLayoutY() - smallestY);
        }

        THAMES.setLayoutX(THAMES.getLayoutX() - smallestX);
        THAMES.setLayoutY(THAMES.getLayoutY() - smallestY);
    }

    /**
     * Arranges the polygons in the formation of London.
     *
     * @param polygons All the polygons to be arranged
     */
    private void arrangePolygons(ArrayList<MenuPolygon> polygons) {
        for (MenuPolygon menuPolygon: polygons){
            double[] points = Doubles.toArray(menuPolygon.getBoroughShape().getPoints());

            Polygon tempPolygon = new Polygon(points);
            getChildren().add(tempPolygon);
            menuPolygon.setXY(tempPolygon.getLayoutBounds().getMinX(), tempPolygon.getLayoutBounds().getMinY());
            getChildren().remove(tempPolygon);
        }
    }
}
