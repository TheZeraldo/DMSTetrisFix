package com.comp2042.ui.managers;

import com.comp2042.ui.controllers.GuiController;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimerManager {
	private final GuiController guiController;
	private final Label timeDisplay;
	private Timeline timerTimeline;
	private int timer;
	
	public TimerManager(Label timeDisplay, GuiController guiController) {
		this.timeDisplay = timeDisplay;
		this.guiController = guiController;
	}
	
	public void togglePause(BooleanProperty isPaused) {
		if (isPaused.get()) {
			timerTimeline.pause();
		} else {
			timerTimeline.play();
		}
		isPaused.set(!isPaused.get());
	}
    
    public void gameOver(boolean diedByOverflow) {
    	timerTimeline.stop();
        if (GameModeManager.getGameMode() == GameModes.SPRINT) {
        	if (diedByOverflow) {
        		guiController.getGameOverPanel().setScore(0);
        	} else {
        		guiController.getGameOverPanel().setScore(timer);
        	}
        }
    }
	
	public void startCountdown(int time) {
    	timer = time;
        timeDisplay.setText(String.valueOf(timer));
    	countDownTimer();
	}
	
	public void startTimer() {
        timer = 0;
        timeDisplay.setText(String.valueOf(timer));
        countUpTimer();
	}
	
	private void countDownTimer() {
		if (timerTimeline != null) {
			timerTimeline.stop();
		}
	    timerTimeline = new Timeline(new KeyFrame(
	            Duration.seconds(1),
	            ae -> { 
	            	timer--;
	            	timeDisplay.setText(String.valueOf(timer));
	            	if (timer == 0) {
	            		guiController.gameOver(false);
	            	}
	            }
	    ));
	    timerTimeline.setCycleCount(Timeline.INDEFINITE);
	    timerTimeline.play();
	}
    
    private void countUpTimer() {
    	if (timerTimeline != null) {
    		timerTimeline.stop();
    	}
        timerTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                ae -> { 
                	timer++;
                	timeDisplay.setText(String.valueOf(timer));
                }
        ));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }
    
    public void increaseTimer(int time) {
    	timer += time;
    }
    
    public int getTime() {
    	return timer;
    }
}
