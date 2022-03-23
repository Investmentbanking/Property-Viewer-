import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A ListView which displays all the listings in the form of ListingBox's.
 * This is a list which the user can scroll to view all af the Boxes.
 *
 * @author Cosmo Colman (K21090628)
 * @version 23.03.2022
 */
public class InspectListingMenu extends ListView<HBox> {

    private final int SPACING = 10;         // Spacing between boxes.
    private final int ROW_MAX = 6;          // How many boxes per row.
    private final int SELECT_BOUND = 4;     // When mouse hover on row, this is how many rows above and below are also checked.
    private final DoubleProperty SIZE_PROPERTY = new SimpleDoubleProperty();    // Property for resize of boxes.

    // List sort options
    public static final ObservableList<String> SORT_OPTIONS = FXCollections.observableArrayList( "Listing Name", "Host Name", "Review Score", "Price");
    public static final ObservableList<String> ORDER_OPTIONS = FXCollections.observableArrayList("Ascending", "Descending");
    private static String sortSelected = SORT_OPTIONS.get(0);
    private static String orderSelected = ORDER_OPTIONS.get(0);
    private static boolean showOutOfRange = true;

    private final ArrayList<NewAirbnbListing> currentListings;
    private ArrayList<HBox> rows;
    //private HashMap<HBox, Boolean> imageShown;
    private static HashMap<NewAirbnbListing, ListingBox> boxLookup;

    /**
     * Constructor for a listing menu of boxes displaying properties.
     * @param listings The listings you want to display in the boxes.
     */
    public InspectListingMenu(ArrayList<NewAirbnbListing> listings) {
        currentListings = listings;

        // De-loads off-screen images so the new images can load.
        if(boxLookup != null) {
            boxLookup.forEach((key, value) -> value.cancelLoad());
        }

        // Creates all the ListingBoxes for each listing.
        boxLookup = new HashMap<>();
        for (NewAirbnbListing listing : listings){
            ListingBox box = new ListingBox(listing);
            boxLookup.put(listing, box);
        }

        getStyleClass().add("inspect-listing");
        setFocusTraversable(false);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        generate(currentListings);
    }

    /**
     * Relists the currently displayed boxes. Suitable if the list order properties have changed.
     */
    public void reList(){
        generate(currentListings);
    }

    /**
     * Sets the sort-by type of the properties.
     * @param sortSelected The sort-by type to set.
     */
    public static void setSortSelected(String sortSelected) {
        InspectListingMenu.sortSelected = sortSelected;
    }

    /**
     * Sets the order type of the properties.
     * @param orderSelected The order type to set.
     */
    public static void setOrderSelected(String orderSelected) {
        InspectListingMenu.orderSelected = orderSelected;
    }

    /**
     * Sets if the out-of-range listings should be shown
     * @param showOutOfRange True if you want to show the out-of-range listings.
     */
    public static void setShowOutOfRange(boolean showOutOfRange) {
        InspectListingMenu.showOutOfRange = showOutOfRange;
    }

    /**
     * Generates all the components and properties of the list.
     * @param listings The listing in the order to be generated.
     */
    private void generate(ArrayList<NewAirbnbListing> listings){
        sortList(listings);                                                          // Order the list before splitting out of range values.

        // Split listing with in-range and out-of-range
        ArrayList<NewAirbnbListing> inRangeListings = new ArrayList<>();
        ArrayList<NewAirbnbListing> outOfRangeListings = new ArrayList<>();

        initialiseRangeLists(listings, inRangeListings, outOfRangeListings);        // Modifies the range lists to contain appropriate listings.
        initialiseBoxes(inRangeListings, outOfRangeListings);                       // Creates the actual list and panes of objects.

        getItems().clear();
        getItems().addAll(rows);

        double padding = 40;
        boundsInLocalProperty().addListener((obs, old, bounds) -> SIZE_PROPERTY.setValue((bounds.getWidth() - padding - ((ROW_MAX - 1) * SPACING)) / ROW_MAX));

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
                listings.sort(Comparator.comparing(NewAirbnbListing::getName));                 // Ascending Order
                break;
            case 1: // Host Name
                listings.sort(Comparator.comparing(NewAirbnbListing::getHostName));             // Ascending Order
                break;
            case 2: // Review Score
                listings.sort(Comparator.comparing(NewAirbnbListing::getReviewScoresRating));   // Ascending Order
                break;
            case 3: // Price
                listings.sort(Comparator.comparing(NewAirbnbListing::getPrice));                // Ascending Order
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
        // Combine lists to make new list that will be displayed.
        int inRangeCount = inRange.size();
        ArrayList<NewAirbnbListing> newListing = new ArrayList<>();
        newListing.addAll(inRange);
        newListing.addAll(outOfRange);

        //imageShown = new HashMap<>();
        rows = new ArrayList<>();
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
                rowCount = 0;
            }

            ListingBox box = boxLookup.get(listing);
            box.setInvalidColour(listingCount >= inRangeCount);
            box.showImage();

            box.minWidthProperty().bind(SIZE_PROPERTY);
            box.setOnMouseClicked(this::openInspectMenu);

            row.getChildren().add(box);
            HBox.setHgrow(box, Priority.ALWAYS);

            rowCount++;
            listingCount++;
        }
    }

    /**
     * Opens the inspect-menu relative to the selected listing.
     * @param event MouseEvent call
     */
    private void openInspectMenu(MouseEvent event) {
        InspectMenuController.setInspectBoxListing(((ListingBox)event.getSource()).getListing());
    }




    // ALL BELOW MAY BE DELETE OR REPURPOSED

//    public void showRowImages(MouseEvent event){
//        HBox hBox = ((HBox)(event.getSource()));
//        showImageInRow(hBox);
//
//        int index = rows.indexOf(hBox);
//        int ok = -1;
//        for (int i = 1; i < SELECT_BOUND; i++){
//            if ((index - i) >= 0){
//                showImageInRow(rows.get(index - i));
//            }
//            if ((index + i) < rows.size()){
//                showImageInRow(rows.get(index + i));
//            }
//        }
//    }
//
//    private void showImageInRow(HBox row){
//        if (!imageShown.get(row)) {
//            imageShown.replace(row, true);
//            for (Node node : row.getChildrenUnmodifiable()) {
//                ((ListingBox) node).showImage();
//            }
//        }
//    }
//
//    public void showOnScreenImages(){
//        Bounds paneBounds = localToScene(getBoundsInParent());
//        for (HBox hBox : rows){
//            Bounds nodeBounds = hBox.localToScene(hBox.getBoundsInLocal());
//            if(paneBounds.intersects(nodeBounds)){
//                imageShown.replace(hBox, true);
//                for (Node node : hBox.getChildrenUnmodifiable()){
//                    //System.out.println("yes");
//                    ((ListingBox)node).showImage();
//                }
//            }
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
//        }
//    }
//
//    // Credit to https://stackoverflow.com/a/30780960/11245518
//    private ArrayList<Node> getVisibleNodes(ScrollPane pane) {
//        ArrayList<Node> visibleNodes = new ArrayList<>();
//        Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());
//        if (pane.getContent() instanceof Parent) {
//            for (Node n : ((Parent) pane.getContent()).getChildrenUnmodifiable()) {
//                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
//                if (paneBounds.intersects(nodeBounds)) {
//                    visibleNodes.add(n);
//                }
//            }
//        }
//        return visibleNodes;
//    }
}
