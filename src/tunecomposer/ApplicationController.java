package tunecomposer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.NONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import tunecomposer.actionclasses.Action;
import tunecomposer.actionclasses.CopyAction;
import tunecomposer.actionclasses.CutAction;

/**
 * Controls the application and handles the menu item selections.
 */
public class ApplicationController implements Initializable {  
    
    /**
     * Undo Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem UndoMenuItem;
    /**
     * Redo Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem RedoMenuItem;
    /**
     * Cut Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem CutMenuItem;
    /**
     * Copy Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem CopyMenuItem;
    /**
     * Paste Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem PasteMenuItem;
    /**
     * Delete Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem DeleteMenuItem;
    /**
     * Select All Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem SelAllMenuItem;
    /**
     * Group Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem GroupMenuItem;
    /**
     * Ungroup Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem UngroupMenuItem;
    /**
     * Play Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem PlayMenuItem;
    /**
     * Stop Menu Button, available to be enabled or disabled.
     */
    @FXML
    private MenuItem StopMenuItem;
    
    
    /**
     * Object that contains the undo and redo stack for the program. 
     */
    private ActionManager actionManager;
    
    /**
     * 
     */
    private ActionManagerObserver actionObserver;
    
    /**
     * Reference to object used to save and load SoundObjects from/to provided 
     * pane in constructor. 
     */
    private FileManager fileManager;
    
    /**
     * Reference to the compositionPaneController. 
     */
    @FXML
    public CompositionPaneController compositionPaneController;
        
    /**
     * Initialize FXML Application. 
     * Creates ActionManager to control actions. 
     * Sets CompPaneController to hold ActionManagerReference.
     * 
     * @param location the source of the scene
     * @param resources the resources of the utility of the scene
     */
    @FXML
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        
        toggleMenuItemDisable(true);
        
        actionObserver = new ActionManagerObserver();
        actionManager = compositionPaneController.actionManager;
        
        compositionPaneController.redBarPaneController
                .addObserver(actionObserver);
        
        actionManager.addObserver(actionObserver);
        
        compositionPaneController.setActionManager(actionManager);
        
