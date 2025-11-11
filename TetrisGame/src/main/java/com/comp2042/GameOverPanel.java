package com.comp2042;

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
	
	final private Label gameOverLabel;
	private Rectangle overlayBackground;
	private Label highScoreLabel;
	private Label yourScoreLabel;
	private Label highScore;
	private Label yourScore;
	private Label restartText;
	private FadeTransition fadeRestartText;
	private int finalScore;
	
    public GameOverPanel() {
        
        //Dark overlay
        overlayBackground = new Rectangle(400, 510);
        overlayBackground.setFill(Color.BLACK);
        overlayBackground.setOpacity(0);
        overlayBackground.setX(0);
        overlayBackground.setY(0);
        
    	//Game Over label
        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        gameOverLabel.setLayoutX(14);
        gameOverLabel.setLayoutY(203);
        
        //Add to root
        getChildren().addAll(overlayBackground, gameOverLabel);
        
        //Blinking effect for Game Over
        FadeTransition blink = new FadeTransition(Duration.seconds(0.3), gameOverLabel);
        
        blink.setFromValue(1.0);
        blink.setToValue(0);
        blink.setCycleCount(4);		//starts as visible, repeat twice, looks like it blinks 3 times
        blink.setAutoReverse(true);
        
        //Game Over moves to the top middle
        TranslateTransition moveToTop = new TranslateTransition(Duration.seconds(0.5), gameOverLabel);
        moveToTop.setByX(84);
        moveToTop.setByY(-153);
        
        //Darkening Effect
        FadeTransition darken = new FadeTransition(Duration.seconds(0.5), overlayBackground);
        
        darken.setFromValue(0.0);
        darken.setToValue(0.7);
        
        //Merge darken and move GAME OVER to move in parallel
        ParallelTransition moveAndDarken = new ParallelTransition(moveToTop, darken);
        
        //Sequentially play blinking effect then darken effect
        SequentialTransition gameOverFadeIn = new SequentialTransition(blink, moveAndDarken);
        
        //Display 2 labels: YOUR SCORE (with 0 under it), HIGH SCORE (with high score under it).
        highScoreLabel = new Label("HIGH SCORE");
        highScoreLabel.getStyleClass().add("gameOverStyleFinal");
        highScoreLabel.setLayoutX(98);
        highScoreLabel.setLayoutY(150);
        highScoreLabel.setOpacity(0);
        
        //HIGH SCORE taken from an external file
        highScore = new Label("2000");
        highScore.getStyleClass().add("gameOverStyleFinal");
        highScore.setLayoutX(98);
        highScore.setLayoutY(200);
        highScore.setOpacity(0);
        
        
        yourScoreLabel = new Label("YOUR SCORE");
        yourScoreLabel.getStyleClass().add("gameOverStyleFinal");
        yourScoreLabel.setLayoutX(98);
        yourScoreLabel.setLayoutY(300);
        yourScoreLabel.setOpacity(0);
        
        yourScore = new Label("0");                         //String.valueOf(gameController.getBoard().getScore().scoreProperty()));		//Add YOUR SCORE from board/score
        yourScore.getStyleClass().add("gameOverStyleFinal");
        yourScore.setLayoutX(98);
        yourScore.setLayoutY(350);
        yourScore.setOpacity(0);
        
        //"Press N to restart" label
        restartText = new Label("PRESS 'N' TO RESTART");
        restartText.getStyleClass().add("restartTextStyle");
        restartText.setLayoutX(30);
        restartText.setLayoutY(450);
        restartText.setOpacity(0);
        
        getChildren().addAll(highScoreLabel, highScore, yourScoreLabel, yourScore, restartText);
        
        //Fade in scores and their labels
        FadeTransition fadeHighScoreLabel = new FadeTransition(Duration.seconds(0.5), highScoreLabel);
        fadeHighScoreLabel.setFromValue(0);
        fadeHighScoreLabel.setToValue(1.0);
        
        FadeTransition fadeHighScore = new FadeTransition(Duration.seconds(0.5), highScore);
        fadeHighScore.setFromValue(0);
        fadeHighScore.setToValue(1.0);
        
        FadeTransition fadeYourScoreLabel = new FadeTransition(Duration.seconds(0.5), yourScoreLabel);
        fadeYourScoreLabel.setFromValue(0);
        fadeYourScoreLabel.setToValue(1.0);
        
        FadeTransition fadeYourScore = new FadeTransition(Duration.seconds(0.5), yourScore);
        fadeYourScore.setFromValue(0);
        fadeYourScore.setToValue(1.0);
        
        //Run all fades in parallel
        ParallelTransition fadeAllScores = new ParallelTransition(fadeHighScoreLabel, fadeHighScore, fadeYourScoreLabel, fadeYourScore);
        
        //'Press N to restart' fading in and out non-stop
        fadeRestartText = new FadeTransition(Duration.seconds(1), restartText);
        fadeRestartText.setFromValue(0);
        fadeRestartText.setToValue(1.0);
        fadeRestartText.setAutoReverse(true);
        fadeRestartText.setCycleCount(FadeTransition.INDEFINITE);
        
        fadeAllScores.setOnFinished(e -> {
        	animateScore(yourScore, finalScore);
        	fadeRestartText.play();
        });
        
        // Only apply CSS and  score fade in after animation finished
        gameOverFadeIn.setOnFinished(e -> {
            gameOverLabel.getStyleClass().add("gameOverStyleFinal");
            fadeAllScores.play();
        });
        
        //Checks if visible (visually) before running animation, uses lambda expression
        visibleProperty().addListener((obs, oldVisibility, currentVisibility) -> {
        	if (currentVisibility) {
        		resetAll();
        		gameOverFadeIn.play();
        	}
        });
        
    }
    
    //Animate score to go from 0 up to the player final score
    private void animateScore(Label label, int finalScore) {
    	Duration duration = Duration.seconds(2);

        //Initial Timeline with keyframes
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(label.textProperty(), "0")),
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
        highScoreLabel.setOpacity(0);
        highScore.setOpacity(0);
        yourScoreLabel.setOpacity(0);
        yourScore.setOpacity(0);
        yourScore.setText("0");
        restartText.setOpacity(0);
        fadeRestartText.stop();
    }
    
    public void setScore(int score) {
    	finalScore = score;
    }

}
