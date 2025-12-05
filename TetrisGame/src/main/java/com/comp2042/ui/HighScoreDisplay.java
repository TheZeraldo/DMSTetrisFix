package com.comp2042.ui;

import java.util.Map;

import com.comp2042.utils.HighScoreManager;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HighScoreDisplay {

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
