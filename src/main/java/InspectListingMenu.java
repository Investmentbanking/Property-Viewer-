import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    // List sort options
    public static final ObservableList<String> SORT_OPTIONS = FXCollections.observableArrayList( "Listing Name", "Host Name", "Review Score", "Price");
    public static final ObservableList<String> ORDER_OPTIONS = FXCollections.observableArrayList("Ascending", "Descending");
    private static String sortSelected = SORT_OPTIONS.get(0);
    private static String orderSelected = ORDER_OPTIONS.get(0);
    private static boolean showOutOfRange = true;

    private final int SPACING = 10;         // Spacing between boxes.
    private final int ROW_MAX = 6;          // How many boxes per row.
    private final DoubleProperty SIZE_PROPERTY = new SimpleDoubleProperty();    // Property for resize of boxes.

    // Events on scroll
    private static final int SCROLLEVENT_DELAY = 250; // In milliseconds
    public static final EventType<Event> SCROLL_TICK = new EventType<>(InspectListingMenu.class.getName() + ".SCROLL_TICK");
    private long lastScroll = 0;
    private int first = 0;
    private int last  = 0;
    private static boolean unloadOffscreen = false;

    private final ArrayList<NewAirbnbListing> currentListings;
    private ArrayList<HBox> rows;
    private static HashMap<NewAirbnbListing, ListingBox> boxLookup;

    /**
     * Constructor for a listing menu of boxes displaying properties.
     * @param listings The listings you want to display in the boxes.
     */
    public InspectListingMenu(ArrayList<NewAirbnbListing> listings) {
        currentListings = listings;

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

        // Events related to scrolling.
        addEventHandler(SCROLL_TICK, e -> {
            updateOnScreenImages(unloadOffscreen);
        });

        addEventFilter(ScrollEvent.ANY, (e) -> {
            lastScroll = System.currentTimeMillis();
        });

        Timeline notifyLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (lastScroll == 0)
                return;
            long now = System.currentTimeMillis();
            if (now - lastScroll > SCROLLEVENT_DELAY) {
                lastScroll = 0;
                fireEvent(new Event(this, this, SCROLL_TICK));
            }
        }));
        notifyLoop.setCycleCount(Timeline.INDEFINITE);
        notifyLoop.play();

        Platform.runLater(() -> {
            updateOnScreenImages(false);
        });
    }

    /**
     * Toggles if the images should unload offscreen or not.
     */
    public void toggleUnloadOffscreen() {
        unloadOffscreen = !unloadOffscreen;
        if (unloadOffscreen) {
            updateImages(0, first, false);
            updateImages(last + 1, getItems().size(), false);
        }
    }

    /**
     * Load or unload the images in all cells within a range.
     * @param start The starting index.
     * @param end The ending index.
     * @param load If true then image will load, if false then image will unload.
     */
    public void updateImages(int start, int end, boolean load){
        if ((start >= 0 && end <= getItems().size() && start < end)){
            for (int index = start; index < end; index++) {
                for (Node node : getItems().get(index).getChildrenUnmodifiable()) {
                    ListingBox box = (ListingBox) node;
                    if (load) { // Load the image
                        if (!box.isImageLoaded()) {
                            box.loadImage();
                        }
                    }
                    if (!load) { // Unload the image
                        if (box.isImageLoaded()) {
                            box.unloadImage();
                        }
                    }
                }
            }
        }
        else{
            System.err.println("Unaccepted range for updating Images");
        }
    }

    /**
     * Loads all time images of the boxes currently on screen.
     * @param unloadPrev If this is set to true, then the previous images will be unloaded.
     */
    private void updateOnScreenImages(boolean unloadPrev){
        if(unloadPrev){
            int prevFirst = first;
            int prevLast = last;
            setVisualIndex();
            boolean intersected = false;
            if (prevFirst < first && first < prevLast){ // First is between last index
                //System.out.println("prevFirst: " + prevFirst + "first: " + first);
                updateImages(prevFirst, first, false);
                intersected = true;
            }
            if (prevFirst < last && last < prevLast){ // Last is between last index
                //System.out.println("prevLast: " + prevLast + "last: " + last);
                updateImages(last+1, prevLast+1, false);
                intersected = true;
            }
            if(!intersected){
                updateImages(prevFirst, prevLast+1, false);
            }
        }
        else {
            setVisualIndex();
        }
        updateImages(first, last+1, true);
    }

    /**
     * Updates the first and last index value of the visual rows in the window.
     */
    private void setVisualIndex(){
        ListViewSkin<?> listViewSkin = (ListViewSkin<?>) getSkin();
        VirtualFlow<?> virtualFlow = (VirtualFlow<?>) listViewSkin.getChildren().get(0);
        first = virtualFlow.getFirstVisibleCell().getIndex();
        last = virtualFlow.getLastVisibleCell().getIndex();
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

        rows = new ArrayList<>();
        HBox row = null;
        int rowCount = 0;
        int listingCount = 0;
        for (NewAirbnbListing listing: newListing){
            if (rowCount == ROW_MAX || row == null){
                row = new HBox();
                row.setSpacing(SPACING);
                row.setPadding(new Insets((((double)SPACING)/4), 0, (((double)SPACING)/4), 0));
                rows.add(row);
                rowCount = 0;
            }

            ListingBox box = boxLookup.get(listing);
            box.setInvalidColour(listingCount >= inRangeCount);

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
}
