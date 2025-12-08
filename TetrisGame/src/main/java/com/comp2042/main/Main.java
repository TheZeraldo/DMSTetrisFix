package com.comp2042.main;

import javafx.application.Application;
import javafx.stage.Stage;

import com.comp2042.logic.game.GameController;
import com.comp2042.ui.controllers.GuiController;
import com.comp2042.ui.controllers.MenuController;


/**
 * The main entry point of the application.
 * This class manages application startup and switches between different game scenes.
 */
public class Main extends Application {
	
	private Stage primaryStage;
	private SceneManager sceneManager;

    /**
     * Starts the JavaFX application and loads the initial menu screen.
     *
     * @param primaryStage: the main window of the application
     * @throws Exception if the scene fails to load
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.primaryStage = primaryStage;
    	this.sceneManager =  new SceneManager(primaryStage);
    	showMenu();
    }

    /**
     * Starts the program.
     *
     * @param args: command line arguments passed to the program
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Loads a scene based on the current game state.
     *
     * @throws Exception if the scene fails to load
     */
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
    
    /**
     * Displays the main menu screen.
     *
     * @throws Exception if the menu scene fails to load
     */
    public void showMenu() throws Exception {
        MenuController m = sceneManager.loadScene("menu.fxml");
        m.setPrimaryStage(primaryStage);
        m.setMain(this);
        m.initialize();
    }
    
    /**
     * Starts the game by loading the game screen and initializing the game controller.
     *
     * @throws Exception if the game scene fails to load
     */
    public void startGame() throws Exception {
        GuiController c = sceneManager.loadScene("gameLayout.fxml");
        c.setMain(this);
        new GameController(c);
    }
}
