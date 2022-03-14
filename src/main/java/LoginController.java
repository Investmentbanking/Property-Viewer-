import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginController extends Application {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button submit;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("login.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public boolean checkDetails(ActionEvent event) {
        try {
            Login log = new Login(username.getText(), password.getText());
            if(log.checkLogin()){
                System.out.println("true");
            }else {
                System.out.println("false");
            }
            return log.checkLogin();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("false");
        return false;
    }
}
