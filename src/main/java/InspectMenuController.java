import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The controller class for the FXML pane that displays all the listings of a particular borough.
 * On the left it displays all the listings and on the right is all the info for that listing.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class InspectMenuController implements Initializable {

    @FXML private Label title;

    @FXML private ComboBox<Integer> min, max;
    @FXML private RadioButton show_invalid;
    @FXML private ComboBox<String> sortby, order;

    @FXML private Button relist;

    @FXML private BorderPane left;
    @FXML private ScrollPane right;

    @FXML private Rectangle very_high, high_to_low, very_low, unknown, out_of_range;
    @FXML private GridPane key;

    private static InspectBoxController inspectBoxController;

    private static ArrayList<NewAirbnbListing> currentListings;
    private static Stage stage = null;
    private static double xOffset, yOffset;

    private static InspectListingMenu inspectMenu;

    @FXML private void callRelist(){
        System.out.println("Relist Called");
        InspectListingMenu.setSortSelected(sortby.getValue());
        InspectListingMenu.setOrderSelected(order.getValue());
        InspectListingMenu.setShowOutOfRange(show_invalid.isSelected());

        RuntimeDetails.setMinimumPrice(min.getValue());
        RuntimeDetails.setMaximumPrice(max.getValue());



        System.out.println("Values reassigned: " + sortby.getValue() + " " );
        System.out.println(sortby.getValue() + " " + order.getValue());
        System.out.println("New Min: " + min.getValue() + " New Max: " + max.getValue());

        inspectMenu.reList();

    }

    /**
     * Closes the window.
     */
    @FXML private void closeWindow(){
        stage.close();
    }

    /**
     * Sets position of mouse on title bar.
     * @param event MouseEvent
     */
    @FXML private void setOffset(MouseEvent event){
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    /**
     * Moves window to mouse.
     * @param event MouseEvent
     */
    @FXML private void toMouse(MouseEvent event){
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    public static void create(ArrayList<NewAirbnbListing> listings, String name){
        currentListings = listings;

        URL url = InspectMenuController.class.getResource("inspectmenu.fxml");
        assert url != null;
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);

        if (stage == null) {
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
        }

        stage.setScene(scene);
        stage.setTitle(name);
        stage.show();
    }

    public static void setInspectBoxListing(NewAirbnbListing newListing){
        inspectBoxController.setListing(newListing);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Stage stage = (Stage)title.getScene().getWindow();
            title.setText(stage.getTitle());
        });


        min.setItems(Pane1Controller.AVAILABLE_PRICES);
        max.setItems(Pane1Controller.AVAILABLE_PRICES);
//        min.getSelectionModel().select(min.getItems().indexOf(RuntimeDetails.getMinimumPrice()));
//        max.getSelectionModel().select(max.getItems().indexOf(RuntimeDetails.getMaximumPrice()));
        min.setValue(RuntimeDetails.getMinimumPrice());
        max.setValue(RuntimeDetails.getMaximumPrice());

        sortby.setItems(InspectListingMenu.SORT_OPTIONS);
        order.setItems(InspectListingMenu.ORDER_OPTIONS);
        sortby.getSelectionModel().selectFirst();
        order.getSelectionModel().selectFirst();


        very_high.setFill(ListingBox.VERY_HIGH_REVIEW_COLOUR);
        Stop[] stops = new Stop[] {
                new Stop(0.0, ListingBox.HIGH_REVIEW_COLOUR),
                new Stop(0.5, ListingBox.MEDIUM_REVIEW_COLOUR),
                new Stop(1.0, ListingBox.LOW_REVIEW_COLOUR)
        };
        LinearGradient gradient = new LinearGradient(1, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        high_to_low.setFill(gradient);
        very_low.setFill(ListingBox.VERY_LOW_REVIEW_COLOUR);
        unknown.setFill(ListingBox.UNKNOWN_REVIEW_COLOUR);
        out_of_range.setFill(ListingBox.OUT_OF_RANGE_COLOUR);

        key.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

        key.setStyle("-fx-border-radius: 15");


        inspectMenu = new InspectListingMenu(currentListings);
        left.setCenter(inspectMenu);

        // Credit to https://stackoverflow.com/a/10753277/11245518
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane inspectBox = null;
        try {
            inspectBox = fxmlLoader.load(getClass().getResource("inspectbox.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inspectBoxController = (InspectBoxController)fxmlLoader.getController();

        right.setContent(inspectBox);
    }
}
