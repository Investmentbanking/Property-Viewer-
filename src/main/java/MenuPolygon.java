import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A pane of a polygon that represents a London borough. The shape is a representation of reality.
 *
 * @author Cosmo Colman (K21090628)
 * @version 26.03.2022
 */
public class MenuPolygon extends MenuShape {

    private final static HashMap<String, double[]> POINTS_LOOKUP;
    static {
        POINTS_LOOKUP = new HashMap<>();
        POINTS_LOOKUP.put("Hillingdon", new double[]{101.0, 322.0, 66.0, 309.0, 84.0, 192.0, 74.0, 166.0, 74.0, 112.0, 139.0, 128.0, 157.0, 196.0, 127.0, 213.0, 149.0, 239.0, 136.0, 270.0});
        POINTS_LOOKUP.put("Harrow", new double[]{139.0, 128.0, 215.0, 95.0, 244.0, 140.0, 202.0, 164.0, 193.0, 194.0, 157.0, 196.0});
        POINTS_LOOKUP.put("Ealing",  new double[]{136.0, 270.0, 206.0, 269.0, 253.0, 264.0, 259.0, 249.0, 254.0, 225.0, 229.0, 230.0, 193.0, 194.0, 157.0, 196.0, 127.0, 213.0, 149.0, 239.0});
        POINTS_LOOKUP.put("Barnet", new double[]{215.0, 95.0, 308.0, 56.0, 341.0, 97.0, 338.0, 127.0, 328.0, 172.0, 286.0, 199.0, 271.0, 179.0, 254.0, 174.0, 256.0, 158.0, 244.0, 140.0});
        POINTS_LOOKUP.put("Brent", new double[]{229.0, 230.0, 254.0, 225.0, 277.0, 226.0, 286.0, 226.0, 300.0, 221.0, 286.0, 199.0, 271.0, 179.0, 254.0, 174.0, 256.0, 158.0, 244.0, 140.0, 202.0, 164.0, 193.0, 194.0});
        POINTS_LOOKUP.put("Haringey", new double[]{328.0, 172.0, 370.0, 175.0, 397.0, 170.0, 411.0, 130.0, 338.0, 127.0});
        POINTS_LOOKUP.put("Enfield",  new double[]{411.0, 130.0, 437.0, 72.0, 436.0, 36.0, 357.0, 26.0, 322.0, 32.0, 308.0, 56.0, 341.0, 97.0, 338.0, 127.0});
        POINTS_LOOKUP.put("Waltham Forest", new double[]{397.0, 170.0, 411.0, 130.0, 437.0, 72.0, 459.0, 87.0, 464.0, 103.0, 461.0, 192.0, 436.0, 205.0});
        POINTS_LOOKUP.put("Redbridge", new double[]{461.0, 192.0, 464.0, 103.0, 508.0, 129.0, 560.0, 109.0, 559.0, 141.0, 533.0, 189.0, 503.0, 205.0, 488.0, 185.0});
        POINTS_LOOKUP.put("Havering", new double[]{571.0, 245.0, 590.0, 266.0, 619.0, 265.0, 637.0, 217.0, 698.0, 206.0, 678.0, 168.0, 665.0, 170.0, 623.0, 101.0, 560.0, 109.0, 559.0, 141.0, 564.0, 176.0, 591.0, 185.0});
        POINTS_LOOKUP.put("Barking and Dagenham", new double[]{571.0, 245.0, 550.0, 238.0, 521.0, 242.0, 503.0, 205.0, 533.0, 189.0, 559.0, 141.0, 564.0, 176.0, 591.0, 185.0});
        POINTS_LOOKUP.put("Newham", new double[]{521.0, 242.0, 503.0, 260.0, 474.0, 258.0, 458.0, 250.0, 442.0, 235.0, 436.0, 205.0, 461.0, 192.0, 488.0, 185.0, 503.0, 205.0});
        POINTS_LOOKUP.put("Bexley", new double[]{615.0, 276.0, 602.0, 318.0, 559.0, 369.0, 511.0, 348.0, 523.0, 293.0, 550.0, 291.0, 549.0, 245.0, 565.0, 254.0, 586.0, 276.0});
        POINTS_LOOKUP.put("Bromley", new double[]{559.0, 369.0, 565.0, 389.0, 556.0, 460.0, 523.0, 489.0, 514.0, 525.0, 464.0, 514.0, 441.0, 472.0, 388.0, 356.0, 446.0, 368.0, 486.0, 350.0, 511.0, 348.0});
        POINTS_LOOKUP.put("Croydon", new double[]{441.0, 472.0, 420.0, 470.0, 363.0, 523.0, 334.0, 487.0, 358.0, 456.0, 351.0, 402.0, 349.0, 378.0, 388.0, 356.0});
        POINTS_LOOKUP.put("Sutton", new double[]{334.0, 487.0, 358.0, 456.0, 351.0, 402.0, 272.0, 403.0, 264.0, 417.0, 288.0, 459.0, 299.0, 457.0});
        POINTS_LOOKUP.put("Kingston upon Thames", new double[]{264.0, 417.0, 217.0, 476.0, 204.0, 480.0, 219.0, 418.0, 213.0, 409.0, 219.0, 352.0, 234.0, 363.0, 264.0, 345.0, 272.0, 403.0});
        POINTS_LOOKUP.put("Merton", new double[]{272.0, 403.0, 351.0, 402.0, 349.0, 378.0, 347.0, 368.0, 312.0, 356.0, 310.0, 341.0, 264.0, 345.0});
        POINTS_LOOKUP.put("Richmond upon Thames", new double[]{155.0, 368.0, 165.0, 343.0, 153.0, 330.0, 202.0, 318.0, 209.0, 351.0, 219.0, 352.0, 208.0, 305.0, 229.0, 293.0, 255.0, 301.0, 265.0, 285.0, 272.0, 287.0, 277.0, 306.0, 255.0, 317.0, 264.0, 345.0, 234.0, 363.0, 219.0, 352.0, 209.0, 351.0, 205.0, 398.0, 182.0, 380.0, 160.0, 381.0});
        POINTS_LOOKUP.put("Hounslow", new double[]{202.0, 318.0, 199.0, 302.0, 228.0, 283.0, 252.0, 291.0, 264.0, 274.0, 253.0, 264.0, 206.0, 269.0, 136.0, 270.0, 101.0, 322.0, 101.0, 333.0, 155.0, 368.0, 165.0, 343.0, 153.0, 330.0});
        POINTS_LOOKUP.put("Greenwich", new double[]{511.0, 348.0, 523.0, 293.0, 550.0, 291.0, 549.0, 245.0, 526.0, 250.0, 506.0, 271.0, 471.0, 268.0, 456.0, 260.0, 452.0, 263.0, 454.0, 280.0, 427.0, 283.0, 434.0, 302.0, 456.0, 302.0, 486.0, 350.0});
        POINTS_LOOKUP.put("Lewisham", new double[]{486.0, 350.0, 456.0, 302.0, 434.0, 302.0, 427.0, 283.0, 422.0, 271.0, 406.0, 280.0, 416.0, 324.0, 388.0, 356.0, 446.0, 368.0});
        POINTS_LOOKUP.put("Southwark", new double[]{422.0, 271.0, 417.0, 259.0, 366.0, 259.0, 379.0, 302.0, 373.0, 324.0, 388.0, 356.0, 416.0, 324.0, 406.0, 280.0});
        POINTS_LOOKUP.put("Lambeth", new double[]{388.0, 356.0, 349.0, 378.0, 347.0, 368.0, 337.0, 305.0, 351.0, 288.0, 366.0, 259.0, 379.0, 302.0, 373.0, 324.0});
        POINTS_LOOKUP.put("Wandsworth", new double[]{347.0, 368.0, 312.0, 356.0, 310.0, 341.0, 264.0, 345.0, 255.0, 317.0, 277.0, 306.0, 308.0, 314.0, 319.0, 296.0, 351.0, 288.0, 337.0, 305.0});
        POINTS_LOOKUP.put("Hackney", new double[]{436.0, 205.0, 389.0, 226.0, 378.0, 229.0, 384.0, 200.0, 370.0, 175.0, 397.0, 170.0});
        POINTS_LOOKUP.put("City of London", new double[]{389.0, 226.0, 378.0, 229.0, 360.0, 234.0, 360.0, 250.0, 392.0, 248.0});
        POINTS_LOOKUP.put("Islington", new double[]{378.0, 229.0, 384.0, 200.0, 370.0, 175.0, 328.0, 172.0, 360.0, 234.0});
        POINTS_LOOKUP.put("Camden", new double[]{300.0, 221.0, 286.0, 199.0, 328.0, 172.0, 360.0, 234.0, 360.0, 250.0, 346.0, 245.0, 325.0, 219.0});
        POINTS_LOOKUP.put("Westminster", new double[]{286.0, 226.0, 300.0, 221.0, 325.0, 219.0, 346.0, 245.0, 360.0, 250.0, 345.0, 279.0, 333.0, 282.0, 328.0, 265.0, 314.0, 267.0});
        POINTS_LOOKUP.put("Kensington and Chelsea", new double[]{333.0, 282.0, 328.0, 265.0, 314.0, 267.0, 286.0, 226.0, 277.0, 226.0, 286.0, 270.0, 311.0, 288.0});
        POINTS_LOOKUP.put("Hammersmith and Fulham", new double[]{311.0, 288.0, 303.0, 302.0, 283.0, 296.0, 280.0, 280.0, 264.0, 274.0, 253.0, 264.0, 259.0, 249.0, 254.0, 225.0, 277.0, 226.0, 286.0, 270.0});
        POINTS_LOOKUP.put("Tower Hamlets",new double[]{392.0, 248.0, 389.0, 226.0, 436.0, 205.0, 442.0, 235.0, 458.0, 250.0, 442.0, 255.0, 444.0, 272.0, 434.0, 273.0, 423.0, 249.0});
    }

