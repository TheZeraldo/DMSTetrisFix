package com.comp2042.main;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class is responsible for creating and displaying UI layouts from FXML files.
 */
public class SceneManager {
	private final Stage primaryStage;

    /**
     * Creates a new SceneManager for the given stage.
     *
     * @param primaryStage: the main application window
     */
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    /**
     * Loads a JavaFX scene from an FXML file and displays it.
     *
     * @param fxmlFile: the name of the FXML file to load
     * @return the controller instance for the loaded scene
     * @throws Exception if the FXML file cannot be loaded
     */
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
