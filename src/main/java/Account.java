import com.opencsv.CSVReader;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

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

            // actual dates set by the user
            Date preferredStartDate = new Date(RuntimeDetails.getStartDate().getYear() - 1900, RuntimeDetails.getStartDate().getMonthValue() - 1, RuntimeDetails.getStartDate().getDayOfMonth()+1);
            Date preferredEndDate   = new Date(RuntimeDetails.getEndDate().getYear() - 1900, RuntimeDetails.getEndDate().getMonthValue() - 1, RuntimeDetails.getEndDate().getDayOfMonth()+1);

            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String propertyID = line[0];
                Date startDate = convertDate(line[2]);
                Date endDate = convertDate(line[3]);

                if(property.getId().equals(propertyID)){
                    System.out.println(preferredStartDate.toInstant());
                    System.out.println(preferredEndDate.toInstant());
                    System.out.println(startDate.toInstant());
                    System.out.println(endDate.toInstant());

                    // check there isn't a date clash
                    // if these are true we don't book they can't be simplified.
                    if ((preferredStartDate.before(startDate) && preferredEndDate.after(startDate)) || (preferredStartDate.before(endDate) && preferredEndDate.after(endDate)) || (preferredStartDate.after(startDate) && preferredEndDate.before(endDate)) || (preferredStartDate.equals(startDate) || preferredEndDate.equals(endDate))){
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

    public boolean noTimeLimitViolation(NewAirbnbListing property){
        if(RuntimeDetails.getTotalNights() < property.getMinimumNights() || RuntimeDetails.getTotalNights() > property.getMaximumNights()){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Sorry :( \nthe dates you have chosen aren't suitable for this property. \nPlease try to change the dates to be able to book this property");
            return true;
        }
        return false;
    }

    private Date convertDate(String dateString){
        // date should be the same as date we have when we write
//        String dateWeHave = String.valueOf(new Date(2020 - 1900, 1 - 1, 7).toInstant());
        return new Date(Integer.valueOf(dateString.substring(0, 4)) - 1900, Integer.valueOf(dateString.substring(5, 7))-1, Integer.valueOf(dateString.substring(8, 10)) + 1);
    }

    /**
     * A simple method to book the property permanently
     *
     * @param property the property we wish to book.
     */
    private void bookProperty(NewAirbnbListing property){
        try {
            FileWriter writer = new FileWriter(file, true);
            Date preferredStartDate = new Date(RuntimeDetails.getStartDate().getYear() - 1900, RuntimeDetails.getStartDate().getMonthValue() - 1, RuntimeDetails.getStartDate().getDayOfMonth()+1);
            Date preferredEndDate   = new Date(RuntimeDetails.getEndDate().getYear() - 1900, RuntimeDetails.getEndDate().getMonthValue() - 1, RuntimeDetails.getEndDate().getDayOfMonth()+1);
            String newBooking = (property.getId()) + "," + MainController.getCurrentUser().getUsername() + "," + preferredStartDate.toInstant() + ","  + preferredEndDate.toInstant() + '\n';
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
