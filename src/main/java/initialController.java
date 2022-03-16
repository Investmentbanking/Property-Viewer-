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

public class initialController extends Application {

    @FXML
    private Button login;
    @FXML
    private Button register;
    @FXML
    private Button guest;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("pane0.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loginClicked(ActionEvent event) throws IOException {
        System.out.println("login");
        URL url = getClass().getResource("login.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    @FXML
    public void registerClicked(ActionEvent event) throws IOException {
        System.out.println("register");
        URL url = getClass().getResource("signup.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    @FXML
    public void guestClicked(ActionEvent event) throws IOException {
        System.out.println("guest");
        URL url = getClass().getResource("pane1.fxml");
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }

    static void setRoot(String fxml) throws IOException {
        URL url = LoginController.class.getResource(fxml);
        assert url != null;
        scene.setRoot(FXMLLoader.load(url));
    }
}