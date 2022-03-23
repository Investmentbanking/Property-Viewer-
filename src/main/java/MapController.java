import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The controller class for the FXML pane that holds the map of the london boroughs.
 * The user can click a circle representing a borough to open a window that displays all the properties of that borough.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class MapController implements Initializable {

    @FXML private ScrollPane pane;

    @FXML private GridPane key;
    @FXML private Rectangle low_to_high;

    ArrayList<NewAirbnbListing> listings;

    /**
     * Open the inspection window for all listings in the dataset. Unspecific to any Borough.
     */
    @FXML
    private void openInspectionWindowForAll(){
        InspectMenuController.create(listings, "All Listings");
    }

    /**
     * Initialises the FXML component.
     * @param location FXML placeholder location.
     * @param resources FXML placeholder resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        key.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

        Stop[] stops = new Stop[] {
                new Stop(0.0, MenuCircle.CHEAP_COLOR),
                new Stop(1.0, MenuCircle.EXPENSIVE_COLOR)
        };
        LinearGradient gradient = new LinearGradient(1, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        low_to_high.setFill(gradient);

        listings = RuntimeDetails.getNewAirbnbListings();

        MapPane map = new MapPane(listings);
        pane.setContent(map);
    }
}
