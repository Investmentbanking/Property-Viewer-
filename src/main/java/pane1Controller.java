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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class pane1Controller extends Application {

    @FXML
    private ChoiceBox<Integer> minimumPriceBox;
    @FXML
    private ChoiceBox<Integer> maximumPriceBox;
    @FXML
    private Button confirmPrice;

    ObservableList<Integer> availablePrices = FXCollections.observableArrayList(0,100,200,300,400,500,600,700,800,900,1000);
    private int minimumPrice;
    private int maximumPrice;

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

    private LocalDate sDate;
    private LocalDate eDate;
    private long totalNights;
    private boolean validPrices;
    private boolean validDates;

    @FXML
    private AnchorPane centrePane;
    @FXML
    private BorderPane mainPane;

    @FXML
    private void initialize() {
        minimumPriceBox.setItems(availablePrices);
        maximumPriceBox.setItems(availablePrices);
        minimumPriceBox.setValue(0);
        maximumPriceBox.setValue(1000);
        checkValidDetails();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("pane1.fxml");
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
        sDate = startDate.getValue();
        if(sDate != null && eDate != null && !sDate.isBefore(eDate)) {
            new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The start date cannot be after or equal to the end date");
            startDate.setValue(null);
            validDates = false;
            checkValidDetails();
        }
        else if(sDate != null && (sDate.isBefore(LocalDate.now()) || sDate.isEqual(LocalDate.now()))) {
            new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The start date cannot be before the current date");
            startDate.setValue(null);
            validDates = false;
            checkValidDetails();
        }
        else {
            setTotalNights();
        }
    }

    @FXML
    public void selectEndDate(ActionEvent event) {
        System.out.println("end selected");
        eDate = endDate.getValue();
        if(eDate != null && sDate != null && !sDate.isBefore(eDate)) {
            new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The start date cannot be before or equal to the start date");
            endDate.setValue(null);
            validDates = false;
            checkValidDetails();
        }
        else if(eDate != null && (eDate.isBefore(LocalDate.now()) || eDate.isEqual(LocalDate.now()))) {
            new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The end date cannot be before the current date");
            endDate.setValue(null);
            validDates = false;
            checkValidDetails();
        }
        setTotalNights();
    }

    @FXML
    public void leftArrowClicked(ActionEvent event) throws IOException {
        System.out.println("left pressed");
        Parent pane1 = FXMLLoader.load(getClass().getResource("pane0.fxml"));
        mainPane.getChildren().remove(centrePane);
        mainPane.getChildren().add(pane1);

    }

    @FXML
    public void rightArrowClicked(ActionEvent event)
    {
        System.out.println("right pressed");
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
            validPrices = false;
            checkValidDetails();
        }
        else if(minPriceInput >= maxPriceInput) {
            new Alerts(Alert.AlertType.ERROR, "Invalid range", null, "The price range selected is invalid");
            minimumPriceBox.setValue(null);
            maximumPriceBox.setValue(null);
            validPrices = false;
            priceRange.textProperty().set("None Selected");
            checkValidDetails();
        }
        else {
            minimumPrice = minPriceInput;
            maximumPrice = maxPriceInput;
            priceRange.textProperty().set(minimumPrice + " to " + maximumPrice);
            validPrices = true;
            checkValidDetails();
        }
    }

    private void setTotalNights()
    {
        if(sDate != null && eDate != null)
        {
            totalNights = sDate.until(eDate, ChronoUnit.DAYS);
            System.out.println(totalNights);
            validDates = true;
            checkValidDetails();
        }
    }

    private void checkValidDetails()
    {
        if(validPrices && validDates)
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
}
