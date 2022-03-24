import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button submit;

    @FXML
    Label loadingLabel;

    @FXML
    public boolean checkDetails(ActionEvent event) {
        if(username.getText().equals("") || password.getText().equals("")){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Please enter all details");
            return false;
        }
        try {
            Login log = new Login(username.getText(), password.getText());
            if (log.checkLogin()){
                Pane1Controller.setCurrentUser(username.getText());
                InitialController.setRoot("pane1.fxml");
                return true;
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        new Alerts(Alert.AlertType.ERROR,"Error", null, "Login failed :( please ensure you have written your username and password correctly");
        return false;
    }

    @FXML
    public void signupPage(ActionEvent event) throws IOException {
        InitialController.setRoot("signup.fxml");
    }

    @FXML
    public void startPage(ActionEvent event) throws IOException {
        InitialController.setRoot("pane0.fxml");
    }

    public void change(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Login log = new Login(username.getText(), password.getText());
            if (log.checkLogin()){
                loadingLabel.setText("Loading... Please Wait");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
