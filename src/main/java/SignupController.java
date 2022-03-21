import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URISyntaxException;

public class SignupController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    PasswordField repeatedPassword;

    @FXML
    public boolean signup(ActionEvent event) throws IOException {
        if(username.getText().equals("") || password.getText().equals("") || repeatedPassword.getText().equals("")){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Please enter all details");
            return false;
        }
        if(password.getText().equals(repeatedPassword.getText())){
            try {
                Login log = new Login(username.getText(), password.getText());
                if(log.checkLogin()){
                    new Alerts(Alert.AlertType.ERROR,"Error", null, "sorry this username already exists! please pick another one.");
                    return false;
                }else{
                    log.makeLogin();
                    new Alerts(Alert.AlertType.INFORMATION,"Success", null, "Signup success! please login!");
                    InitialController.setRoot("login.fxml");
                    return true;
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else {
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Both the password and the repeated password have to match!");
            return false;
        }
        return false;
    }

    @FXML
    public void loginPage(ActionEvent event) throws IOException {
        InitialController.setRoot("login.fxml");
    }

    @FXML
    public void startPage(ActionEvent event) throws IOException {
        InitialController.setRoot("pane0.fxml");
    }
}
