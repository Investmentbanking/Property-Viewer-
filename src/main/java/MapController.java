import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML private Pane pane;

    @FXML private GridPane key;
    @FXML private Rectangle low_to_high;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        key.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

        Stop[] stops = new Stop[] {
                new Stop(0.0, MenuCircle.CHEAP_COLOR),
                new Stop(1.0, MenuCircle.EXPENSIVE_COLOR)
        };
        LinearGradient gradient = new LinearGradient(1, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        low_to_high.setFill(gradient);


        MapPane map = new MapPane(AirbnbDataLoader.loadNewDataSet());
        pane.getChildren().add(map);

//        map.minWidthProperty().bind(pane.widthProperty());
//        map.minHeightProperty().bind(pane.heightProperty());
    }
}
