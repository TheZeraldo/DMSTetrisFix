package com.comp2042.ui.controllers;

import com.comp2042.main.GameStates;
import com.comp2042.main.Main;
import com.comp2042.ui.managers.HighScoreDisplay;
import com.comp2042.ui.managers.KeyBindEditor;
import com.comp2042.ui.managers.ModeButtonManager;
import com.comp2042.utils.GameModes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Controller for the main menu screen.
 * Handles navigation between game modes, high scores, settings, and manages key binding and mode selection UI.
 */
public class MenuController {
	
	private Main main;
	private Stage primaryStage;
    
    @FXML
    private Label descriptionPopup;
	
	@FXML
	private Pane modePane;
	
	@FXML
	private Pane highScoresPane;
	
	@FXML
	private Pane settingsPane;
	
	@FXML
	private VBox highScoresVBox;
	
    @FXML
    private Button bigButton;

    @FXML
    private Button classicButton;

    @FXML
    private Button hardcoreButton;

    @FXML
    private Button invisButton;

    @FXML
    private Button sprintButton;

    @FXML
    private Button timeButton;

    @FXML
    private Button tonlyButton;

    @FXML
    private Button ultraButton;

    @FXML
    private Button leftKeyBindButton;

    @FXML
    private Button rightKeyBindButton;

    @FXML
    private Button rotateKeyBindButton;

    @FXML
    private Button softDropKeyBindButton;

    @FXML
    private Button hardDropKeyBindButton;

    @FXML
    private Button holdKeyBindButton;

    @FXML
    private Button pauseKeyBindButton;

    @FXML
    private Button menuKeyBindButton;

    @FXML
    private Button newGameKeyBindButton;
    
    /**
     * Initializes the menu UI, loads fonts, and prepares mode and key bind buttons.
     */
    public void initialize() {
    	Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    	modePane.setVisible(false);
    	descriptionPopup.setVisible(false);
    	descriptionPopup.setMouseTransparent(true);
    	highScoresPane.setVisible(false);
    	settingsPane.setVisible(false);
    	
    	initModeButtons();
    	initKeyBindButtons();
    }
    
    /**
     * Initializes all key bind buttons and links them to their actions.
     */
    private void initKeyBindButtons() {
    	KeyBindEditor.setup(leftKeyBindButton, "Move Left");
    	KeyBindEditor.setup(rightKeyBindButton, "Move Right");
    	KeyBindEditor.setup(rotateKeyBindButton, "Rotate");
    	KeyBindEditor.setup(softDropKeyBindButton, "Soft Drop");
    	KeyBindEditor.setup(hardDropKeyBindButton, "Hard Drop");
    	KeyBindEditor.setup(holdKeyBindButton, "Hold");
    	KeyBindEditor.setup(pauseKeyBindButton, "Pause");
    	KeyBindEditor.setup(menuKeyBindButton, "Main Menu");
    	KeyBindEditor.setup(newGameKeyBindButton, "New Game");
	}
    
    /**
     * Initializes all game mode buttons and configures their descriptions.
     */
    private void initModeButtons() {
    	ModeButtonManager.setup(classicButton, GameModes.CLASSIC, descriptionPopup, modePane);
    	ModeButtonManager.setup(hardcoreButton, GameModes.HARDCORE, descriptionPopup, modePane);
    	ModeButtonManager.setup(timeButton, GameModes.TIME, descriptionPopup, modePane);
    	ModeButtonManager.setup(sprintButton, GameModes.SPRINT, descriptionPopup, modePane);
    	ModeButtonManager.setup(ultraButton, GameModes.ULTRA, descriptionPopup, modePane);
    	ModeButtonManager.setup(invisButton, GameModes.INVISIBLE, descriptionPopup, modePane);
    	ModeButtonManager.setup(bigButton, GameModes.BIG, descriptionPopup, modePane);
    	ModeButtonManager.setup(tonlyButton, GameModes.TONLY, descriptionPopup, modePane);
    }

    /**
     * Sets the primary stage used for the application.
     *
     * @param stage: the primary application window
     */
	public void setPrimaryStage(Stage stage) {
    	this.primaryStage = stage;
    }
    
    /**
     * Sets the main application reference for scene switching.
     *
     * @param main: the main application instance
     */
    public void setMain(Main main) {
    	this.main = main;
    }

    /**
     * Closes the application when the exit button is pressed.
     *
     * @param event: the action event triggered by the exit button
     */
    @FXML
    void onExitButtonPressed(ActionEvent event) {
    	primaryStage.close();
    }

    /**
     * Displays the game mode selection pane.
     *
     * @param event: the action event triggered by the start button
     */
    @FXML
    void onStartButtonPressed(ActionEvent event) {
    	modePane.setVisible(true);
    }

    /**
     * Displays the high scores pane and loads score data.
     *
     * @param event: the action event triggered by the high scores button
     */
    @FXML
    void displayHighScores(ActionEvent event) {
    	HighScoreDisplay.setup(highScoresVBox);
    	highScoresPane.setVisible(true);
    }
    
    /**
     * Displays the settings pane.
     *
     * @param event: the action event triggered by the settings button
     */
    @FXML
    void displaySettings(ActionEvent event) {
    	settingsPane.setVisible(true);
    }
    
    /**
     * Sets the selected game mode and starts the game.
     *
     * @param event: the action event triggered by a mode button
     * @throws Exception if the game scene fails to load
     */
    @FXML
    void setMode(ActionEvent event) throws Exception {
    	Button clicked = (Button) event.getSource();
    	ModeButtonManager.setMode(clicked);
    	modePane.setVisible(false);
    	GameStates.SetGameState(GameStates.PLAYING);
    	main.loadScene();
    }

    /**
     * Hides all overlay panes (mode selection, high scores, and settings).
     *
     * @param event: the action event triggered by the close action
     */
    @FXML
    void hidePanes(ActionEvent event) {
    	modePane.setVisible(false);
    	highScoresPane.setVisible(false);
    	settingsPane.setVisible(false);
    }

}
