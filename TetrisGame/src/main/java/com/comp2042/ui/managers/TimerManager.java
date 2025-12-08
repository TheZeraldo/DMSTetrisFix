package com.comp2042.ui.managers;

import com.comp2042.ui.controllers.GuiController;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;


/**
 * Controls game timing, including countdown and count up timers.
 * Handles pausing, resuming, and game over timing logic.
 */
public class TimerManager {
	private final GuiController guiController;
	private final Label timeDisplay;
	private Timeline timerTimeline;
	private int timer;
	
    /**
     * Creates a new TimerManager to control time display and game timing.
     *
     * @param timeDisplay: the label used to show the time
     * @param guiController: the main GUI controller for game
     */
	public TimerManager(Label timeDisplay, GuiController guiController) {
		this.timeDisplay = timeDisplay;
		this.guiController = guiController;
	}
	
    /**
     * Toggles the paused state of the timer.
     *
     * @param isPaused: the BooleanProperty representing the pause state
     */
	public void togglePause(BooleanProperty isPaused) {
		if (isPaused.get()) {
			timerTimeline.pause();
		} else {
			timerTimeline.play();
		}
		isPaused.set(!isPaused.get());
	}
    
    /**
     * Stops the timer and handles game over timing logic.
     * If game mode is Sprint and player lost due to overflow, score is 0
     *
     * @param diedByOverflow: true if the game ended due to overflow, otherwise false
     */
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
	

    /**
     * Starts a countdown timer from a specified time.
     *
     * @param time: the starting time in seconds
     */
	public void startCountdown(int time) {
    	timer = time;
        timeDisplay.setText(String.valueOf(timer));
    	countDownTimer();
	}
	
    /**
     * Starts a count up timer from zero.
     */
	public void startTimer() {
        timer = 0;
        timeDisplay.setText(String.valueOf(timer));
        countUpTimer();
	}
	
    /**
     * Counts down the time.
     */
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
    
    /**
     * Counts up the time.
     */
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
    
    /**
     * Increases the current timer value.
     *
     * @param time: the number of seconds to add
     */
    public void increaseTimer(int time) {
    	timer += time;
    }
    
    /**
     * Returns the current timer value.
     *
     * @return the current time in seconds
     */
    public int getTime() {
    	return timer;
    }
}
