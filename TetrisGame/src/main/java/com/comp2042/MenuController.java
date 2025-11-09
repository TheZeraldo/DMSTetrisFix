package com.comp2042;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
	
	private Main main;
	private Stage primaryStage;

    @FXML
    private Button exitButton;

    @FXML
    private Button startButton;
    
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
    void onStartButtonPressed(ActionEvent event) throws Exception {
    	GameStates.SetGameState(GameStates.PLAYING);
    	main.start(primaryStage);
    }

}
