import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main controller for welcome pane
 * allows for selection of prices, dates
 * welcomes and shows instructions on how to use program
 * controls top bar and arrows
 * handles pane switching
 *
 * @author Burhan Tekcan K21013451
 * @version 1.5
 */
public class MainController {

    @FXML // minimum price selection
    private ChoiceBox<Integer> minimumPriceBox;
    @FXML // maximum price selection
    private ChoiceBox<Integer> maximumPriceBox;
    @FXML // confirmation box
    private Button confirmPrice;
    @FXML // button to go back one pane
    private Button leftArrow;
    @FXML // button to go forward one pane
    private Button rightArrow;
    @FXML // pane number label at bottom
    private Label paneLabel;

    @FXML // start date for properties to be shown -> booking...
    private DatePicker startDate;
    @FXML // end date for properties to be shown -> booking
    private DatePicker endDate;
    @FXML // output of currently selected price range
    private Label priceRange;
    @FXML // the central pane where content is displayed
    private BorderPane centrePane;

    // the first pane; stored to show again if user goes back to pane 1
    private static Node pane1;
    // all available panes for the main scene
    private final ArrayList<Node> sceneNodes = new ArrayList<>();
    // pointer to the current pane
    private int pointer;
    // the prices available to choose from
    public static final ObservableList<Integer> AVAILABLE_PRICES = FXCollections.observableArrayList(0,50,100,150,200,300,400,500,600,700,800,900,1000,1200,1400,1600,1800,2000,3000,4000,5000,10000);
    // object of the current users account
    private static Account currentUser;
    // Statistics combobox
    ComboBox combobox;

    private final MapController mapController; // Controller for map pane.


