import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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


    @FXML // text label telling the user to wait
    private Label loadingLabel;
    // current scene being presented
    private static Scene scene;

    /**
     * Sets the visibility of the loading label to false
     * before the scene is presented to the user
     */
    @FXML
    private void initialize()
    {
        loadingLabel.setVisible(false);
    }
    /**
     * sets the root scene to stage
     * with optionsPane.fxml and shows the stage
     * @param stage stage of application
     */
    @Override
    public void start(Stage stage) throws IOException {
        RuntimeDetails.setNewAirbnbListings(AirbnbDataLoader.loadNewDataSet());     // Loads the new dataset being used for this project.
        RuntimeDetails.setOldAirbnbListings(AirbnbDataLoader.loadOldDataSet());     // Loads the old dataset being used for this project.
        URL url = getClass().getResource("optionsPane.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        scene = new Scene(root);
        stage.setTitle("Scene Viewer 1.0");
        stage.setMinHeight(520);
        stage.setMinWidth(720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * directs user to login scene
     * @param event ActionEvent login button pressed
     */
    @FXML
    public void loginClicked(ActionEvent event) throws IOException {
        setRoot("login.fxml");
    }

    /**
     * directs user to register scene
     * @param event  ActionEvent register button pressed
     */
    @FXML
    public void registerClicked(ActionEvent event) throws IOException {
        setRoot("signup.fxml");
    }

    /**
     * directs user as guest to pane 1
     * @param event  ActionEvent guest button pressed
     */
    @FXML
    public void guestClicked(ActionEvent event) throws IOException {
        setRoot("welcomePane.fxml");
    }

    /**
     * Sets the loading label to be visible on the pane,
     * allowing the user to read it
     * @param event on click of a button
     */
    @FXML
    public void loadingLabelVisible(MouseEvent event)
    {
        loadingLabel.setVisible(true);
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