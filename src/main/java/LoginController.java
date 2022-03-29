import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController {

    @FXML
    TextField username;             // the username field

    @FXML
    PasswordField password;         // the password field

    @FXML
    Label loadingLabel;             // the loading label

    /**
     * This method is responsible for handling input from the user and allowing them to log in.
     * In all cases an appropriate message will be shown to indicate if a certain operation failed/succeeded
     *
     * @param event login button clicked
     * @return true if the user is allowed to log in, false otherwise
     */
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

    /**
     * This method is responsible for displaying a "Loading please wait" message to the user.
     *
     * @param mouseEvent login button pressed and details are correct
     */
    @FXML
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

    /**
     * A simple method to change the current fxml file with another (sign up page)
     *
     * @param event signup button clicked
     * @throws IOException if the corresponding fxml file isn't found
     */
    @FXML
    public void signupPage(ActionEvent event) throws IOException {
        InitialController.setRoot("signup.fxml");
    }

    /**
     * A simple method to change the current fxml file with another (main page)
     *
     * @param event main page button clicked
     * @throws IOException if the corresponding fxml file isn't found
     */
    @FXML
    public void startPage(ActionEvent event) throws IOException {
        InitialController.setRoot("optionsPane.fxml");
    }
}