    static {
        try {
            currentUser = new Account();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise the settings for the scene
     * sets the available prices and initial conditions for the welcome pane
     */
    @FXML
    private void initialize()
    {
        minimumPriceBox.setItems(AVAILABLE_PRICES);
        maximumPriceBox.setItems(AVAILABLE_PRICES);
        minimumPriceBox.setValue(0);
        maximumPriceBox.setValue(10000);
        priceRange.textProperty().set(RuntimeDetails.getPriceRange());
        startDate.setValue(RuntimeDetails.getStartDate());
        endDate.setValue(RuntimeDetails.getEndDate());
        checkValidDetails();
    }

    /**
     * Sets pointer to 0 (which is pane 1)
     * adds all panes to arraylist to be accessed when changing pane
     */
    public MainController() throws IOException
    {
        FXMLLoader boroughMapLoader = new FXMLLoader();
        Node boroughMap = null;
        try {
            boroughMap = boroughMapLoader.load((Objects.requireNonNull(getClass().getResource("map.fxml"))).openStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        mapController = boroughMapLoader.getController();

        Node pane4 = FXMLLoader.load(getClass().getResource("bookingPane.fxml"));
        sceneNodes.add(null); // it is already created and in scene.
        sceneNodes.add(boroughMap);
        sceneNodes.add(null); // uses separate method to create scene
        sceneNodes.add(pane4);
        pointer = 0;
    }

    /**
     * takes in start date, checks if valid
     * if not it produces an appropriate error
     * if both dates are valid, flag is set to true
     * total nights are set
     * @param event ActionEvent selection of start date
     */
    @FXML
    public void selectStartDate(ActionEvent event)
    {
        RuntimeDetails.setStartDate(startDate.getValue());
        LocalDate sDate = RuntimeDetails.getStartDate();
        LocalDate eDate = RuntimeDetails.getEndDate();
        if(sDate != null && eDate != null && !sDate.isBefore(eDate)) {
            new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The start date cannot be after or equal to the end date");
            startDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(sDate != null && (sDate.isBefore(LocalDate.now()) || sDate.isEqual(LocalDate.now()))) {
            new Alerts(Alert.AlertType.ERROR, "Date Error", null, "The start date cannot be before the current date");
            startDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(eDate != null && sDate != null) {
            RuntimeDetails.setValidDates(true);
            setTotalNights();
        }
    }

    /**
     * takes in end date, checks if valid
     * if not it produces an appropriate error
     * if both dates are valid, flag is set to true
     * total nights are set
     * @param event ActionEvent selection of end date
     */
    @FXML
    public void selectEndDate(ActionEvent event)
    {
        RuntimeDetails.setEndDate(endDate.getValue());
        LocalDate sDate = RuntimeDetails.getStartDate();
        LocalDate eDate = RuntimeDetails.getEndDate();
        if(eDate != null && sDate != null && !sDate.isBefore(eDate))
        {
            new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The end date cannot be before or equal to the start date");
            endDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(eDate != null && (eDate.isBefore(LocalDate.now()) || eDate.isEqual(LocalDate.now())))
        {
            new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The end date cannot be before the current date");
            endDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(eDate != null && sDate != null) {
            RuntimeDetails.setValidDates(true);
            setTotalNights();
            if(pointer == 0) {
                pane1 = centrePane.getCenter();
            }
        }
    }

    /**
     * Goes back one pane
     * decrements pointer, removes current centre
     * adds pane from arraylist sceneNodes with index pointer
     * @param event ActionEvent of left arrow being clicked
     */
    @FXML
    public void leftArrowClicked(ActionEvent event) throws IOException
    {
        pointerDecrement();
        switch(pointer) {
            case 0:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(pane1);
                togglePriceBoxes(false);
                break;
            case 1:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(sceneNodes.get(pointer));
                togglePriceBoxes(false);
                break;
            case 2:

                centrePane.getChildren().remove(centrePane.getCenter());
                changeToStats();
                togglePriceBoxes(true);
                break;

            default:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(sceneNodes.get(pointer));
                togglePriceBoxes(true);
        }
        setPaneLabelText();
    }

    /**
     * Goes forward one pane
     * increments pointer, removes centre
     * adds pane from arraylist sceneNodes with index pointer
     * @param event ActionEvent of right arrow being clicked
     */
    @FXML
    public void rightArrowClicked(ActionEvent event) throws IOException {
        pointerIncrement();
        switch(pointer) {
            case 0:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(pane1);
                togglePriceBoxes(false);
                break;
            case 1:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(sceneNodes.get(pointer));
                togglePriceBoxes(false);
                break;
            case 2:
                centrePane.getChildren().remove(centrePane.getCenter());
                changeToStats();
                togglePriceBoxes(true);
                break;

            default:
                centrePane.getChildren().remove(centrePane.getCenter());
                centrePane.setCenter(sceneNodes.get(pointer));
                togglePriceBoxes(true);
        }
        setPaneLabelText();
    }

    /**
     * Confirms the entered prices; if the prices are valid:
     * the flag is set true, prices are set in RuntimeDetails, and the selected prices are outputted.
     * If the prices are invalid: appropriate errors are created, values are set to null and flag is set false.
     * @param event ActionEvent of confirm prices button being pressed.
     */
    @FXML
    public void confirmPrices(ActionEvent event)
    {
        Integer minPriceInput = minimumPriceBox.getSelectionModel().getSelectedItem();
        Integer maxPriceInput = maximumPriceBox.getSelectionModel().getSelectedItem();
        if(minPriceInput == null || maxPriceInput == null) {
            new Alerts(Alert.AlertType.ERROR, "Price range not selected", null, "The price range is not selected");
            priceRange.textProperty().set("None Selected");
            RuntimeDetails.setValidPrices(false);
            checkValidDetails();
        }
        else if(minPriceInput >= maxPriceInput) {
            new Alerts(Alert.AlertType.ERROR, "Invalid range", null, "The price range selected is invalid");
            minimumPriceBox.setValue(null);
            maximumPriceBox.setValue(null);
            RuntimeDetails.setValidPrices(false);
            priceRange.textProperty().set("None Selected");
            checkValidDetails();
        }
        else {
            RuntimeDetails.setMinimumPrice(minPriceInput);
            RuntimeDetails.setMaximumPrice(maxPriceInput);
            priceRange.textProperty().set(RuntimeDetails.getPriceRange());
            RuntimeDetails.setValidPrices(true);
            Statistics.reloadListings();
            checkValidDetails();

            mapController.rangeChanged(); // Reloads the structure of the map.
        }
    }

    /**
     * Sets the label for the pane number
     * used when the pane is changed
     */
    private void setPaneLabelText()
    {
        int currentPane = pointer + 1;
        paneLabel.setText("Pane " + currentPane);
    }

    /**
     * Sets the total nights, if the dates are valid
     */
    private void setTotalNights()
    {
        if(RuntimeDetails.isValidDates())
        {
            RuntimeDetails.setTotalNights();
            checkValidDetails();
        }
    }

    /**
     * checks if all details are valid
     * arrows are enabled if details are valid
     */
    private void checkValidDetails()
    {
        if(RuntimeDetails.isValidDetails())
        {
            leftArrow.setDisable(false);
            rightArrow.setDisable(false);
        }
        else
        {
            leftArrow.setDisable(true);
            rightArrow.setDisable(true);
        }
    }

    /**
     * increments the pointer up to size of sceneNodes
     * sets to pointer to 0 if it reaches arraylist size
     */
    private void pointerIncrement()
    {
        pointer++;
        if(pointer >= sceneNodes.size())
        {
            pointer = 0;
        }
    }

    /**
     * decrements the pointer
     * sets to pointer to arraylist size - 1 if it reaches 0
     */
    private void pointerDecrement()
    {
        pointer--;
        if(pointer < 0)
        {
            pointer = sceneNodes.size() - 1;
        }
    }

    /**
     * Gets the fxml file, adding the pane to the center of the scene,
     * setting the initial values for the statistics and prompts
     * and defining the choice-box actions.
     */
    private void changeToStats() throws IOException {
        URL url = getClass().getResource("Statistics.fxml");
        assert url != null;
        Node root = FXMLLoader.load(url);
        centrePane.setCenter(root);
        Scene scene = centrePane.getScene();
        Statistics.createStats();
        getCatergoryStartStats(scene);
        combobox = (ComboBox) scene.lookup("#box");
        combobox.getItems().addAll("default", "reviews", "borough and listings", "amenities and property interior");
        combobox.setPromptText("Please select what you want your stats to be tailored towards: ");
        combobox.setOnAction((event) -> {
            if(isSelectedDefault()) {
                Statistics.createStats();

                getCatergoryStartStats(scene);
            }
            else if (isSelectedReviews()) {
                Statistics.createReviewStats();

                getCatergoryStartStats(scene);
            }
            else if (isSelectedBorough()){
                Statistics.createBoroughAndListingsStats();

                getCatergoryStartStats(scene);
            }
            else {
                Statistics.createAmenitiesStats();

                getCatergoryStartStats(scene);
            }
        });
    }

    /**
     * Gets the selected value from the Statistics selection box,
     * returns true if the value is equal to 'default'
     * @return true if 'default' is selected in Statistics combobox
     */
    private boolean isSelectedDefault() {
        return combobox.getSelectionModel().getSelectedItem().equals("default");
    }


    /**
     * Gets the selected value from the Statistics selection box,
     * returns true if the value is equal to 'reviews'
     * @return true if 'reviews' 'is selected in Statistics combobox
     */
    private boolean isSelectedReviews() {
        return combobox.getSelectionModel().getSelectedItem().equals("reviews");
    }

    /**
     * Gets the selected value from the Statistics selection box,
     * returns true if the value is equal to 'borough and listings'
     * @return true if 'borough and listings' is selected in Statistics combobox
     */
    private boolean isSelectedBorough() {
        return combobox.getSelectionModel().getSelectedItem().equals("borough and listings");
    }

    /**
     * Assigns the text areas to variables from the scene
     * setting the contents to Statistics values
     * @param scene the Statistics panel scene
     */
    public void getCatergoryStartStats(Scene scene) {
        Text stat11 = (Text) scene.lookup("#stat1");
        Text stat22 = (Text) scene.lookup("#stat2");
        Text stat33 = (Text) scene.lookup("#stat3");
        Text stat44 = (Text) scene.lookup("#stat4");

        stat11.setText(Statistics.getNextStat());
        stat22.setText(Statistics.getNextStat());
        stat33.setText(Statistics.getNextStat());
        stat44.setText(Statistics.getNextStat());
    }

    /**
     * Simple getter method to return the current users account as object
     * @return the users account
     */
    public static Account getCurrentUser() {
        return currentUser;
    }

    /**
     * A setter method to set the current users Username as the argument string
     * @param username the current users username
     */
    public static void setCurrentUser(String username){
        currentUser.setUsername(username);
    }

    /**
     * Enables or Disables price boxes and confirm button depending on boolean input
     * @param toggle true to disable price boxes and confirm button
     */
    private void togglePriceBoxes(boolean toggle)
    {
        minimumPriceBox.setDisable(toggle);
        maximumPriceBox.setDisable(toggle);
        confirmPrice.setDisable(toggle);
    }
}