        Pane sObjPane = compositionPaneController. soundObjectPaneController.soundObjectPane;
        fileManager = new FileManager(sObjPane, actionManager);
    
    }   
    
    @FXML
    protected void handleAboutMenuItemAction(ActionEvent event) {
        Alert about = new Alert(NONE);
        about.setTitle("About");
        about.setResizable(true);
        about.getDialogPane().setPrefSize(500, 150);
        String text = "TuneComposer by Synergy\u2122\n"
                + "\nRicardo Vivanco, Emma Twerskey, Cooper Lazar & Niki Lonberg.\n"
                + "\n\u2122 and \u00a9 2017 Synergy Inc. All Rights Reserved. License and Warranty";
        about.setContentText(text);
        about.getDialogPane().getButtonTypes().add(ButtonType.OK);
        about.showAndWait();
    }
    
    @FXML
    protected void handleNewMenuItemAction(ActionEvent event) {
    }
    
    @FXML
    protected void handleOpenMenuItemAction(ActionEvent event) {
    }
    
    @FXML
    protected void handleSaveMenuItemAction(ActionEvent event) {
        fileManager.save();
    }
    
    @FXML
    protected void handleSaveAsMenuItemAction(ActionEvent event) {
    }
        
    /**
     * Handles the Exit menu item and exits the scene.
     * 
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        System.exit(0);
    }
    
    /**
     * Handles the Undo menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handleUndoMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        actionManager.undo();        
    }
    
    /**
     * Handles the Undo menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handleRedoMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        actionManager.redo();
    }
    
    /**
     * Handles the Undo menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handleCutMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        compositionPaneController.cut();
    }

    /**
     * Handles the Undo menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handleCopyMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        compositionPaneController.copy();
    }

    /**
     * Handles the Undo menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handlePasteMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        compositionPaneController.paste();
    }
    
    /**
     * Handles the Select All menu item selection. 
     * Selects all SoundObjects.
     * 
     * @param event the button click event
     */
    @FXML
    protected void handleSelectAllMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        
        compositionPaneController.selectAll();
    }

    /**
     * Handles the Delete menu item selection. Deletes all selected SoundObjects.
     * 
     * @param event the button click event
     */
    @FXML
    protected void handleDeleteMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();

        compositionPaneController.delete();
    }
    
    /**
     * Handles the Group menu item selection.
     * 
     * @param event the button click event
     */
    @FXML
    protected void handleGroupMenuItemAction(ActionEvent event) {  
        compositionPaneController.stop();
        
        compositionPaneController.group();
    }
    
    /**
     * Handles the Ungroup menu item selection.
     * 
     * @param event the button click event
     */
    @FXML
    protected void handleUngroupMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();

        compositionPaneController.ungroup();
    }
    
    /**
     * Handles the Play menu item selection.
     * 
     * @param event the menu selection event
     */
    @FXML 
    protected void handlePlayMenuItemAction(ActionEvent event) {
        compositionPaneController.play();
        StopMenuItem.setDisable(false);
    }
    
    /**
     * Handles the Stop menu item selection.
     * 
     * @param event the button click event
     */
    @FXML 
    protected void handleStopMenuItemAction(ActionEvent event) {
        compositionPaneController.stop();
        PlayMenuItem.setDisable(false);
        StopMenuItem.setDisable(true);
    }
    
    /**
     * Disables or enables all menu items
     * 
     * @param on if true, then disable all menu items, if false, enable them
     */
    private void toggleMenuItemDisable(boolean on){
        UndoMenuItem.setDisable(on);
        RedoMenuItem.setDisable(on);
        SelAllMenuItem.setDisable(on);
        PlayMenuItem.setDisable(on);
        CopyMenuItem.setDisable(on);
        CutMenuItem.setDisable(on);
        DeleteMenuItem.setDisable(on);
        GroupMenuItem.setDisable(on);
        UngroupMenuItem.setDisable(on);
        StopMenuItem.setDisable(on);
        PasteMenuItem.setDisable(on);
    }
    
    /**
     * Nested class that observes the ActionManager and RedBarPaneController
     * and uses the observed info to disable or enable the menuItems.
     */
    class ActionManagerObserver implements Observer {
        
        /**
         * List of selected items updated from ActionManager.
         */
        ArrayList<SoundObject> selItems;
        /**
         * List of all sound objects on the pane, updated from ActionManager.
         */
        ObservableList<Node> allSoundObjects;
        
        boolean isRedoEmpty;
        boolean isUndoEmpty;
        
        /**
         * Update is called by notifyObservers in Action Manager and the Red Bar
         * Controller whenever a change is made to the undo stack or the red line
         * reaches the end of the last note.
         * Updates the disabling of all menu buttons.
         * 
         * @param observed instance of the observable class.
         * @param x optional object to be passed, not used.
         */
        @Override
        public void update(Observable observed, Object x){
            if (observed instanceof ActionManager){
                actionManager = (ActionManager) observed;
            }
            
            setDisable();
        }
        
        /**
         * Updates the disabling of all menu items based on changes made to the
         * observable classes.
         */
        private void setDisable(){
            
            isRedoEmpty = actionManager.isRedoStackEmpty();
            isUndoEmpty = actionManager.isUndoStackEmpty();
            
            selItems = SoundObjectPaneController.SELECTED_SOUNDOBJECT_ARRAY;
            
            allSoundObjects = compositionPaneController.
                    soundObjectPaneController.soundObjectPane.getChildren();
            
            toggleMenuItemDisable(false);
            
            checkDisableUndo();
            checkDisableRedo();
            checkDisableCopy();
            checkDisableCut();
            checkDisablePaste();
            checkDisableSelAll();
            checkDisableGroup();
            checkDisableUngroup();
            checkDisablePlay();
            checkDisableDelete();
            checkDisableStop();
        }
        
        /**
         * Disables "Undo" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableUndo(){
            if (isUndoEmpty){
                UndoMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Redo" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableRedo(){
            if (isRedoEmpty){
                RedoMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Copy" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableCopy(){
            if(selItems.isEmpty()){
                CopyMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Cut" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableCut(){
            if(selItems.isEmpty()){
                CutMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Paste" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisablePaste(){
            PasteMenuItem.setDisable(true);
            for (ArrayList<Action> aList: actionManager.undoStack){
                for (Action aObj: aList){
                    if ((aObj instanceof CutAction) || (aObj instanceof CopyAction)){
                        PasteMenuItem.setDisable(false);
                    }
                }
            }
        }
        
        /**
         * Disables "Select All" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableSelAll(){
            if (allSoundObjects.isEmpty()){
                SelAllMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Group" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableGroup(){
            if(selItems.isEmpty()){
                GroupMenuItem.setDisable(true);
            }
            else if (selItems.size()==1){
                GroupMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Ungroup" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableUngroup(){
            UngroupMenuItem.setDisable(true);
            for (SoundObject sObj: selItems){
                if (sObj instanceof Gesture){
                    UngroupMenuItem.setDisable(false);
                }
            }
        }
        
        /**
         * Disables "Delete" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableDelete(){
            if(selItems.isEmpty()){
                DeleteMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Play" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisablePlay(){
            if (allSoundObjects.isEmpty()){
                PlayMenuItem.setDisable(true);
            }
        }
        
        /**
         * Disables "Stop" if it needs to be disabled.
         * Precondition: button is enabled.
         */
        private void checkDisableStop(){
            StopMenuItem.setDisable(true);
        }
        
        
    }
}
