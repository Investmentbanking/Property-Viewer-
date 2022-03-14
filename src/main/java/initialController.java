import javafx.application.Application;
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
    Button login;
    @FXML
    Button register;
    @FXML
    Button guest;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("pane0FXML.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();
    }
}