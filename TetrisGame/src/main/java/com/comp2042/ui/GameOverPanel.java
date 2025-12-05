package com.comp2042.ui;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;
import com.comp2042.utils.HighScoreManager;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameOverPanel extends Pane {
	private static final Duration BLINK_DURATION = Duration.seconds(0.3);
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.5);
	private static final Duration SCORE_FADE_DURATION = Duration.seconds(0.5);
	private static final Duration RESTART_DURATION = Duration.seconds(1);
	
	final private Label gameOverLabel;
	private Rectangle overlayBackground;
	private Label highScoreLabel;
	private Label yourScoreLabel;
	private Label highScore;
	private Label yourScore;
	private Label restartText;
	private Label newHighScoreLabel;
	private FadeTransition fadeRestartText;
	private int finalScore;
	private int currentHighScore;
	private boolean newHighScore = false;
	
    public GameOverPanel() {
        
        //Dark overlay
        overlayBackground = createOverlay();
        
    	//Game Over label
        gameOverLabel = createLabel("GAME OVER", "gameOverStyle", 214, 203);
        
        //Display 2 labels: YOUR SCORE (with 0 under it), HIGH SCORE (with high score under it).
        highScoreLabel = createLabel("HIGH SCORE", "gameOverStyleFinal", 198, 150);
        
        //HIGH SCORE taken from an external file
        highScore = createLabel("0", "gameOverStyleFinal", 198, 200);
        
        yourScoreLabel = createLabel("YOUR SCORE", "gameOverStyleFinal", 198, 300);

        yourScore = createLabel("0", "gameOverStyleFinal", 198, 350);
        
        //"Press N to restart" label
        restartText = createLabel("PRESS 'N' TO RESTART", "restartTextStyle", 130, 450);
        
        //Add to root
        getChildren().addAll(overlayBackground, gameOverLabel, highScoreLabel, highScore, yourScoreLabel, yourScore, restartText);
        
        setupVisibilityListener();
    }
    
    private Rectangle createOverlay() {
    	Rectangle overlay = new Rectangle(600, 510);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0);
        return overlay;
    }
    
    private Label createLabel(String labelText, String styleClass, int x, int y) {
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setOpacity(0);
        return label;
    }
    
    private void setupVisibilityListener() {
	    //Checks if visible (visually) before running animation, uses lambda expression
	    visibleProperty().addListener((obs, oldVisibility, currentVisibility) -> {
	    	if (currentVisibility) {
	    		resetAll();
	    		playGameOverAnimation();
	    	}
	    });
    }
    
    private void playGameOverAnimation() {
        //Blinking effect for Game Over
        FadeTransition blink = new FadeTransition(BLINK_DURATION, gameOverLabel);
        
        blink.setFromValue(1.0);
        blink.setToValue(0);
        blink.setCycleCount(4);		//starts as visible, repeat twice, looks like it blinks 3 times
        blink.setAutoReverse(true);
        
        //Game Over moves to the top middle
        TranslateTransition moveToTop = new TranslateTransition(TRANSLATE_DURATION, gameOverLabel);
        moveToTop.setByX(-16);
        moveToTop.setByY(-153);
        
        //Darkening Effect
        FadeTransition darken = new FadeTransition(TRANSLATE_DURATION, overlayBackground);
        
        darken.setFromValue(0.0);
        darken.setToValue(0.7);
        
        //Merge darken and move GAME OVER to move in parallel
        ParallelTransition moveAndDarken = new ParallelTransition(moveToTop, darken);
        
        //Sequentially play blinking effect then darken effect
        SequentialTransition gameOverFadeIn = new SequentialTransition(blink, moveAndDarken);
        
        // Only apply CSS and  score fade in after animation finished
        gameOverFadeIn.setOnFinished(e -> {
        gameOverLabel.getStyleClass().add("gameOverStyleFinal");
        fadeInScores();
        });
        
        gameOverFadeIn.play();
    }
    
    private void fadeInScores() {
        //Fade in scores and their labels
        //Run all fades in parallel
        ParallelTransition fadeAllScores = new ParallelTransition(
        		createFadeTransition(highScoreLabel),
        		createFadeTransition(highScore),
        		createFadeTransition(yourScoreLabel),
        		createFadeTransition(yourScore)
        		);
        
        fadeAllScores.setOnFinished(e -> {
    		animateScore(yourScore, 0, finalScore);
        	playRestartAnimation();
        	if (newHighScore) {
        		newHighScoreAnimation();
        	}
        });
        
        fadeAllScores.play();
    }

	private FadeTransition createFadeTransition(Label label) {
    	FadeTransition fade  = new FadeTransition(SCORE_FADE_DURATION, label);
        fade.setFromValue(0);
        fade.setToValue(1.0);
        return fade;
    }
    
    private void playRestartAnimation() {
        //'Press N to restart' fading in and out non-stop
        fadeRestartText = new FadeTransition(RESTART_DURATION, restartText);
        fadeRestartText.setFromValue(0);
        fadeRestartText.setToValue(1.0);
        fadeRestartText.setAutoReverse(true);
        fadeRestartText.setCycleCount(FadeTransition.INDEFINITE);
        
        fadeRestartText.play();
	}
    
    private void newHighScoreAnimation() {
    	highScoreLabel.setTextFill(Color.GOLD);
    	highScore.setTextFill(Color.GOLD);
    	
    	newHighScoreLabel = new Label("NEW HIGH SCORE!!");
    	newHighScoreLabel.getStyleClass().add("gameOverStyleFinal");
    	newHighScoreLabel.setTextFill(Color.GOLD);
    	newHighScoreLabel.setLayoutX(158);
    	newHighScoreLabel.setLayoutY(110);
    	newHighScoreLabel.setOpacity(0);
        getChildren().add(newHighScoreLabel);
        
        FadeTransition newHighScoreFade = new FadeTransition(Duration.seconds(0.2), newHighScoreLabel);
        newHighScoreFade.setFromValue(0);
        newHighScoreFade.setToValue(1.0);
        newHighScoreFade.setAutoReverse(true);
        newHighScoreFade.setCycleCount(11);
    	newHighScoreFade.setDelay(Duration.seconds(3));
    	
    	newHighScoreFade.play();
    	
        newHighScoreFade.setOnFinished(e -> {
            animateScore(highScore, currentHighScore, finalScore);
        });
    }
    
    //Animate score to go from 0 up to the player final score
    private void animateScore(Label label, int initialScore, int finalScore) {
    	Duration duration = Duration.seconds(2);

        //Initial Timeline with keyframes
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(label.textProperty(), String.valueOf(initialScore))),
            new KeyFrame(duration, event -> label.setText(String.valueOf(finalScore)))
        );

        //timeline.getKeyFrames().clear();
        final int frames = 60; 				//60 fps for smooth animation
        for (int i = 0; i <= frames; i++) {
            double progress = (double) i / frames;
            int currentValue = (int) (finalScore * progress);
            timeline.getKeyFrames().add(
                new KeyFrame(duration.multiply(progress), new KeyValue(label.textProperty(), String.valueOf(currentValue)))
            );
        }

        timeline.play();
    }
    
    private void resetAll() {
    	overlayBackground.setOpacity(0);
    	gameOverLabel.getStyleClass().remove("gameOverStyleFinal");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        gameOverLabel.setTranslateX(0);
        gameOverLabel.setTranslateY(0);
        highScoreLabel.setOpacity(0);
        highScore.setOpacity(0);
        yourScoreLabel.setOpacity(0);
        yourScore.setOpacity(0);
    	yourScore.setText("0");
        restartText.setOpacity(0);
        if (fadeRestartText != null) {
        	fadeRestartText.stop();
        }
        if (newHighScoreLabel != null) {
            newHighScoreLabel.setOpacity(0);
        }
    }
    
    public void setScore(int score) {
    	finalScore = score;

    	currentHighScore = HighScoreManager.getHighScore();
    	highScore.setText(String.valueOf(currentHighScore));
    	System.out.println(currentHighScore);
    	
    	if (HighScoreManager.isHighScore(score, currentHighScore)) {
    		newHighScore = true;
    		HighScoreManager.saveHighScore(score);
    	} else {
    		newHighScore = false;
    	}
    }
}
