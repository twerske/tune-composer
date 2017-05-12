package tunecomposer.actionclasses;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import tunecomposer.SoundObject;

/**
 * An action which deletes given SoundObjects from their pane.
 */
public class ChangeInstrumentAction extends Action {
    
    private final String instrument;
        
    /**
     * Constructs an action event to delete SoundObjects.
     * Sets affectObjs and soundObjectPane.
     * 
     * @param instrumentName
     * @param selectedObjs selList all SoundObjects to be affected
     * @param soundObjectPane the SoundObjectPane these selectedObjs are on
     */
    public ChangeInstrumentAction(String instrumentName, 
            ArrayList<SoundObject> selectedObjs, Pane soundObjectPane) {
        affectedObjs = (ArrayList<SoundObject>) selectedObjs.clone();
        affectedObjs.forEach((sObj) -> {
            sObj.setPreviousName();
        });
        this.soundObjectPane = soundObjectPane;
        instrument = instrumentName;
    }
    
    /**
     * Removes all affectedObjs from the soundObjectPane.
     */
    @Override
    public void execute() {
        affectedObjs.forEach((sObj) -> {
            sObj.changeInstrument(instrument);
        });
    }
    
    /**
     * Adds all affectedObjs to the soundObjectPane.
     */
    @Override
    public void undo() {
        affectedObjs.forEach((sObj) -> {
            sObj.changeToPreviousInstrument();
        });
    }

    /**
     * Re-executes action. 
     * Removes all notes in affectedObjs from soundObjectPane.
     */
    @Override
    public void redo() {
        execute();
    }
}
