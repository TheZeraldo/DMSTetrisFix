package com.comp2042.ui.managers;

import java.util.Map;

import com.comp2042.utils.HighScoreManager;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * Handles the visual display of high scores in the UI.
 * Loads all scores from the HighScoreManager and inserts them into a VBox as formatted labels.
 */
public class HighScoreDisplay {

    /**
     * Populates the given VBox with all high scores.
     * Displays a "No High Scores" message if none exist.
     *
     * @param box: the VBox used to display the high scores
     */
	public static void setup(VBox box) {
    	box.getChildren().clear();
    	
    	if (HighScoreManager.getAllHighScores().entrySet().isEmpty()) {
    		Label label = new Label("No High Scores");
    		label.getStyleClass().add("highScoreLabelClass");
    		box.getChildren().add(label);
    	}
    	
    	for (Map.Entry<String, Integer> entry : HighScoreManager.getAllHighScores().entrySet()) {
    		String mode = entry.getKey();
    		int highScore = entry.getValue();
    		
    		Label label = new Label(mode + ": " + highScore);
    		label.getStyleClass().add("highScoreLabelClass");
    		box.getChildren().add(label);
    	}
	}
}
