package com.comp2042;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
    
    public void initialize(URL location, ResourceBundle resources) {
    	Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    	modePane.setVisible(false);
    	descriptionPopup.setVisible(false);
    	descriptionPopup.setMouseTransparent(true);
    	
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

    @FXML
    void onExitButtonPressed(ActionEvent event) {
    	primaryStage.close();
    }

    @FXML
    void onStartButtonPressed(ActionEvent event) {
    	modePane.setVisible(true);
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

}
