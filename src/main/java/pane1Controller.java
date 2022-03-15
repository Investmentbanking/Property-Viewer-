import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class pane1Controller extends Application {

    @FXML
    private ChoiceBox minimumPrice;
    @FXML
    private ChoiceBox maximumPrice;

    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    private LocalDate sDate;
    private LocalDate eDate;
    private long totalNights;

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
        if(sDate != null && eDate != null && !sDate.isBefore(eDate))
        {
            Alerts alert = new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The start date cannot be after or equal to the end date");
            startDate.setValue(null);
        }
        if(sDate != null && (sDate.isBefore(LocalDate.now()) || sDate.isEqual(LocalDate.now())))
        {
            Alerts alert = new Alerts(Alert.AlertType.ERROR, "Time Error", null, "The start date cannot be before the current date");
            startDate.setValue(null);
        }
        validateDates();
    }

    @FXML
    public void selectEndDate(ActionEvent event)
    {
        System.out.println("end selected");
        eDate = endDate.getValue();
        if(eDate != null && sDate != null && !sDate.isBefore(eDate))
        {
            Alerts alert = new Alerts(Alert.AlertType.ERROR,"Date Error", null, "The start date cannot be before or equal to the start date");
            endDate.setValue(null);
        }
        validateDates();
    }

    @FXML
    public void leftArrowClicked(ActionEvent event)
    {
        System.out.println("left pressed");
    }

    @FXML
    public void rightArrowClicked(ActionEvent event)
    {
        System.out.println("right pressed");
    }

    @FXML
    public void minimumChosen(ActionEvent event)
    {
        System.out.println("min price chosen");
    }

    @FXML
    public void maxChosen(ActionEvent event)
    {
        System.out.println("max price chosen");
    }

    private void validateDates()
    {
        if(sDate != null && eDate != null)
        {
            totalNights = sDate.until(eDate, ChronoUnit.DAYS);
            System.out.println(totalNights);
        }
    }
}
