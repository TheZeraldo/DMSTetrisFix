package com.comp2042.ui.managers;

import com.comp2042.ui.controllers.GuiController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreManager {
	private IntegerProperty score = new SimpleIntegerProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
	
	public ScoreManager(GuiController guiController, VBox scoreVBox, VBox levelVBox, Label scoreDisplay, Label levelDisplay) {
	}
	
	public IntegerProperty getScore() {
		return score;
	}
	
	public IntegerProperty getLevel() {
		return level;
	}
}
