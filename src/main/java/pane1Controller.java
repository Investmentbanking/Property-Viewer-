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
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date Error");
            alert.setHeaderText(null);
            alert.setContentText("The start date cannot be after or equal to the end date");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date Error");
            alert.setHeaderText(null);
            alert.setContentText("The end date cannot be before or equal to the start date");
            alert.showAndWait();
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
            Duration duration = Duration.between(sDate, eDate);
            totalNights = duration.toDays();
            System.out.println(totalNights);
        }
    }
}
