import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public boolean checkDetails(ActionEvent event) {
        try {
            Login log = new Login(username.getText(), password.getText());
            if (log.checkLogin()){
                System.out.println("success");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("false");
        return false;
    }

    public void signupPage(ActionEvent event) throws IOException {
        InitialController.setRoot("signup.fxml");
    }

    @FXML
    public void startPage(ActionEvent event) throws IOException {
        InitialController.setRoot("pane0.fxml");
    }
}
