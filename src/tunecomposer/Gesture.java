package tunecomposer;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * This class creates and edits Gesture objects to display notes in the tune 
 * and be played in MidiPLayer.
 * @extends SoundObject
 * 
 * @author Emma Twersky
 */
public final class Gesture extends SoundObject{
    /**
     * Creates the pane the Gesture is on. 
     */
    public Pane pane;
    
    /**
     * Creates the coordinates of the visualRectangle. 
     */
    public double topX;
    public double topY;
    public double bottomX;
    public double bottomY;
    
    /**
     * Initialize the Gesture object and variables, then constructs the 
     * display.
     * 
     * @param soundObjectPane
     */
    Gesture(Pane soundObjectPane){
        visualRectangle = new Rectangle();
        visualRectangle.setMouseTransparent(true);
        containedSoundObjects = new ArrayList();
        
        SoundObjectPaneController.SELECTED_SOUNDOBJECT_ARRAY.forEach((sObj) -> {
            containedSoundObjects.add(sObj);
        });
        setHandlers();
      
        refreshVisualRectangle();
        pane = soundObjectPane;
        pane.getChildren().add(visualRectangle);
        
        select();
    }
    
    /**
     * Refreshes the current coordinates of the visualRectangle display.
     */
    private void refreshVisualRectangle(){
        setVisualRectangleCoords();
        double width = bottomX - topX;
        double height = bottomY - topY;
        visualRectangle.setX(topX);
        visualRectangle.setY(topY);
        visualRectangle.setWidth(width);
        visualRectangle.setHeight(height);
    }
    
    /**
     * Finds and sets the current surrounding coordinates of the Gesture.
     * Sets the current TopX, TopY, BottomX and BottomY.
     */
    public void setVisualRectangleCoords() {
        topX = 2000;
        topY = 1280;
        bottomX = 0;
        bottomY = 0;

        containedSoundObjects.forEach((sObj) -> {
            Rectangle r = sObj.visualRectangle;
            if (topX > r.getX()) {
                topX = r.getX();
            }
            if (topY > r.getY()) {
                topY = r.getY();
            }
            if (bottomX < (r.getX() + r.getWidth())) {
                bottomX = r.getX() + r.getWidth();
            }
            if (bottomY < (r.getY() + r.getHeight())) {
                bottomY = r.getY() + r.getHeight();
            }
        });
    }
    
    /**
     * Sets all notes within this group as selected. 
     */
    @Override
    public void select(){
        this.containedSoundObjects.forEach((note) -> {
            note.select();
        });
        
        selected = true;
        visualRectangle.getStyleClass().removeAll("unselectedGesture");
        visualRectangle.getStyleClass().add("selectedGesture");
//        SoundObjectPaneController.updateSelectedSoundObjectArray(); 
    }
    
    /**
     * Sets all notes within this group as unselected.
     */
    @Override
    public void unselect(){
        this.containedSoundObjects.forEach((note) -> {
            note.unselect();
        });
        
        selected = false;
        visualRectangle.getStyleClass().removeAll("selectedGesture");
        visualRectangle.getStyleClass().add("unselectedGesture");
//        SoundObjectPaneController.updateSelectedSoundObjectArray(); 
    }
    
    /**
     * Change the selection state of all notes contained within this gesture.
     */
    @Override
    public void toggleSelection(){
        this.containedSoundObjects.forEach((note) -> {
            note.toggleSelection();
        });
    }
    
    /**
     * Returns whether object is selected.
     * 
     * @return boolean representing state of selected
     */
    @Override
    public boolean isSelected() {
        return selected;
    }        
    
    /**
     * Shifts all elements of gesture by given increment. 
     * This move includes all SoundObjects.
     * 
     * @param x number of horizontal pixels to shift notes
     * @param y number of vertical pixels to shift notes
     */
    @Override
    public void move(int x, int y){
        containedSoundObjects.forEach((note) -> {
            note.move(x, y);
        });
        refreshVisualRectangle();
    }
    
    /**
     * Resizes all notes of gesture by given increment. 
     * Also updates the size of all gestureBoxes.
     * 
     * @param l number of pixels to change note duration by
     */
    @Override
    public void changeLength(int l){
        containedSoundObjects.forEach((note) -> {
            note.changeLength(l);
        });
        refreshVisualRectangle();
    }
    
