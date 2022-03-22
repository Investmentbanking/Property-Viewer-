import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

/**
 *Initial controller for application
 * provides functionality for selecting different scenes
 * allows you to press login, register and guest
 *
 * @author Burhan Tekcan K21013451
 * @version 1.0
 */
public class InitialController extends Application {

    @FXML // login button
    private Button login;
    @FXML // register button
    private Button register;
    @FXML // guest button
    private Button guest;

    // current scene being presented
    private static Scene scene;

    /**
     * sets the root scene to stage
     * with pane0.fxml
     * shows the scene
     * @param stage stage of application
     */
    @Override
    public void start(Stage stage) throws IOException {

    RuntimeDetails.setListings(AirbnbDataLoader.loadNewDataSet());  // Loads the dataset being used for this project.

//        URL url = getClass().getResource("pane4.fxml");
//        assert url != null;
//        Parent root = FXMLLoader.load(url);
//        scene = new Scene(root);
//        stage.setTitle("Scene Viewer - Select an Option");
//        stage.setScene(scene);
//        stage.show();

        // FOR TESTING MAP AND INSPECT MENU
        URL url = getClass().getResource("pane0.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        scene = new Scene(root);
        stage.setTitle("lol");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * directs user to login scene
     * @param event ActionEvent login button pressed
     */
    @FXML
    public void loginClicked(ActionEvent event) throws IOException {
        URL url = getClass().getResource("login.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    /**
     * directs user to register scene
     * @param event  ActionEvent register button pressed
     */
    @FXML
    public void registerClicked(ActionEvent event) throws IOException {
        URL url = getClass().getResource("signup.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    /**
     * directs user as guest to pane 1
     * @param event  ActionEvent guest button pressed
     */
    @FXML
    public void guestClicked(ActionEvent event) throws IOException {
        URL url = getClass().getResource("pane1.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    /**
     * directs user to entered fxml file
     * gets fxml url, sets root to loaded file
     */
    static void setRoot(String fxml) throws IOException {
        URL url = LoginController.class.getResource(fxml);
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }
}