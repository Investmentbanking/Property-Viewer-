import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class BookingController {
    private static ObservableList<NewAirbnbListing> listings = FXCollections.observableArrayList();

    @FXML
    ListView listOfProperties;

    @FXML
    Label totalNights, price, name, beds, baths, area, from, to;

    @FXML
    ImageView picture;

    @FXML
    public void confirmBooking(ActionEvent event) {
        Account currentUser = MainController.getCurrentUser();
        if(currentUser.getUsername() == null){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry you need an account to be able to book a property!");
        }else{
            if(listOfProperties.getSelectionModel().getSelectedItem() != null) {
                NewAirbnbListing currentItem = (NewAirbnbListing) listOfProperties.getSelectionModel().getSelectedItem();
                if(!currentUser.isPropertyTaken(currentItem)){
                    // actually book in the property
                    currentUser.reserveProperty(currentItem);
                    new Alerts(Alert.AlertType.CONFIRMATION,"Success", null, currentUser.getUsername() + " has reserved property with property ID: " + currentItem.getId());
                    listings.remove(currentItem);
                    reset();
                }else {
                    new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry someone else booked this property!");
                }

            }
                // check that this property isn't registered to another user

        }
    }


    public void initialize() {
        loadProperties();
    }

    public void loadProperties(){
        listOfProperties.setItems(listings);
    }

    public static void addListing(NewAirbnbListing property){
        listings.add(property);
    }

    public void handleSelectionByTheUser(MouseEvent mouseEvent) {
        if(listOfProperties.getSelectionModel().getSelectedItem() != null){
            NewAirbnbListing currentItem = (NewAirbnbListing) listOfProperties.getSelectionModel().getSelectedItem();
            totalNights.setText(String.valueOf(RuntimeDetails.getTotalNights()));
            double calculatedPrice = RuntimeDetails.getTotalNights() * currentItem.getPrice();
            price.setText(String.valueOf(calculatedPrice));

            Image pictureImage = loadImage(currentItem.getPictureURL(), false);
            picture.setImage(pictureImage);

            from.setText(String.valueOf(RuntimeDetails.getStartDate()));
            to.setText(String.valueOf(RuntimeDetails.getEndDate()));

            name.setText(currentItem.getName());
            beds.setText(String.valueOf(currentItem.getBeds()));
            baths.setText(String.valueOf(currentItem.getBathrooms()));
            area.setText(currentItem.getNeighbourhoodCleansed());
        }
        System.out.println(listings.size());
        System.out.println("clicked on " + listOfProperties.getSelectionModel().getSelectedItem());
    }


    private void reset(){
        name.setText("Thanks for booking :)");
        picture.setImage(loadImage(null, false));
        beds.setText("-");
        baths.setText("-");
        area.setText("-");
        totalNights.setText("-");
        price.setText("-");
        from.setText("-");
        to.setText("-");
    }

    public static boolean checkProperty(NewAirbnbListing listing){
        for(NewAirbnbListing property : listings){
            if(property.getId().equals(listing.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an image loaded from a URL and if the URL is invalid a placeholder image is loaded instead.
     * @param url URL of the image.
     * @param backgroundLoading True if you want the image to be leaded in a background thread.
     * @return The Image class of the URL or placeholder image.
     */
    private Image loadImage(URL url, boolean backgroundLoading){
        if (url != null) {
            Image image = new Image(url.toString(), backgroundLoading);
            if (!image.isError()) {
                return image;
            }
        }
        return new Image("imagePlaceholder.jpg");
    }
}
