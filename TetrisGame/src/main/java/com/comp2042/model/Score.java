package com.comp2042.model;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(0);
    private final IntegerProperty lines = new SimpleIntegerProperty(0);

    public IntegerProperty scoreProperty() {
        return score;
    }
    
    public IntegerProperty levelProperty() {
    	return level;
    }

    public void add(int i){
        score.setValue(score.getValue() + i);
    }
    
    public void addLines(int clearedLines) {
        lines.setValue(lines.getValue() + clearedLines);
        
        if (GameModeManager.getGameMode() == GameModes.SPRINT) {
        	return;
        }
        
        if (lines.getValue() / 10 > level.getValue()) {
        	level.setValue(level.getValue() + 1);
        }
    }
    
    public int linesLeft() {
    	if (GameModeManager.getGameMode() == GameModes.SPRINT) {
    		return 40 - lines.getValue();
    	} else {
    		return 10 - (lines.getValue() % 10);
    	}
    }
    
    public int getFallSpeed() {
    	//Speed can't go lower than 100 ms, should be reached by level 10
    	return Math.max(100, 400 - (level.getValue() * 30));
    }

    public void reset() {
        score.setValue(0);
        level.setValue(0);
        lines.setValue(0);
    }
}
