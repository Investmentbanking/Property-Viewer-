import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * The controller class
 */
public class StatisticsController extends Application {
   ComboBox combobox;

    @Override
    public void start(Stage stage) throws IOException {

        URL url = getClass().getResource("Statistics.fxml");
        assert url != null;
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();


        Statistics.createStats();

        getCatergoryStartStats(scene);

        combobox = (ComboBox) scene.lookup("#box");
        combobox.getItems().addAll("default", "reviews", "borough", "amenities and property interior");
        combobox.setPromptText("Please select what you want your stats to be tailored towards: ");
        //combobox.getSelectionModel().selectFirst();
        //combobox.setPromptText("Please select what you want your stats to be tailored towards: ");
        combobox.setOnAction((event) -> {
            if(isSelectedDefault()) {
                Statistics.createStats();

                getCatergoryStartStats(scene);
            }

            else if (isSelectedReviews()) {
                Statistics.createReviewStats();

                getCatergoryStartStats(scene);
            }
            else if (isSelectedBorough()){
                Statistics.createBoroughStats();

                getCatergoryStartStats(scene);
            }
            else {
                Statistics.createAmenitiesStats();

                getCatergoryStartStats(scene);
            }
        });
    }

    private boolean isSelectedDefault() {
        return combobox.getSelectionModel().getSelectedItem().equals("default");
    }

    private boolean isSelectedReviews() {
        return combobox.getSelectionModel().getSelectedItem().equals("reviews");
    }

    private boolean isSelectedBorough() {
        return combobox.getSelectionModel().getSelectedItem().equals("borough");
    }

    public void getCatergoryStartStats(Scene scene) {
        Text stat11 = (Text) scene.lookup("#stat1");
        Text stat22 = (Text) scene.lookup("#stat2");
        Text stat33 = (Text) scene.lookup("#stat3");
        Text stat44 = (Text) scene.lookup("#stat4");

        stat11.setText(Statistics.getNextStat());
        stat22.setText(Statistics.getNextStat());
        stat33.setText(Statistics.getNextStat());
        stat44.setText(Statistics.getNextStat());
    }
}