import javafx.scene.control.Alert;

/**
 * A quick one line way to make an alert
 *
 * @author Burhan Tekcan K21013451
 * @version 1.0
 */
public class Alerts {

    /**
     * Creates an alert to show and wait
     * @param alertType The type of alert
     * @param title Title of the alert
     * @param headerText Capital Header of alert
     * @param contentText Text of an alert
     */
    public Alerts(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle("-fx-font-family: Serif");
        alert.showAndWait();
    }
}
