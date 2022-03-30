import com.opencsv.CSVReader;
import javafx.scene.control.Alert;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * This class is responsible for holding an account. It allows the user to be modelled as an object
 * which in turn will allow the programmer to do whatever they want with the user, from allowing them to login, signup
 * or even continuing as a guest.
 *
 * @author Syraj Alkhalil k21007329
 * @version 1.0
 */
public class Account {
    private String username;                                                        // The username
    private ArrayList<NewAirbnbListing> bookings;                                   // The actual bookings of this person
    private final URL url = this.getClass().getResource("reservation.csv");   // The overall url used like this to reduce repetition
    private final String file = new File(url.toURI()).getAbsolutePath();            // The location of the file used like this to reduce repetition


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

            // actual dates set by the user
            LocalDate preferredStartDate = RuntimeDetails.getStartDate();
            LocalDate preferredEndDate   = RuntimeDetails.getEndDate();

            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String propertyID = line[0];
                LocalDate startDate = convertDate(line[2]);
                LocalDate endDate = convertDate(line[3]);

                if(property.getId().equals(propertyID)){
                    // check there isn't a date clash
                    // if these are true we don't book they can't be simplified.
                    if ((preferredStartDate.isBefore(startDate) && preferredEndDate.isAfter(startDate)) || (preferredStartDate.isBefore(endDate) && preferredEndDate.isAfter(endDate)) || (preferredStartDate.isAfter(startDate) && preferredEndDate.isBefore(endDate)) || (preferredStartDate.equals(startDate) || preferredEndDate.equals(endDate))){
                        // then book
                        new Alerts(Alert.AlertType.WARNING,"Warning", null, "this property is already reserved in this time");
                        return true;
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * This method is responsible for checking if a time violation exists when booking
     * a property. A time violation is defined as attempting to book a property for too long
     * or too short.
     *
     * @param property the property we wish to check
     * @return true if there exists a time violation. explained above.
     */
    public boolean noTimeLimitViolation(NewAirbnbListing property){
        if(RuntimeDetails.getTotalNights() < property.getMinimumNights() || RuntimeDetails.getTotalNights() > property.getMaximumNights()){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry :( \nthe dates you have chosen aren't suitable for this property. \nPlease try to change the dates to be able to book this property");
            return true;
        }
        return false;
    }

    /**
     * This method is responsible for converting a given string to LocalDate
     *
     * @param dateString the string we wish to convert
     * @return LocalDate object of the string we passed in.
     */
    private LocalDate convertDate(String dateString){
        // date should be the same as date we have when we write
        return LocalDate.of(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7)), Integer.parseInt(dateString.substring(8, 10)));
    }

    /**
     * A simple method to book the property permanently
     *
     * @param property the property we wish to book.
     */
    private void bookProperty(NewAirbnbListing property){
        try {
            FileWriter writer = new FileWriter(file, true);
            LocalDate preferredStartDate = RuntimeDetails.getStartDate();
            LocalDate preferredEndDate   = RuntimeDetails.getEndDate();
            String newBooking = (property.getId()) + "," + MainController.getCurrentUser().getUsername() + "," + preferredStartDate + ","  + preferredEndDate + '\n';
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
