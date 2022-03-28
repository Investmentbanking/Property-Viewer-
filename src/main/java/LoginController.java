import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
                MainController.setCurrentUser(username.getText());
                InitialController.setRoot("welcomePane.fxml");
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
        InitialController.setRoot("optionsPane.fxml");
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
