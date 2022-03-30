import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;


/**
 * This class is responsible for Booking. It acts as the main controller behind the bookingPane.fxml file.
 * It handles all the input by the user + any button clicked
 *
 * @author Syraj Alkhalil k21007329
 * @version 1.0
 */
public class BookingController {
    // this is the list that will be observed automatically by the system
    private static final ObservableList<NewAirbnbListing> listings = FXCollections.observableArrayList();

    // The main table that holds the list of all bookings to show
    @FXML
    TableView listOfProperties;

    // Simple fields that are manipulated to display stats about the property
    @FXML
    Label totalNights, price, name, beds, baths, area, from, to, visibleText;

    // The button which is made visible depending on a certain condition.
    @FXML
    Button visibleButton;

    // The main picture of the property
    @FXML
    ImageView picture;


    /**
     * The main method of this class
     */
    public void initialize() {
        loadProperties();
        if(MainController.getCurrentUser().getUsername() != null){
            visibleButton.setVisible(false);
            visibleText.setVisible(false);
        }
    }

    /**
     * This method is responsible for setting up the table and telling the table to start observing the observable list "listings"
     */
    public void loadProperties(){
        // the column that shows the name of the bookings
        TableColumn listingName = new TableColumn("Name");
        listingName.setCellFactory(TextFieldTableCell.forTableColumn());
        listingName.setCellValueFactory(new PropertyValueFactory<NewAirbnbListing, String>("name"));
        listOfProperties.getColumns().clear();
        listOfProperties.getColumns().addAll(listingName);
        listOfProperties.setItems(listings);
    }

    /**
     * This method is responsible for booking the selected property. If there is no
     * property selected then an appropriate message will be shown.
     *
     * @param event the confirm booking button is clicked
     */
    @FXML
    public void confirmBooking(ActionEvent event) {
        Account currentUser = MainController.getCurrentUser();
        if(currentUser.getUsername() == null){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry you need an account to be able to book a property!");
        }else{
            if(listOfProperties.getSelectionModel().getSelectedItem() != null) {
                NewAirbnbListing currentItem = (NewAirbnbListing) listOfProperties.getSelectionModel().getSelectedItem();
                // checking that this property isn't registered to another user or the current user
                if(!currentUser.isPropertyTaken(currentItem) && !currentUser.noTimeLimitViolation(currentItem)){
                    // actually book in the property
                    currentUser.reserveProperty(currentItem);
                    new Alerts(Alert.AlertType.CONFIRMATION,"Success", null, currentUser.getUsername() + " has reserved property with property ID: " + currentItem.getId());
                    listings.remove(currentItem);
                    reset();
                }else{
                    reset();
                    name.setText("Property Name");
                }
            }else {
                new Alerts(Alert.AlertType.WARNING,"Error", null, "Please select/book a property first before confirming");
            }
        }
    }

    /**
     * This method is responsible for handling the selected item by the user.
     * The method will update all the fields displayed to reflect the correct changes that will occur after
     * the selection of a property.
     *
     * @param mouseEvent a given property is clicked by the user
     */
    @FXML
    public void handleSelectionByTheUser(MouseEvent mouseEvent) {
        if(listOfProperties.getSelectionModel().getSelectedItem() != null){
            NewAirbnbListing currentItem = (NewAirbnbListing) listOfProperties.getSelectionModel().getSelectedItem();
            totalNights.setText(String.valueOf(RuntimeDetails.getTotalNights()));
            double calculatedPrice = RuntimeDetails.getTotalNights() * currentItem.getPrice();
            price.setText("$" + calculatedPrice);

            Image pictureImage = loadImage(currentItem.getPictureURL());
            picture.setImage(pictureImage);

            from.setText(String.valueOf(RuntimeDetails.getStartDate()));
            to.setText(String.valueOf(RuntimeDetails.getEndDate()));

            name.setText(currentItem.getName());
            beds.setText(String.valueOf(currentItem.getBeds()));
            baths.setText(String.valueOf(currentItem.getBathrooms()));
            area.setText(currentItem.getNeighbourhoodCleansed());
        }
    }

    /**
     * This method is only responsible for resetting the fields displayed on the screen
     * to their original text in addition to displaying a "Thank You" message to the user.
     */
    private void reset(){
        name.setText("Thanks for booking :)");
        picture.setImage(loadImage(null));
        beds.setText("-");
        baths.setText("-");
        area.setText("-");
        totalNights.setText("-");
        price.setText("-");
        from.setText("-");
        to.setText("-");
    }

    /**
     * This method is responsible for checking if a property is taken by the current user or another user.
     *
     * @param listing the listing we wish to check for
     * @return true if the listing is indeed taken in any way shape or form, false otherwise
     */
    public static boolean checkProperty(NewAirbnbListing listing){
        for(NewAirbnbListing property : listings){
            if(property.getId().equals(listing.getId())){
                new Alerts(Alert.AlertType.ERROR,"Error", null," You can add properties only once!");
                return true;
            }
        }
        return MainController.getCurrentUser().isPropertyTaken(listing);
    }

    /**
     * A simple method to change the current fxml file with another (sign up page)
     *
     * @param event signup button clicked
     * @throws IOException if the corresponding fxml file isn't found
     */
    @FXML
    public void signup(ActionEvent event) throws IOException {
        InitialController.setRoot("signup.fxml");
    }

    /**
     * This method is responsible for adding to the observable list
     *
     * @param property the property we wish to add
     */
    public static void addListing(NewAirbnbListing property){
        listings.add(property);
    }


    /**
     * Returns an image loaded from a URL and if the URL is invalid a placeholder image is loaded instead.
     * @param url URL of the image.
     * @return The Image class of the URL or placeholder image.
     */
    private Image loadImage(URL url){
        if (url != null) {
            Image image = new Image(url.toString(), false);
            if (!image.isError()) {
                return image;
            }
        }
        return new Image("imagePlaceholder.jpg");
    }
}
