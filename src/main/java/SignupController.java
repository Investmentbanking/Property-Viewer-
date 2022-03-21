import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public boolean signup(ActionEvent event) {
        if(password.getText().equals(repeatedPassword.getText())){
            try {
                Login log = new Login(username.getText(), password.getText());
                if(log.checkLogin()){
                    System.out.println("login false");
                    return false;
                }else{
                    log.makeLogin();
                    System.out.println("login success");
                    return true;
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("not matching");
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
