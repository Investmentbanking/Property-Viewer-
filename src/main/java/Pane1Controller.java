import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Main controller for pane 1
 * allows for selection of prices, dates
 * welcomes and shows instructions on how to use program
 * controls top bar and arrows
 * handles pane switching
 *
 * @author Burhan Tekcan K21013451
 * @version 1.0
 */
public class Pane1Controller extends Application {

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
    @FXML // the root pane
    private BorderPane mainPane;

    // the first pane; stored to then show again if user wants to go back
    private Node pane1;
    // all available panes for the main scene
    private final ArrayList<Node> sceneNodes = new ArrayList<>();
    // pointer to the current pane
    private int pointer;
    // the prices available to choose from
    private final ObservableList<Integer> availablePrices = FXCollections.observableArrayList(0,100,200,300,400,500,600,700,800,900,1000);

    // SERGES STUFF DONT TOUCH

    private static Account currentUser = new Account();

    public static Account getCurrentUser() {
        return currentUser;

    }

    public static void setCurrentUser(String username){
        currentUser.setUsername(username);
    }

    // SERGES STUFF END

    /**
     * Initialise the settings for the scene
     * sets the available prices and initial conditions for pane 1
     */
    @FXML
    private void initialize()
    {
        minimumPriceBox.setItems(availablePrices);
        maximumPriceBox.setItems(availablePrices);
        minimumPriceBox.setValue(0);
        maximumPriceBox.setValue(1000);
        priceRange.textProperty().set(RuntimeDetails.getPriceRange());
        startDate.setValue(RuntimeDetails.getStartDate());
        endDate.setValue(RuntimeDetails.getEndDate());
        checkValidDetails();
    }

    /**
     * Sets pointer to 0 (which is pane 1)
     * adds all panes to arraylist to be accessed
     */
    public Pane1Controller() throws IOException
    {
        Node pane2 = FXMLLoader.load(getClass().getResource("login.fxml"));
        Node pane3 = FXMLLoader.load(getClass().getResource("signup.fxml"));
        sceneNodes.add(null);
        sceneNodes.add(pane2);
        sceneNodes.add(pane3);
        pointer = 0;
    }

    /**
     * creates scene with pane 1
     * adds the scene to the stage
     * @param stage for the scene to be placed in
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        URL url = getClass().getResource("pane1.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("Scene Viewer 1.0");
        stage.setScene(scene);
        stage.show();
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
            new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The start date cannot be before the current date");
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
            pane1 = centrePane.getCenter();
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
        if(pointer == 0)
        {
            centrePane.getChildren().remove(centrePane.getCenter());
            centrePane.setCenter(pane1);
        }
        else {
            centrePane.getChildren().remove(centrePane.getCenter());
            centrePane.setCenter(sceneNodes.get(pointer));
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
        if(pointer == 0)
        {
            centrePane.getChildren().remove(centrePane.getCenter());
            centrePane.setCenter(pane1);
        }
        else {
            centrePane.getChildren().remove(centrePane.getCenter());
            centrePane.setCenter(sceneNodes.get(pointer));
        }
        setPaneLabelText();
    }

    /**
     * Confirms the entered prices
     * if the prices are valid,
     * flag set true, prices set in RuntimeDetails
     * selected prices are outputted
     * if the prices are invalid,
     * appropriate error created, values set null and flag set false
     * @param event ActionEvent of confirm prices button being pressed
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
            priceRange.textProperty().set(minPriceInput + " to " + maxPriceInput);
            RuntimeDetails.setValidPrices(true);
            checkValidDetails();
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
}
