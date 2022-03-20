import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jnr.constants.platform.Local;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class pane1Controller extends Application {

    @FXML
    private ChoiceBox<Integer> minimumPriceBox;
    @FXML
    private ChoiceBox<Integer> maximumPriceBox;
    @FXML
    private Button confirmPrice;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label priceRange;
    @FXML
    private BorderPane centrePane;
    @FXML
    private BorderPane mainPane;

    private ArrayList<String> fxmlFiles = new ArrayList<>();
    private int pointer;
    private final ObservableList<Integer> availablePrices = FXCollections.observableArrayList(0,100,200,300,400,500,600,700,800,900,1000);

    @FXML
    private void initialize() {
        minimumPriceBox.setItems(availablePrices);
        maximumPriceBox.setItems(availablePrices);
        minimumPriceBox.setValue(0);
        maximumPriceBox.setValue(1000);
        priceRange.textProperty().set(RuntimeDetails.getPriceRange());
        startDate.setValue(RuntimeDetails.getStartDate());
        endDate.setValue(RuntimeDetails.getEndDate());
        checkValidDetails();
    }

    public pane1Controller()
    {
        fxmlFiles.add("pane1.fxml");
        fxmlFiles.add("login.fxml");
        fxmlFiles.add("signup.fxml");
        pointer = 0;
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("general.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void selectStartDate(ActionEvent event)
    {
        System.out.println("start selected");
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

    @FXML
    public void selectEndDate(ActionEvent event) {
        System.out.println("end selected");
        RuntimeDetails.setEndDate(endDate.getValue());
        LocalDate sDate = RuntimeDetails.getStartDate();
        LocalDate eDate = RuntimeDetails.getEndDate();
        if(eDate != null && sDate != null && !sDate.isBefore(eDate)) {
            new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The end date cannot be before or equal to the start date");
            endDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(eDate != null && (eDate.isBefore(LocalDate.now()) || eDate.isEqual(LocalDate.now()))) {
            new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The end date cannot be before the current date");
            endDate.setValue(null);
            RuntimeDetails.setValidDates(false);
            checkValidDetails();
        }
        else if(eDate != null && sDate != null) {
            RuntimeDetails.setValidDates(true);
            setTotalNights();
        }
    }

    @FXML
    public void leftArrowClicked(ActionEvent event) throws IOException {
        System.out.println("left pressed");
        pointerDecrement();
        Parent pane1 = FXMLLoader.load(getClass().getResource(fxmlFiles.get(pointer)));
        mainPane.getChildren().remove(centrePane);
        mainPane.getChildren().add(pane1);

    }

    @FXML
    public void rightArrowClicked(ActionEvent event) throws IOException {
        System.out.println("right pressed");
        pointerIncrement();
        Parent pane = FXMLLoader.load(getClass().getResource(fxmlFiles.get(pointer)));
        mainPane.getChildren().remove(centrePane);
        mainPane.getChildren().add(pane);
    }

    @FXML
    public void confirmPrices(ActionEvent event)
    {
        System.out.println("Price confirmed");
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

    private void setTotalNights()
    {
        if(RuntimeDetails.isValidDates())
        {
            RuntimeDetails.setTotalNights();
            checkValidDetails();
        }
    }

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

    private void pointerIncrement()
    {
        pointer++;
        System.out.println(pointer);
        if(pointer >= fxmlFiles.size())
        {
            pointer = 0;
        }
    }

    private void pointerDecrement()
    {
        pointer--;
        System.out.println(pointer);
        if(pointer < 0)
        {
            pointer = fxmlFiles.size() - 1;
        }
    }
}
