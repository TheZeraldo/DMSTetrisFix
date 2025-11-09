package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
	
	private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
    	
    	switch(GameStates.gameState) {
    	
    	case MENU:
    		showMenu();
    		break;
    	case PLAYING:
    		startGame();
    		break;
    	case GAME_OVER:
    		//add GAME OVER screen
    		break;
    	}
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public void startGame() throws Exception {
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 400, 510);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        new GameController(c);
    }
    
    public void showMenu() throws Exception {
    	URL location = getClass().getClassLoader().getResource("menu.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        MenuController m = fxmlLoader.getController();
        m.setPrimaryStage(primaryStage);
        m.setMain(this);

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 400, 510);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
