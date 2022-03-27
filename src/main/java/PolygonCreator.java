import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class was made to create the polygons for the MenuPolygon class. It shouldn't have any connection
 * to the Property Viewer project and is only being included for context, evidence and in case more polygon
 * maps would like to be made in the future.
 *
 * Setting up the class:
 *      - Before anything, you need to declare an instance of this class in the JavaFX main class and then
 *          set the instance as the root of the scene for the stage, then the program will be ready to use.
 *
 * How to use:
 *      1) The MAP_IMAGE constant will place an image in the background of the pane if you want to use it
 *          as a reference for points.
 *
 *      2) Left-click the scene in places where you want the edges of the polygons to be. The main appeal
 *          of this program is how multiple polygons can share the same edges so keep that in mind.
 *
 *      3) When you've places the coordinates you want, you can then right-click to select certain points
 *          you want to make into a polygon.
 *
 *      4) Once you've selected all the points, middle-click the scene with your mouse to create a polygon
 *          in that area. it will then unselect all the polygons you selected, but they will remain on
 *          the scene.
 *
 *      5) You can repeat this process to create as many polygons are you want.
 *
 *      6) When you've finished making the polygons, press the "O" key on your keyboard. This will print
 *          out all the commands to make those polygons in the console.
 *
 *      7) You can now paste these coordinates wherever you want these polygon shapes to be.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class PolygonCreator extends Pane {

    // Change this value to whatever image you want the background reference to be.
    private final Image MAP_IMAGE = new Image("https://hidden-london.com/wp-content/uploads/2018/03/London-boroughs-map-742.png");

    ArrayList<Double> points;   // Stores coordinate-points when circles are selected.
    ArrayList<Circle> circles;  // The circles on the scene.
    ArrayList<String> commands; // The commands to make polygons.

    /**
     * Constructor for the polygon creator.
     */
    public PolygonCreator() {

        points = new ArrayList<>();
        circles = new ArrayList<>();
        commands = new ArrayList<>();

        // The original map that served as a reference for the GeoMap.
        ImageView image = new ImageView(MAP_IMAGE);
        image.setMouseTransparent(true);
        getChildren().add(image);

        setOnMouseClicked(this::sceneClick);

        // The key "O" prints all the commands to make polygons.
        Platform.runLater(() -> getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.O) {
                System.out.println("--PRINT METHODS--");
                commands.forEach(System.out::println);
            }
        }));

    }

    /**
     * Actions when you left or middle-click the scene.
     * When you Left-Click the scene, you place a circle. This circle represents coordinates that you want to be the corner of a polygon.
     * When you Middle-Click the scene, the program will compile all the green circle you have previously selected and then turns them
     * into a polygon shame. It also deselects the circles.
     * @param e MouseEvent on scene click.
     */
    private void sceneClick(MouseEvent e){
        if (e.getButton() == MouseButton.PRIMARY) {
            double x = (double) Math.round(e.getX());
            double y = (double) Math.round(e.getY());
            System.out.println("Set circle at: X: " + x + ", Y: " + y);
            Circle circle = new Circle(x, y, 5, Color.BLUE);
            circles.add(circle);
            getChildren().add(circle);
            circle.setOnMouseClicked(this::circleClick);
        }
        if (e.getButton() == MouseButton.MIDDLE) {
            if(points.size() <= 4){ // 2 * 2 = 4 because points are stored twice.
                System.out.println("Please select at least 3 points");
            }
            else {
                System.out.println("Made Polygon with: " + points.toString());
                Polygon polygon = new Polygon();
                Random rnd = new Random();
                polygon.setFill(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255), 0.7));
                polygon.setMouseTransparent(true);
                polygon.getPoints().addAll(points);
                getChildren().add(polygon);

                String stringPoints = "";
                for (double d : points) {
                    stringPoints += d + ", ";
                }
                stringPoints = stringPoints.substring(0, stringPoints.length() - 2);

                commands.add(String.format("Polygon polygon%s = new Polygon(%s);", commands.size(), stringPoints));

                points = new ArrayList<>();
                circles.forEach(circle -> circle.setFill(Color.BLUE));
            }
        }
    }

    /**
     * Selects a circle. When a circle is selected with Right-Click it adds its coordinates to a list.
     * @param e MouseEvent on circle click.
     */
    private void circleClick(MouseEvent e){
        if (e.getButton() == MouseButton.SECONDARY) {
            Circle click =  (Circle)(e.getSource());
            double x = click.getCenterX();
            double y = click.getCenterY();

            System.out.println("Select circle: X: " + x + ", Y: " + y);

            click.setFill(Color.GREEN);
            points.add(x);
            points.add(y);
        }
    }
}
