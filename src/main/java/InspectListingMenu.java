import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jnr.ffi.annotations.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A pane class which displays all the listings in the form of ListingBox's.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class InspectListingMenu extends ListView<HBox> {

    private final int SPACING = 10;         // Spacing between boxes.
    private final int ROW_MAX = 6;          // How many boxes per row.

    private final int STARTING_BOUND = 10;  // The amount of rows which are loaded on start.
    private final int SELECT_BOUND = 4;     // When mouse hover on row, this is how many rows above and below are also checked.

    private ArrayList<NewAirbnbListing> currentListings;

    private ArrayList<HBox> rows;
    private HashMap<HBox, Boolean> imageShown;

    private static HashMap<NewAirbnbListing, ListingBox> boxLookup;
    //private VBox root;

    public static final ObservableList<String> SORT_OPTIONS = FXCollections.observableArrayList( "Listing Name", "Host Name", "Review Score", "Price");
    public static final ObservableList<String> ORDER_OPTIONS = FXCollections.observableArrayList("Ascending", "Descending");

    private static String sortSelected = SORT_OPTIONS.get(0);
    private static String orderSelected = ORDER_OPTIONS.get(0);

    private static boolean showOutOfRange = true;

    private DoubleProperty size = new SimpleDoubleProperty();

    public InspectListingMenu(ArrayList<NewAirbnbListing> listings) {
        currentListings = listings;


        getStyleClass().add("inspect-listing");

        if(boxLookup != null) {
            boxLookup.forEach((key, value) -> {
                value.cancelLoad();
            });
        }

        boxLookup = new HashMap<>();
        for (NewAirbnbListing listing : listings){
            ListingBox box = new ListingBox(listing);
            boxLookup.put(listing, box);
        }

        generate(currentListings);
    }

    public void reList(){
        System.out.println("Relist started");
        generate(currentListings);
        System.out.println("Relist ended");
    }



    public static void setSortSelected(String sortSelected) {
        InspectListingMenu.sortSelected = sortSelected;
    }
    public static void setOrderSelected(String orderSelected) {
        InspectListingMenu.orderSelected = orderSelected;
    }
    public static void setShowOutOfRange(boolean showOutOfRange) {
        InspectListingMenu.showOutOfRange = showOutOfRange;
    }

    /**
     * Generates all the components and properties of the list.
     * @param listings The list of properties
     */
    private void generate(ArrayList<NewAirbnbListing> listings){
        sortList(listings);                                                         // Order the list before splitting out of range values.

        // Split listing with in-range and out-of-range
        ArrayList<NewAirbnbListing> inRangeListings = new ArrayList<>();
        ArrayList<NewAirbnbListing> outOfRangeListings = new ArrayList<>();

        initialiseRangeLists(listings, inRangeListings, outOfRangeListings);        // Modifies the range lists to contain appropriate listings.
        initialiseBoxes(inRangeListings, outOfRangeListings);                       // Creates the actual list and panes of objects.

        //setPadding(new Insets(0, SPACING, 0, SPACING));
//        setFitToWidth(true);
//        setHbarPolicy(ScrollBarPolicy.NEVER);
//        setContent(root);

        setFocusTraversable(false);

        //setPadding(new Insets(SPACING));


        getItems().clear();
        getItems().addAll(rows);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));


        double padding = 40;
        boundsInLocalProperty().addListener((obs, old, bounds) -> {
            size.setValue((bounds.getWidth() - padding - ((ROW_MAX - 1) * SPACING)) / ROW_MAX);
        });

