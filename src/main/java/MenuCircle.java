import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * A pane of a circle that represents a borough. The size represents the amount of available listings and the colour represents the average price.
 *
 * @author Cosmo Colman (K21090628)
 * @version 22.03.2022
 */
public class MenuCircle extends StackPane {

    private Borough borough;
    private ArrayList<NewAirbnbListing> listings;

    //private Pane shapePane;
    private Circle circle;
    private Text text;

    private double circleSize;
    private Color circleColor;

    //private static final Color CHEAP_COLOR = Color.rgb(74, 222, 16);
    public static final Color CHEAP_COLOR = Color.rgb(46, 50, 177);
    public static final Color EXPENSIVE_COLOR = Color.rgb(242, 63, 63);

    private static final int MIN_SIZE = 35;
    private static final int MAX_SIZE = 150;

    private static final int MAX_FONT_SIZE = 35;
    private static final int MIN_FONT_SIZE = 10;

    private int fontSize;

    private FillTransition filltCircleIn, filltCircleOut;
    private ScaleTransition stTextIn, stTextOut;

    private static ArrayList<MenuCircle> allCircles;

    public MenuCircle(Borough borough, ArrayList<NewAirbnbListing> listings) {
        this.borough = borough;
        this.listings = listings;

        circleSize = calculateSize();
        circleColor = calculateColor();

        int tempFontSize = MAX_FONT_SIZE - MIN_FONT_SIZE;
        fontSize = (int)(((circleSize/MAX_SIZE) * tempFontSize) + MIN_FONT_SIZE);

        circle = new Circle(circleSize, circleColor);
        text = new Text(borough.getName());
        text.setMouseTransparent(true);

        circle.setOnMouseClicked(this::openInspectionWindow);

        filltCircleIn = new FillTransition(Duration.millis(100), circle, circleColor, circleColor.darker());
        filltCircleOut = new FillTransition(Duration.millis(100), circle, circleColor.darker(), circleColor);

        stTextIn = new ScaleTransition(Duration.millis(100), text);
        stTextIn.setToX(1.2);
        stTextIn.setToY(1.2);
        stTextOut = new ScaleTransition(Duration.millis(100), text);
        stTextOut.setToX(1);
        stTextOut.setToY(1);


        circle.setOnMouseEntered(this::mouseEnter);
        circle.setOnMouseExited(this::mouseLeave);

        //setOnMouseDragged(this::moveToMouse);       // FOR TESTING

        getChildren().addAll(circle);//,text);




//        circle.radiusProperty().bind(widthProperty());

        putTextInCircleNEW();

        // Visualise pane test
        //setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }



    private void mouseEnter(MouseEvent event){
        setCursor(Cursor.HAND);
        filltCircleIn.play();
        stTextIn.play();
    }

    private void mouseLeave(MouseEvent event){
        setCursor(Cursor.DEFAULT);
        filltCircleOut.play();
        stTextOut.play();
    }

    private void openInspectionWindow(MouseEvent event){
       InspectMenuController.create(listings, borough.getName());
    }

    private void putTextInCircleNEW(){
        do {
            Font newFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, fontSize--);
            text.setFont(newFont);
        }while (text.getLayoutBounds().getWidth() > circleSize*2);

        text.setFill(Color.WHITE);

        getChildren().add(text);
    }


    public Circle getCircle() {
        return circle;
    }

    public static void setAllCircles(ArrayList<MenuCircle> allCircles) {
        MenuCircle.allCircles = allCircles;
    }

    public Borough getBorough() {
        return borough;
    }

    public double getRadius(){
        return circle.getRadius();
    }

    public int getSizeValue(){
        return borough.getAvgPrice();
    }

    public void setPosition(int x, int y){
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setPositionFromCentre(int x, int y){
        setLayoutX(x - getRadius());
        setLayoutY(y - getRadius());

//        setLayoutX(x - (getWidth()/2));
//        setLayoutY(y - (getHeight()/2));

//        setTranslateX(x - getRadius());
//        setTranslateY(y - getRadius());
    }

    public double getLayoutCentreX(){
        return getLayoutX() + getRadius();
    }
    public double getLayoutCentreY(){
        return getLayoutY() + getRadius();
    }
    /**
     * Calculates what the colour of the circle should be based on the price.
     * @return the calculated colour of the circle.
     */
    private Color calculateColor(){
        double min = borough.getAvgPriceStat().min();
        double max = borough.getAvgPriceStat().max();

        double calc = (borough.getAvgPrice() - min) / (max - min);

        double r = EXPENSIVE_COLOR.getRed() - CHEAP_COLOR.getRed();
        double g = EXPENSIVE_COLOR.getGreen() - CHEAP_COLOR.getGreen();
        double b = EXPENSIVE_COLOR.getBlue() - CHEAP_COLOR.getBlue();

        r = (r * calc) + CHEAP_COLOR.getRed();
        g = (g * calc) + CHEAP_COLOR.getGreen();
        b = (b * calc) + CHEAP_COLOR.getBlue();

        return new Color(r, g, b, 1.0);
    }

    /**
     * Calculates what the size of the circle should be based on how many available listings there are.
     * @return the calculated size of the circle.
     */
    private int calculateSize(){
        double min = borough.getAvailableStat().min();
        double max = borough.getAvailableStat().max();

        double calc;
        calc = (borough.getAvailable() - min) / (max - min);
        calc *= MAX_SIZE - MIN_SIZE;
        calc += MIN_SIZE;

        return (int)calc;
    }

//    private void moveToMouse(MouseEvent event){
//        setManaged(false);
//        this.setTranslateX(event.getX() + this.getTranslateX() - (getWidth()/2));// - 120);
//        this.setTranslateY(event.getY() + this.getTranslateY()- (getHeight()/2));// - 50);
//        event.consume();
//
//        System.out.println(checkCollision(this) + " for " + borough.getName());
//    }

//    //https://stackoverflow.com/a/15014709/11245518
//    private boolean checkCollision(MenuCircle circle) {
//        boolean collisionDetected = false;
//        for (MenuCircle static_circle : allCircles) {
//            if (static_circle != circle) {
//                Shape intersect = Shape.intersect(circle.getCircle(), static_circle.getCircle());
//                if (intersect.getBoundsInLocal().getWidth() != -1) {
//                    collisionDetected = true;
//                }
//            }
//        }
//        return collisionDetected;
//    }
}