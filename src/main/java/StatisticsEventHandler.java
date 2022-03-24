import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * This class handles button presses in the statistics panel.
 *
 * @author Ayesha Dorani
 */
public class StatisticsEventHandler implements EventHandler<Event> {

    /**
     *
     * @param event
     */
    @Override
    public void handle(Event event) {
        Node node = (Node) event.getSource();
        Statistics.changeStats(node);
        //Statistics.changeReviewStats(node);
        //Statistics.changeBoroughStats(node);
        //Statistics.changeAmenitiesStats(node);
    }
}
