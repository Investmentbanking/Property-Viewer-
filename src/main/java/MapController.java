import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * The controller class for the FXML pane that holds the map of the london boroughs.
 * The user can click a circle representing a borough to open a window that displays all the properties of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 26.03.2022
 */
public class MapController implements Initializable {

    @FXML private BorderPane pane;

    @FXML private Button toggle_map;
    private final ArrayList<String> SWITCH_LABEL = new ArrayList<>(Arrays.asList("Show Bubbles", "Show Geographical"));

    @FXML private GridPane geo_key, circle_key;
    @FXML private Rectangle low_to_high_geo, low_to_high_circle;

    private ArrayList<NewAirbnbListing> listings;

    private ArrayList<Pane> mapsPanes;

    /**
     * Open the inspection window for all listings in the dataset. Unspecific to any Borough.
     */
    @FXML
    private void openInspectionWindowForAll(){
        InspectMenuController.create(listings, "All Listings");
    }

    /**
     * Switches the displayed map between Geographical and Bubble.
     */
    @FXML
    private void switchMap(){
        int currentIndex = SWITCH_LABEL.indexOf(toggle_map.getText());
        toggle_map.setText(SWITCH_LABEL.get(1 - currentIndex));
        pane.setCenter(mapsPanes.get(1 - currentIndex));

        geo_key.setVisible(!geo_key.isVisible());
        circle_key.setVisible(!circle_key.isVisible());
    }

    /**
     * Initialises the FXML component.
     * @param location FXML placeholder location.
     * @param resources FXML placeholder resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Background keyBackground = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY));
        geo_key.setBackground(keyBackground);
        circle_key.setBackground(keyBackground);
        geo_key.setVisible(true);
        circle_key.setVisible(false);

        Stop[] stops = new Stop[] {
                new Stop(0.0, MenuCircle.LOW_COLOR),
                new Stop(1.0, MenuCircle.HIGH_COLOR)
        };
        LinearGradient gradient = new LinearGradient(1, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        low_to_high_geo.setFill(gradient);
        low_to_high_circle.setFill(gradient);

        listings = RuntimeDetails.getNewAirbnbListings();

        GeoMap geoMap = new GeoMap();
        CircleMap circleMap = new CircleMap();

        mapsPanes = new ArrayList<>();
        mapsPanes.add(geoMap);
        mapsPanes.add(circleMap);

        toggle_map.setText(SWITCH_LABEL.get(0));
        pane.setCenter(mapsPanes.get(0));
    }
}
