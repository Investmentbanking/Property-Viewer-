import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class BookingController {
    private ObservableList<NewAirbnbListing> listings = FXCollections.observableArrayList();

    @FXML
    ListView listOfProperties;

    @FXML
    Label totalNights;

    @FXML
    public void confirmBooking(ActionEvent event) {
        Account currentUser = Pane1Controller.getCurrentUser();
        if(currentUser.getUsername() == null){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry you need an account to be able to book a property!");
        }else{
            // check that this property isn't registered to another user
            if(!currentUser.isPropertyTaken()){
                // actually book in the property
                currentUser.reserveProperty();
                new Alerts(Alert.AlertType.CONFIRMATION,"Success", null, currentUser.getUsername() + "has reserved property with property ID: " + currentUser.getDesiredProperty());
                initialize();
            }else {
                new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry someone else booked this property!");
            }
        }
    }


    public void initialize() {
        loadProperties();
    }

    public void loadProperties(){
        listings.clear();
        Account currentUser = Pane1Controller.getCurrentUser();
        listings.addAll(currentUser.getDesiredProperty());
        listOfProperties.setItems(listings);
    }

    public void handleSelectionByTheUser(MouseEvent mouseEvent) {
        if(listOfProperties.getSelectionModel().getSelectedItem() != null){
            Object currentItem = listOfProperties.getSelectionModel().getSelectedItem();
            totalNights.setText(listOfProperties.getSelectionModel().getSelectedItem().getClass().getName());
        }
        System.out.println(listings.size());
        System.out.println("clicked on " + listOfProperties.getSelectionModel().getSelectedItem());
    }
}