    /**
     * Constructor for a Menu-Polygon which represents a specific borough and contains the listing of all properties in that borough. Clicking this object will open the pane specific to that borough.
     * @param borough The borough the object represents.
     * @param listings The listings of that borough.
     */
    public MenuPolygon(Borough borough, ArrayList<NewAirbnbListing> listings) {
        super(borough, listings, getBoroughPolygon(borough.getName()));

        shapeSizeScale = 1.2;
        textSizeScale = 1.5;

//        shape.setStroke(circleColor.brighter().brighter());
//        shape.setStrokeWidth(3);
//        shape.setStrokeType(StrokeType.INSIDE);

        fontSize = 15;
        do {
            Font newFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, fontSize--);
            text.setFont(newFont);
        }while (text.getLayoutBounds().getWidth() > shape.getLayoutBounds().getWidth());
    }

    /**
     * Returns the polygon shape of the passed in borough name. If the name is invalid, an empty polygon is returned.
     * @param boroughName The name of the Borough.
     * @return The polygon in the shape of the borough.
     */
    private static Polygon getBoroughPolygon(String boroughName){
        if (POINTS_LOOKUP.containsKey(boroughName)){
            return new Polygon(POINTS_LOOKUP.get(boroughName));
        }
        else {
            System.err.println("NON-RECOGNISED BOROUGH NAME - Assigning empty Polygon");
            return new Polygon();
        }
    }

    /**
     * Get the polygon object of the object.
     * @return The polygon object of the object.
     */
    public Polygon getBoroughShape() {
        return (Polygon) shape;
    }

    /**
     * Set the position of the object dependent on the polygon shape, ignoring the text.
     * @param x X position to be set.
     * @param y Y position to be set.
     */
    public void setXY(double x, double y){
        double textWidth = text.getLayoutBounds().getWidth();
        double polygonWidth = shape.getLayoutBounds().getWidth();
        if (textWidth > polygonWidth){
             x -= (textWidth - polygonWidth)/2;
        }
        setLayoutX(x);
        setLayoutY(y);
    }
}
