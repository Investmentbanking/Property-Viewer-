import javafx.scene.control.Alert;

public class Alerts {

    public Alerts(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle("-fx-font-family: Serif");
        alert.showAndWait();
    }
}
