package tunecomposer;

import java.util.Observable;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Controls and animates the red bar which visualizes the moment at which
 * the MidiPlayer is playing.
 */
public class RedBarPaneController extends Observable implements Initializable{
    
    /**
     * Creates timeline for animation and RED_BAR object.
     */
    private Timeline timeline;
    
    /**
     * Holds the visual representation of the Red Bar.
     */
    @FXML
    private Rectangle RED_BAR;
    
    /**
     * Sets frame rate, AKA animation speed, to 10.
     */
    private static final int FRAME_RATE = 10;
    
    /**
     * Initializes end of notes to time 0.
     */
    private int compositionEnd = 0;
    
    /**
     * Creates RED_BAR object on given pane and initializes timeline.
     * 
     * @param location the source of the scene
     * @param resources the resources of the utility of the scene
     */
    @FXML
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        timeline = new Timeline();
        RED_BAR.setVisible(false);
    }
        
    /**
     * Moves the line across the screen at the speed set by movementSpeed, 
     * disappears at end of last note displayed.
     * Notify Observer (Application Controller) that change has been made.
     * 
     * @param soundObjectPane pane where all SoundObject visuals live.
     */
    public void playAnimation(Pane soundObjectPane) {
        timeline.stop();
        timeline.getKeyFrames().clear();
        RED_BAR.setX(0);
        RED_BAR.setVisible(true);
        
        findEndCoordinate(soundObjectPane);
        
        KeyValue kv = new KeyValue(RED_BAR.xProperty(), compositionEnd);
        Duration duration = Duration.millis(compositionEnd * FRAME_RATE);
                
        EventHandler onFinished = (EventHandler<ActionEvent>) (ActionEvent event) -> {
            setChanged();
            timeline.stop();
            RED_BAR.setVisible(false);
            RED_BAR.setX(0);

            notifyObservers();
        };
        
        KeyFrame kf = new KeyFrame(duration, onFinished, kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }    
    
    /**
     * Stop the Red_Bar animation and make it disappear.
     */
    public void stopAnimation() {
        timeline.stop();
        RED_BAR.setVisible(false);
        RED_BAR.setX(0);
    }
    
    /**
     * Returns the X value of the right side of the final note on the pane.
     * 
     * @param soundObjectPane the pane of the SoundObjects
     */
    private void findEndCoordinate(Pane soundObjectPane) {
        compositionEnd = 0;
        for (Node sObj : soundObjectPane.getChildren()) {
            Rectangle r = (Rectangle) sObj;
            int end = (int) (r.getX() + r.getWidth());
            if (end > compositionEnd)
                compositionEnd = end;
        }
    } 
}