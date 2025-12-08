package com.comp2042.ui.managers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Manages score and level properties for the game UI.
 * Provides JavaFX properties for data binding.
 */
public class ScoreManager {
	private IntegerProperty score = new SimpleIntegerProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
	
    /**
     * Returns the score property used for UI binding.
     *
     * @return the score property
     */
	public IntegerProperty getScore() {
		return score;
	}
	
    /**
     * Returns the level property used for UI binding.
     *
     * @return the level property
     */
	public IntegerProperty getLevel() {
		return level;
	}
}
