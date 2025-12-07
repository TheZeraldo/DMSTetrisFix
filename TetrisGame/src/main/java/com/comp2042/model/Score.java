package com.comp2042.model;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Manages the player's score, level, and cleared line count.
 * This class also calculates fall speed based on the current level.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(0);
    private final IntegerProperty lines = new SimpleIntegerProperty(0);

    /**
     * Gets the score property for UI data binding.
     *
     * @return the score property
     */
    public IntegerProperty scoreProperty() {
        return score;
    }
    
    /**
     * Gets the level property for UI data binding.
     *
     * @return the level property
     */
    public IntegerProperty levelProperty() {
    	return level;
    }


    /**
     * Adds points to the current score.
     *
     * @param i: the number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }
    
    /**
     * Adds cleared lines and updates the level when appropriate.
     *
     * @param clearedLines: the number of lines cleared
     */
    public void addLines(int clearedLines) {
        lines.setValue(lines.getValue() + clearedLines);
        
        if (GameModeManager.getGameMode() == GameModes.SPRINT) {
        	return;
        }
        
        if (lines.getValue() / 10 > level.getValue()) {
        	level.setValue(level.getValue() + 1);
        }
    }
    
    /**
     * Gets the number of lines left to clear for the next level, or to win if Sprint Mode.
     *
     * @return the remaining number of lines
     */
    public int linesLeft() {
    	if (GameModeManager.getGameMode() == GameModes.SPRINT) {
    		return 40 - lines.getValue();
    	} else {
    		return 10 - (lines.getValue() % 10);
    	}
    }
    
    /**
     * Calculates the fall speed of the bricks based on the current level.
     *
     * @return the fall speed in milliseconds
     */
    public int getFallSpeed() {
    	//Speed can't go lower than 100 ms, should be reached by level 10
    	return Math.max(100, 400 - (level.getValue() * 30));
    }

    /**
     * Resets the score, level, and cleared lines counters.
     */
    public void reset() {
        score.setValue(0);
        level.setValue(0);
        lines.setValue(0);
    }
}
