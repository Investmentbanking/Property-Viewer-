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
    private String username;                                                        // the username
    private ArrayList<NewAirbnbListing> bookings;                                   // the actual bookings of this person
    private final URL url = this.getClass().getResource("reservation.csv");   // the overall url used like this to reduce repetition
    private final String file = new File(url.toURI()).getAbsolutePath();            // the location of the file used like this to reduce repetition


    /**
     * The main constructor of the Account class, initialises the current user with an empty list of bookings
     *
     * @throws URISyntaxException when the CSV isn't found.
     */
    public Account() throws URISyntaxException {
        this.bookings = new ArrayList<>();
    }

    /**
     * A simple method to determine if a certain property has been taken and by whom,
     * if the property is already reserved by the current user a message will be displayed
     * accordingly.
     *
     * @param property the property we are checking
     * @return true if the property is taken, false otherwise
     */
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

    /**
     * A simple method to book the property permanently
     *
     * @param property the property we wish to book.
     */
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

    /**
     * This method is responsible for adding the current property to the list of
     * all properties reserved by the current user.
     *
     * @param property the property we wish to reserve.
     */
    public void reserveProperty(NewAirbnbListing property){
        bookings.add(property);
        bookProperty(property);
    }

    /**
     * A simple getter method to retrieve the username field
     *
     * @return the String representation of the username field.
     */
    public String getUsername() {
        return username;
    }

    /**
     * A simple getter method to retrieve the bookings field
     *
     * @return the ArrayList representation of the field bookings.
     */
    public ArrayList<NewAirbnbListing> getBookings() {
        return bookings;
    }

    /**
     * A simple setter method to set the username field
     *
     * @param username the username we wish to set the field username to.
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
