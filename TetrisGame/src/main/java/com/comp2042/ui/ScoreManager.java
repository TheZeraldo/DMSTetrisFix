package com.comp2042.ui;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreManager {
	private final GuiController guiController;
	private final VBox scoreVBox;
	private final VBox levelVBox;
	private final Label scoreDisplay;
	private final Label levelDisplay;
    private IntegerProperty score = new SimpleIntegerProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
	
	public ScoreManager(GuiController guiController, VBox scoreVBox, VBox levelVBox, Label scoreDisplay, Label levelDisplay) {
		this.guiController = guiController;
		this.scoreVBox = scoreVBox;
		this.levelVBox = levelVBox;
		this.scoreDisplay = scoreDisplay;
		this.levelDisplay = levelDisplay;
	}
	
	public IntegerProperty getScore() {
		return score;
	}
	
	public IntegerProperty getLevel() {
		return level;
	}
}