    /**
     * Recursively snap all items in gesture to closest note.
     */
    @Override
    public void snapInPlace() {
        containedSoundObjects.forEach((note) -> {
            note.snapInPlace();
        });
        refreshVisualRectangle();
    }
    
    /**
     * Sets the visualRectangle's mouse event handlers. 
     */
    @Override
    public void setHandlers() {
        this.containedSoundObjects.forEach((sObj) -> {
            sObj.visualRectangle.setOnMousePressed(handleGesturePressed);
            sObj.visualRectangle.setOnMouseDragged(handleGestureDragged);
            sObj.visualRectangle.setOnMouseReleased(handleGestureReleased);
        });
    }
    
    /**
     * Adds the note to the given pane. 
     * Does not manage selection. Does not handle if given pane is null. 
     * 
     * @param soundObjectPane
     */
    @Override
    public void addToPane(Pane soundObjectPane) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Removes the gesture from the given pane. 
     * Does not manage selection. Does not handle if given pane is null. 
     * 
     * @param soundObjectPane
     */
    @Override
    public void removeFromPane(Pane soundObjectPane){    
        containedSoundObjects.forEach((sObj) -> {
            sObj.removeFromPane(soundObjectPane);
        });
        soundObjectPane.getChildren().remove(visualRectangle);
    }
    
    /**
     * Adds the gesture to the MidiPlayer. 
     * 
     * @param player given TunePlayer object
     */
    @Override
    public void addToMidiPlayer(MidiPlayer player) {
        this.containedSoundObjects.forEach((sObj) -> {
            sObj.addToMidiPlayer(player);
        });
    }
    
    /**
     * Prepares to un-group the gesture.
     * Resets the containedSoundObjects to previous event handlers and 
     * removes the visual rectangle.
     */
    public void ungroup(){    
        pane.getChildren().remove(visualRectangle);
        containedSoundObjects.forEach((sObj) -> {
            sObj.setHandlers();
        });
    }
    
    /**
     * Handles note pressed event. 
     * Sets initial pressed values of the mouse and consumes the event.
     * 
     * @param event the mouse click event
     */
    EventHandler<MouseEvent> handleGesturePressed = (MouseEvent event) -> {
        CompositionPaneController.tunePlayerObj.stop();
        initialX = (int) event.getX();
        initialY = (int) event.getY();
        
        containedSoundObjects.forEach((sObj) -> {
            int editLengthMax = (int) (sObj.visualRectangle.getX() + sObj.visualRectangle.getWidth());
            int editLengthMin = editLengthMax - clickToEditLength;
            if ((editLengthMin <= initialX) && (initialX <= editLengthMax)) {
                draggingLength = true;
            }
        });
        
        event.consume();
    };
    
    /**
     * Handles note dragged event.
     * Selects and drags to move the note or drags to change duration, 
     * based on note click location conventions. Translates note and consumes 
     * event. Also updates note lists.
     * 
     * @param event the mouse click event
     */
    EventHandler<MouseEvent> handleGestureDragged = (MouseEvent event) -> {
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        if (!selected) {
//            SoundObjectPaneController.unselectAllSoundObjects();
            select();
        }
        
        if (draggingLength) {
            SoundObjectPaneController.SELECTED_SOUNDOBJECT_ARRAY.forEach((sObj) -> {
                sObj.changeLength(x - initialX);
            });
        }
        else {
            int translateX = (x - initialX);
            int translateY = (y - initialY);
            SoundObjectPaneController.SELECTED_SOUNDOBJECT_ARRAY.forEach((sObj) -> {
                sObj.move(translateX, translateY);
            });
        }
        
        initialX = x;
        initialY = y;
        
        event.consume();
    };
    
    /**
     * Handles mouse released.
     * Snaps note into place on lines, handles click note selections
     * based on the control button and consumes event.
     * 
     * @param event the mouse click event
     */
    EventHandler<MouseEvent> handleGestureReleased = (MouseEvent event) -> {
        draggingLength = false;
        
        if (event.isStillSincePress()) {
            if (event.isControlDown()) {
                toggleSelection();
            }
            else {
//                SoundObjectPaneController.unselectAllSoundObjects();
                select();
            }
        }
        
        SoundObjectPaneController.SELECTED_SOUNDOBJECT_ARRAY.forEach((sObj) -> {
            sObj.snapInPlace();
        });
        
        event.consume();
    };
}