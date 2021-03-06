package tunecomposer.actionclasses;

import java.util.ArrayList;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import tunecomposer.SoundObject;
import static tunecomposer.SoundObjectParser.soundObjsToXML;

/**
 * An action which copies objects to the clipboard.
 */
public class CopyAction extends Action {
    
    /**
     * Constructs an action event to copy SoundObjects.
     * Sets affectObjs and soundObjectPane.
     * 
     * @param selectedObjs selList all SoundObjects to be affected
     */
    public CopyAction(ArrayList<SoundObject> selectedObjs) {
        affectedObjs = (ArrayList<SoundObject>) selectedObjs.clone();
    }
    
    /**
     * Copies all affectedObjs from the soundObjectPane.
     */
    @Override
    public void execute() {
        String sObjsString = soundObjsToXML(affectedObjs);
        
        Clipboard clipboard;
        clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sObjsString);
        clipboard.setContent(content);
    }
    
    /**
     * Not possible.
     */
    @Override
    public void undo() {
    }

    /**
     * Not possible. 
     */
    @Override
    public void redo() {
    }
}
