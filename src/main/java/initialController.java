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

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("pane0.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loginClicked(ActionEvent event)
    {
        System.out.println("login");
    }

    @FXML
    public void registerClicked(ActionEvent event)
    {
        System.out.println("register");
    }

    @FXML
    public void guestClicked(ActionEvent event)
    {
        System.out.println("guest");
    }
}