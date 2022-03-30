import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * This class is responsible for the signing up. It acts as the main controller behind the singup.fxml file.
 * It handles all the input by the user + any button clicked
 *
 * @author Syraj Alkhalil k21007329
 * @version 1.0
 */
public class SignupController {

    @FXML
    TextField username;                                   // The username field
    @FXML
    PasswordField password, repeatedPassword;             // The password and the repeated password fields
    @FXML
    Button submit;                                        // Submit button

    /**
     * This method is responsible for signing up a user.
     * If for any reason the user can't sign up a message will be displayed with the reason.
     * If there was a successful signup the user will be directed to the login page to login and a success message will show
     *
     * @param event the signup button is clicked
     * @return true if the signup action was complete, false otherwise
     */
    @FXML
    public boolean signup(ActionEvent event) {
        if(username.getText().equals("") || password.getText().equals("") || repeatedPassword.getText().equals("")){
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Please enter all details");
            return false;
        }
        if(password.getText().equals(repeatedPassword.getText())){
            try {
                Login log = new Login(username.getText(), password.getText());
                if(log.checkLoginNewUser()){
                    new Alerts(Alert.AlertType.ERROR,"Error", null, "sorry this username already exists! please pick another one.");
                    return false;
                }else{
                    log.makeLogin();
                    new Alerts(Alert.AlertType.INFORMATION,"Success", null, "Signup success! please login!");
                    InitialController.setRoot("login.fxml");
                    return true;
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }else {
            new Alerts(Alert.AlertType.ERROR,"Error", null, "Both the password and the repeated password have to match!");
            return false;
        }
        return false;
    }

    /**
     * A simple method to change the current fxml file with another (login page)
     *
     * @param event main page button clicked
     * @throws IOException if the corresponding fxml file isn't found
     */
    @FXML
    public void loginPage(ActionEvent event) throws IOException {
        InitialController.setRoot("login.fxml");
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
            submit.fire();
        }
    }
}
