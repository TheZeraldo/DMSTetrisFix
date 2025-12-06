package com.comp2042.main;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
	private final Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public <T> T loadScene(String fxmlFile) throws Exception{
        URL location = getClass().getClassLoader().getResource(fxmlFile);

        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        
        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 600, 510);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        return fxmlLoader.getController();
    }
}
