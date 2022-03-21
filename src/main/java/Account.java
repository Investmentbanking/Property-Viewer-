import com.opencsv.CSVReader;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Account {
    private String username;
    private ArrayList<NewAirbnbListing> bookings;
    private ArrayList<NewAirbnbListing> desiredProperties;
    private final URL url = this.getClass().getResource("reservation.csv");

    public Account() {
        this.bookings = new ArrayList<>();
        this.username = "";
        this.desiredProperties = new ArrayList<>();
        addProperties();
    }

    private void addProperties() {
        desiredProperties.add(new NewAirbnbListing("12", "asd"));
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<NewAirbnbListing> getBookings() {
        return bookings;
    }

    public ArrayList<NewAirbnbListing> getDesiredProperty(){
        return desiredProperties;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPropertyTaken() {
        try {
            String file = new File(url.toURI()).getAbsolutePath();
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;

            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String propertyID = line[0];
                String propertyBookedBy = line[1];

                for (NewAirbnbListing listings : desiredProperties) {
                    if(listings.getId().equals(propertyID)){
                        new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry :( \n this property is already reserved by a user with the ID: " + propertyBookedBy);
                        return true;
                    }
                }
            }
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void reserveProperty(){
        bookings.addAll(desiredProperties);
        desiredProperties.clear();
    }
}