//        for (int i = 0; i <= STARTING_BOUND; i++){
//            showImageInRow(rows.get(i));                // Loads image of first SELECT_BOUND mount of rows.
//        }
    }


    /**
     * Sorts the list depending on the selected sort and order options.
     * @param listings The list to be sorted.
     */
    private void sortList(ArrayList<NewAirbnbListing> listings){
        switch(SORT_OPTIONS.indexOf(sortSelected)){
            case 0: // Listing Name
                listings.sort(Comparator.comparing(NewAirbnbListing::getName));                 // Ascending
                break;
            case 1: // Host Name
                listings.sort(Comparator.comparing(NewAirbnbListing::getHostName));             // Ascending
                break;
            case 2: // Review Score
                listings.sort(Comparator.comparing(NewAirbnbListing::getReviewScoresRating));   // Ascending
                break;
            case 3: // Price
                listings.sort(Comparator.comparing(NewAirbnbListing::getPrice));                // Ascending
                break;
        }

        if(ORDER_OPTIONS.indexOf(orderSelected) == 1){ // If Descending
            Collections.reverse(listings);
        }
    }

    /**
     * Initialise the in-range/out-of-range lists. Note that if the showOutOfRange variable is vale, the outOfRange list will be empty.
     * @param listings The list of all properties.
     * @param inRange The modified list of all in-range properties.
     * @param outOfRange The modified list of all out-of-range properties. (If enabled)
     */
    private void initialiseRangeLists(ArrayList<NewAirbnbListing> listings, ArrayList<NewAirbnbListing> inRange, ArrayList<NewAirbnbListing> outOfRange){
        for (NewAirbnbListing listing: listings){
            double price = listing.getPrice();
            if ((price >= RuntimeDetails.getMinimumPrice()) && (price <= RuntimeDetails.getMaximumPrice())){
                inRange.add(listing);
            }
            else if (showOutOfRange){ // If this is false then nothing is added so nothing will be displayed.
                outOfRange.add(listing);
            }
        }
    }

    /**
     * Initialises the listing boxes in their pane.
     * @param inRange The listings within the selected range.
     * @param outOfRange The listings out of the selected range.
     */
    private void initialiseBoxes(ArrayList<NewAirbnbListing> inRange, ArrayList<NewAirbnbListing> outOfRange){
        // Prepare Boxes and Rows
        rows = new ArrayList<>();
        //imageShown = new HashMap<>();

        //root = new VBox();   // Causes way less lag than GridPane
        //setPadding(new Insets(SPACING, 0, SPACING,0));
        //setSpacing(SPACING);

        // Create List
        int inRangeCount = inRange.size();
        ArrayList<NewAirbnbListing> newListing = new ArrayList<>();
        newListing.addAll(inRange);
        newListing.addAll(outOfRange);

        HBox row = null;
        int rowCount = 0;
        int listingCount = 0;
        for (NewAirbnbListing listing: newListing){
            if (rowCount == ROW_MAX || row == null){
                row = new HBox();
                //row.setOnMouseEntered(this::showRowImages);
                row.setSpacing(SPACING);
                row.setPadding(new Insets((((double)SPACING)/4), 0, (((double)SPACING)/4), 0));
                //imageShown.put(row, false);
                rows.add(row);
                //root.getChildren().add(row);
                rowCount = 0;
            }

            ListingBox box = boxLookup.get(listing);
            if (listingCount < inRangeCount) {
                box.setInvalidColour(false);
            }
            else {
                box.setInvalidColour(true);
            }
            box.showImage();

            box.minWidthProperty().bind(size);
            box.setOnMouseClicked(this::openInspectMenu);
            row.getChildren().add(box);
            HBox.setHgrow(box, Priority.ALWAYS);
            rowCount++;
            listingCount++;
        }
    }



    public void showRowImages(MouseEvent event){
        HBox hBox = ((HBox)(event.getSource()));
        showImageInRow(hBox);

        int index = rows.indexOf(hBox);
        int ok = -1;
        for (int i = 1; i < SELECT_BOUND; i++){
            if ((index - i) >= 0){
                showImageInRow(rows.get(index - i));
            }
            if ((index + i) < rows.size()){
                showImageInRow(rows.get(index + i));
            }
        }
    }

    private void showImageInRow(HBox row){
        if (!imageShown.get(row)) {
            imageShown.replace(row, true);
            for (Node node : row.getChildrenUnmodifiable()) {
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
