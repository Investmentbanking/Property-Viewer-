import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * This class is responsible for login in. It acts as the main controller behind the login.fxml file.
 * It handles all the input by the user + any button clicked
 *
 * @author Syraj Alkhalil k21007329
 * @version 1.0
 */
public class LoginController {

    @FXML
    TextField username;             // The username field

    @FXML
    PasswordField password;         // The password field

    @FXML
    Label loadingLabel;             // The loading label

    @FXML
    Button submit;                  // The submit button

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
        displayingText();
    }

    private void displayingText(){
        try {
            Login log = new Login(username.getText(), password.getText());
            System.out.println(username.getText());
            System.out.println(password.getText());
            if (log.checkLogin()){
                loadingLabel.setText("Loading... Please Wait");
                loadingLabel.setVisible(true);
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

    /**
     * This method is responsible for activating the submit button if the enter key is pressed
     *
     * @param keyEvent the key pressed
     */
    @FXML
    public void enter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            displayingText();
            submit.fire();
        }
    }
}
