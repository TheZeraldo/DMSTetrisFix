package com.comp2042.main;

import javafx.application.Application;
import javafx.stage.Stage;

import com.comp2042.logic.game.GameController;
import com.comp2042.ui.controllers.GuiController;
import com.comp2042.ui.controllers.MenuController;

public class Main extends Application {
	
	private Stage primaryStage;
	private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.primaryStage = primaryStage;
    	this.sceneManager =  new SceneManager(primaryStage);
    	showMenu();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public void loadScene() throws Exception{
    	switch(GameStates.gameState) {
    	case MENU:
    		showMenu();
    		break;
    	case PLAYING:
    		startGame();
    		break;
    	}
    }
    
    public void showMenu() throws Exception {
        MenuController m = sceneManager.loadScene("menu.fxml");
        m.setPrimaryStage(primaryStage);
        m.setMain(this);
        m.initialize();
    }
    
    public void startGame() throws Exception {
        GuiController c = sceneManager.loadScene("gameLayout.fxml");
        c.setMain(this);
        new GameController(c);
    }
}
