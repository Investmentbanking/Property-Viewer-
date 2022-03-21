import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jnr.ffi.annotations.In;

import java.util.ArrayList;
import java.util.HashMap;

public class InspectListingMenu extends ScrollPane {

    final int SPACING = 10;
    final int ROW_MAX = 6;

    ArrayList<HBox> rows;
    HashMap<HBox, Boolean> imageShown;
    VBox root;



    private DoubleProperty size = new SimpleDoubleProperty();

    public InspectListingMenu(ArrayList<NewAirbnbListing> listings) {
        rows = new ArrayList<>();
        imageShown = new HashMap<>();

        root = new VBox();   // Causes way less lag than GridPane
        root.setPadding(new Insets(SPACING, 0, SPACING,0));
        root.setSpacing(SPACING);
        HBox row = null;

        int count = 0;
        for (NewAirbnbListing listing: listings){
            if (count == ROW_MAX || row == null){
                row = new HBox();
                row.setOnMouseEntered(this::showRowImages);
                imageShown.put(row, false);
                rows.add(row);
                row.setSpacing(SPACING);
                root.getChildren().add(row);
                count = 0;
            }

            ListingBox box = new ListingBox(listing);
            box.minWidthProperty().bind(size);

            box.setOnMouseClicked(this::openInspectMenu);
            row.getChildren().add(box);
            HBox.setHgrow(box, Priority.ALWAYS);
            count++;
        }

        setPadding(new Insets(0, SPACING, 0, SPACING));
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setContent(root);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        double padding = 4;
        viewportBoundsProperty().addListener((obs, old, bounds) -> {
            size.setValue((bounds.getWidth() - padding - ((ROW_MAX - 1) * SPACING)) / ROW_MAX);
        });

        //setOnScroll(this::showImages);

//        vvalueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("scroll time");
//            }
//        });






        //ADD BACK LATER MAYBE

        Platform.runLater(this::showOnScreenImages);

    }


    public void showRowImages(MouseEvent event){
        HBox hBox = ((HBox)(event.getSource()));
        if (!imageShown.get(hBox)) {
            imageShown.replace(hBox, true);
            for (Node node : hBox.getChildrenUnmodifiable()) {
                ((ListingBox) node).showImage();
            }
        }
    }

    public void showOnScreenImages(){
        Bounds paneBounds = localToScene(getBoundsInParent());
        for (HBox hBox : rows){
            Bounds nodeBounds = hBox.localToScene(hBox.getBoundsInLocal());
            if(paneBounds.intersects(nodeBounds)){
                imageShown.replace(hBox, true);
                for (Node node : hBox.getChildrenUnmodifiable()){
                    //System.out.println("yes");
                    ((ListingBox)node).showImage();
                }
            }
//            else {
//                for (Node node : hBox.getChildrenUnmodifiable()){
//                    //System.out.println("no");
//                    ((ListingBox)node).removeImage();
//                }
//            }

//            if (hBox.getLayoutY() < 0){
//                for (Node node : hBox.getChildrenUnmodifiable()){
//                    ((ListingBox)node).removeImage();
//                }
//            }
//            else if (hBox.getLayoutY() < getHeight()){
//                for (Node node : hBox.getChildrenUnmodifiable()){
//                    ((ListingBox)node).showImage(null);
//                }
//            }
//            else{
//                break;
//            }
        }
    }

    private boolean isInBounds(Node node){
        return true;
    }

    // Credit to https://stackoverflow.com/a/30780960/11245518
    private ArrayList<Node> getVisibleNodes(ScrollPane pane) {
        ArrayList<Node> visibleNodes = new ArrayList<>();
        Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());
        if (pane.getContent() instanceof Parent) {
            for (Node n : ((Parent) pane.getContent()).getChildrenUnmodifiable()) {
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (paneBounds.intersects(nodeBounds)) {
                    visibleNodes.add(n);
                }
            }
        }
        return visibleNodes;
    }

    private void openInspectMenu(MouseEvent event) {
        InspectMenuController.setInspectBoxListing(((ListingBox)event.getSource()).getListing());
    }

}
