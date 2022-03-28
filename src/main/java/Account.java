import com.opencsv.CSVReader;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Account {
    private String username;
    private ArrayList<NewAirbnbListing> bookings;
    private final URL url = this.getClass().getResource("reservation.csv");
    private final String file = new File(url.toURI()).getAbsolutePath();


    public Account() throws URISyntaxException {
        this.bookings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<NewAirbnbListing> getBookings() {
        return bookings;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPropertyTaken(NewAirbnbListing property) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;

            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String propertyID = line[0];
                String propertyBookedBy = line[1];

                if(property.getId().equals(propertyID)){
                    if(propertyBookedBy.equals(MainController.getCurrentUser().getUsername())){
                        new Alerts(Alert.AlertType.WARNING,"Warning", null, "this property is already reserved by you!");
                    }else {
                        new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry :( \n this property is already reserved by another user");
                    }
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void bookProperty(NewAirbnbListing property){
        try {
            FileWriter writer = new FileWriter(file, true);
            String newBooking = (property.getId()) + "," + MainController.getCurrentUser().getUsername() + '\n';
            writer.write(newBooking);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reserveProperty(NewAirbnbListing property){
        bookings.add(property);
        bookProperty(property);
    }
}
