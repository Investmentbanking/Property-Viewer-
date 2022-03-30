import javafx.scene.control.Alert;

/**
 * A class that creates an alert in one line
 * made so that blocks of code are not repeated throughout
 *
 * @author Burhan Tekcan K21013451
 * @version 1.0
 */
public class Alerts {

    /**
     * Creates an alert to show to the user.
     *
     * @param alertType the type of alert.
     * @param title title of the alert.
     * @param headerText capital Header of alert.
     * @param contentText text of an alert.
     */
    public Alerts(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle("-fx-font-family: Arial");
        alert.showAndWait();
    }
}
