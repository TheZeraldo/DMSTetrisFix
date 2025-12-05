package com.comp2042.ui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.comp2042.main.GameStates;
import com.comp2042.main.Main;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;
import com.comp2042.utils.HighScoreManager;
import com.comp2042.utils.KeyBinds;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

	public void setPrimaryStage(Stage stage) {
    	this.primaryStage = stage;
    }
    
    public void setMain(Main main) {
    	this.main = main;
    }

    @FXML
    void onExitButtonPressed(ActionEvent event) {
    	primaryStage.close();
    }

    @FXML
    void onStartButtonPressed(ActionEvent event) {
    	modePane.setVisible(true);
    }

    @FXML
    void displayHighScores(ActionEvent event) {
    	HighScoreDisplay.setup(highScoresVBox);
    	highScoresPane.setVisible(true);
    }
    
    @FXML
    void displaySettings(ActionEvent event) {
    	settingsPane.setVisible(true);
    }
    
    @FXML
    void setMode(ActionEvent event) throws Exception {
    	Button clicked = (Button) event.getSource();
    	ModeButtonManager.setMode(clicked);
    	modePane.setVisible(false);
    	GameStates.SetGameState(GameStates.PLAYING);
    	main.loadScene();
    }

    @FXML
    void hidePanes(ActionEvent event) {
    	modePane.setVisible(false);
    	highScoresPane.setVisible(false);
    	settingsPane.setVisible(false);
    }

}
