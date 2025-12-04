package com.comp2042.ui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.comp2042.main.GameStates;
import com.comp2042.main.Main;
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
    
    private Button waitingForKey = null;
    
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
    	setupKeyBindButton(leftKeyBindButton, "Move Left");
    	setupKeyBindButton(rightKeyBindButton, "Move Right");
    	setupKeyBindButton(rotateKeyBindButton, "Rotate");
    	setupKeyBindButton(softDropKeyBindButton, "Soft Drop");
    	setupKeyBindButton(hardDropKeyBindButton, "Hard Drop");
    	setupKeyBindButton(holdKeyBindButton, "Hold");
    	setupKeyBindButton(pauseKeyBindButton, "Pause");
    	setupKeyBindButton(menuKeyBindButton, "Main Menu");
    	setupKeyBindButton(newGameKeyBindButton, "New Game");
	}
    
    private void initModeButtons() {
    	onModeButtonHover(classicButton, GameModes.CLASSIC);
    	onModeButtonHover(hardcoreButton, GameModes.HARDCORE);
    	onModeButtonHover(timeButton, GameModes.TIME);
    	onModeButtonHover(sprintButton, GameModes.SPRINT);
    	onModeButtonHover(ultraButton, GameModes.ULTRA);
    	onModeButtonHover(invisButton, GameModes.INVISIBLE);
    	onModeButtonHover(bigButton, GameModes.BIG);
    	onModeButtonHover(tonlyButton, GameModes.TONLY);
    }

	public void setPrimaryStage(Stage stage) {
    	this.primaryStage = stage;
    }
    
    public void setMain(Main main) {
    	this.main = main;
    }
    
    void onModeButtonHover(Button button, GameModes mode) {
    	button.setOnMouseEntered(e -> showPopup(button, GameModes.GetGameModeDescription(mode)));
    	button.setOnMouseExited(e -> hidePopup());
    }
    
    void showPopup(Button button, String description) {
    	descriptionPopup.setText(description);
    	descriptionPopup.setVisible(true);
    	
    	
    	double buttonX = button.getLayoutX();
    	double buttonY = button.getLayoutY();
    	double buttonHeight = button.getHeight();
    	
    	double popupX = buttonX;
    	double popupY = buttonY + buttonHeight;
    	double popupHeight = descriptionPopup.getHeight();
    	
    	double paneHeight = modePane.getHeight();
    	
    	if (popupY + popupHeight > paneHeight) {
    		popupY = buttonY - popupHeight;
    	}
    	
    	descriptionPopup.setLayoutX(popupX);
    	descriptionPopup.setLayoutY(popupY);
    }
    
    void hidePopup() {
    	descriptionPopup.setVisible(false);
    }
    
    private void setupKeyBindButton(Button button, String action) {
    	button.setText(KeyBinds.getKey(action).toString());
    	
    	button.setOnAction(e -> {
    		waitingForKey = button;
    		button.setText("Press a key...");
    		
    		Scene scene = button.getScene();
    		
    		EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
    			@Override
    			public void handle(KeyEvent event) {
    				KeyCode key = event.getCode();
    				if (KeyBinds.setKey(action, key)) {
    					button.setText(key.toString());
    				} else {
    					button.setText("Key already in use!");
    				}
    				
    				waitingForKey = null;
    				scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
    			}
    		};
    		
    		scene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
    	});
    }
    
//    private void setupKeyBindButton(Button button, String action) {
//    	button.setText(KeyBinds.getKey(action).toString());
//    	
//    	button.setOnAction(e -> {
//    		waitingForKey = button;
//    		button.setText("Press a key...");
//    		
//    		button.getScene().setOnKeyPressed(event -> {
//    			KeyCode key = event.getCode();
//    			KeyBinds.setKey(action, key);
//    			button.setText(KeyBinds.getKey(action).toString());
//    			button.getScene().setOnKeyPressed(null);
//    			waitingForKey = null;
//    		});
//    	});
//    }

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
    	highScoresVBox.getChildren().clear();
    	
    	for (Map.Entry<String, Integer> entry : HighScoreManager.getAllHighScores().entrySet()) {
    		String mode = entry.getKey();
    		int highScore = entry.getValue();
    		
    		Label highScoreLabel = new Label(mode + ": " + highScore);
    		highScoreLabel.getStyleClass().add("highScoreLabelClass");
    		highScoresVBox.getChildren().add(highScoreLabel);
    	}
    	highScoresPane.setVisible(true);
    }
    
    @FXML
    void displaySettings(ActionEvent event) {
    	settingsPane.setVisible(true);
    }
    
    @FXML
    void setMode(ActionEvent event) throws Exception {
    	Button clicked = (Button) event.getSource();
    	
    	switch (clicked.getText()) {
			case "Classic":
				GameModes.SetGameMode(GameModes.CLASSIC);
				break;
			case "Hardcore":
				GameModes.SetGameMode(GameModes.HARDCORE);
				break;
			case "Time Limit":
				GameModes.SetGameMode(GameModes.TIME);
				break;
			case "Sprint":
				GameModes.SetGameMode(GameModes.SPRINT);
				break;
			case "Ultra":
				GameModes.SetGameMode(GameModes.ULTRA);
				break;
			case "Invisible":
				GameModes.SetGameMode(GameModes.INVISIBLE);
				break;
			case "Big Mode":
				GameModes.SetGameMode(GameModes.BIG);
				break;
			case "T-Mode":
				GameModes.SetGameMode(GameModes.TONLY);
				break;
		}
    	
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
