import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
 * @version 26.03.2022
 */
public class InspectMenuController implements Initializable {

    @FXML private Label title;

    @FXML private RadioButton unload_radio;

    @FXML private ComboBox<Integer> min, max;
    @FXML private RadioButton show_invalid;
    @FXML private ComboBox<String> sortby, order;

    @FXML private BorderPane left;
    @FXML private ScrollPane right;

    @FXML private Rectangle very_high, high_to_low, very_low, unknown, out_of_range;

    private static InspectBoxController inspectBoxController;
    private static InspectListingMenu inspectMenu;

    private static ArrayList<NewAirbnbListing> currentListings;
    private static Stage stage = null;
    private static double xOffset, yOffset;
    private static double widthOffset, heightOffset;

    /**
     * Assigns new list order parameters and then calls a relist for the inspect-listing menu.
     */
    @FXML private void callRelist(){
        InspectListingMenu.setSortSelected(sortby.getValue());
        InspectListingMenu.setOrderSelected(order.getValue());
        InspectListingMenu.setShowOutOfRange(show_invalid.isSelected());

        RuntimeDetails.setMinimumPrice(min.getValue());
        RuntimeDetails.setMaximumPrice(max.getValue());

        inspectMenu.reList();
    }

    /**
     * Toggles if the images should unload offscreen or not.
     */
    @FXML private void toggleOffScreenUnload(){
        inspectMenu.toggleUnloadOffscreen();
    }

    /**
     * Closes the window.
     */
    @FXML private void closeWindow(){
        stage.close();
    }

    /**
     * Sets position of mouse on the stage. Used for recreating how window tile bars work and window resizing.
     * @param event MouseEvent call.
     */
    @FXML private void setOffset(MouseEvent event){
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();

        widthOffset = stage.getWidth();
        heightOffset = stage.getHeight();
    }

    /**
     * Moves window to mouse. Used for recreating how window tile bars work.
     * @param event MouseEvent call.
     */
    @FXML private void toMouse(MouseEvent event){
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    /**
     * Implements a west corner stage resize functionality which isn't native to undecorated windows.
     * @param event MouseEvent call.
     */
    @FXML private void eastResize(MouseEvent event){
        double newWidth = widthOffset + event.getSceneX() + xOffset;
        if(newWidth > stage.getMinWidth()) {
            stage.setWidth(newWidth);
        }
    }

    /**
     * Implements a south corner stage resize functionality which isn't native to undecorated windows.
     * @param event MouseEvent call.
     */
    @FXML private void southResize(MouseEvent event){
        double newHeight = heightOffset + event.getSceneY() + yOffset;
        if(newHeight > stage.getMinHeight()) {
            stage.setHeight(newHeight);
        }
    }

    /**
     * Implements a south-west corner stage resize functionality which isn't native to undecorated windows.
     * @param event MouseEvent call.
     */
    @FXML private void southeastResize(MouseEvent event){
        double newWidth = widthOffset + event.getSceneX() + xOffset;
        if(newWidth > stage.getMinWidth()) {
            stage.setWidth(newWidth);
        }
        double newHeight = heightOffset + event.getSceneY() + yOffset;
        if(newHeight > stage.getMinHeight()) {
            stage.setHeight(newHeight);
        }
    }

    /**
     * Creates and shows the new stage for the Inspect-Menu.
     * @param listings The listings you want to display in the menu.
     * @param title The title of the window displayed at the top.
     */
    public static void create(ArrayList<NewAirbnbListing> listings, String title){
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

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(750);
        stage.setMinHeight(400);

        Scene scene = new Scene(root, 1400, 800);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Sets a new listing to display in the Inspect-Menu and also refreshed the content.
     * @param newListing The new listing you wish to set the Inspect-Menu to.
     */
    public static void setInspectBoxListing(NewAirbnbListing newListing){
        inspectBoxController.setListing(newListing);
    }

    /**
     * Initialises the FXML component.
     * @param location FXML placeholder location.
     * @param resources FXML placeholder resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Since state is currently null, runLater is preformed for when it is visible.
        Platform.runLater(() -> {
            Stage stage = (Stage)title.getScene().getWindow();
            title.setText(stage.getTitle());
        });

        // Assigning the ComboBox's
        min.setItems(Pane1Controller.AVAILABLE_PRICES);
        max.setItems(Pane1Controller.AVAILABLE_PRICES);
        min.setValue(RuntimeDetails.getMinimumPrice());
        max.setValue(RuntimeDetails.getMaximumPrice());

        sortby.setItems(InspectListingMenu.SORT_OPTIONS);
        order.setItems(InspectListingMenu.ORDER_OPTIONS);
        sortby.getSelectionModel().selectFirst();
        order.getSelectionModel().selectFirst();

        // Assigning the colours of they key.
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

        // Assigning the Unload-Radio status.
        unload_radio.setSelected(InspectListingMenu.isUnloadOffscreen());

        // Assigning the SplitPane contents
        inspectMenu = new InspectListingMenu(currentListings);
        left.setCenter(inspectMenu);

        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane inspectBox = null;
        try {
            inspectBox = fxmlLoader.load((getClass().getResource("inspectbox.fxml")).openStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        inspectBoxController = fxmlLoader.getController();
        right.setContent(inspectBox);
    }
}
