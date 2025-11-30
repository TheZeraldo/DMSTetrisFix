package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.logic.bricks.Brick;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane ghostBrickPanel;

    @FXML
    private GameOverPanel gameOverPanel;
    
    @FXML
    private Label scoreDisplay;
    
    @FXML
    private Label levelDisplay;
    
    @FXML
    private Label linesDisplay;
    
    @FXML
    private Label timeDisplay;
    
    @FXML
    private Pane nextBrickPane;
    
    @FXML
    private Pane pausePane;
    
    @FXML
    private Pane holdBrickPane;
    
    @FXML
    private VBox scoreVBox;
    
    @FXML
    private VBox levelVBox;
    
    private Main main;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;
    
    private Rectangle[][] ghostRectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    
    private IntegerProperty score = new SimpleIntegerProperty();
    
    private IntegerProperty level = new SimpleIntegerProperty();
    
    private Timeline timerTimeline;
    
    private int timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        hardDrop(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.C) {
                        refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                }
                if (isGameOver.getValue() == Boolean.FALSE) {
                	if (keyEvent.getCode() == KeyCode.P) {
                		pauseGame(null);
                        keyEvent.consume();
                    }
                }
            	if (keyEvent.getCode() == KeyCode.ESCAPE) {
            		returnToMainMenu();
                    keyEvent.consume();
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangle.setArcHeight(9);
                rectangle.setArcWidth(9);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        ghostRectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                if (brick.getBrickData()[i][j] != 0) {
                    rectangle.setFill(Color.GRAY.deriveColor(0, 1, 1, 0.3));
                    rectangle.setArcHeight(9);
                    rectangle.setArcWidth(9);
                } else {
                    rectangle.setFill(Color.TRANSPARENT);
                }
                ghostRectangles[i][j] = rectangle;
                ghostBrickPanel.add(rectangle, j, i);
            }
        }
        ghostBrickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getxPosition() * ghostBrickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        ghostBrickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getLandingYPosition() * ghostBrickPanel.getHgap() + brick.getLandingYPosition() * BRICK_SIZE);

        if (GameModes.gameMode == GameModes.SPRINT) {
        	scoreVBox.setVisible(false);
        	levelVBox.setVisible(false);
        } else {
			scoreVBox.setVisible(true);
        	levelVBox.setVisible(true);
		}
        updateFallSpeed(400);
        if (GameModes.gameMode == GameModes.TIME) {
        	timer = 20;
            timeDisplay.setText(String.valueOf(timer));
        	countdownTimer();
        } else if (GameModes.gameMode == GameModes.ULTRA) {
        	timer = 120;
            timeDisplay.setText(String.valueOf(timer));
        	countdownTimer();
        } else {
            timer = 0;
            timeDisplay.setText(String.valueOf(timer));
            startTimer();
        }
    }

    public void updateFallSpeed(int speed) {
    	//stops old timeline and starts new one with new speed
    	if (timeLine != null) {
        	timeLine.stop();
    	}
    	if (GameModes.gameMode == GameModes.HARDCORE) {
	        timeLine = new Timeline(new KeyFrame(
	                Duration.millis(100),
	                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
	        ));
    	} else {
    		System.out.println(speed);			//TODO: Remove
	        timeLine = new Timeline(new KeyFrame(
	                Duration.millis(speed),
	                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
	        ));
    	}
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
	}
    
    private void startTimer() {
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
    
    private void countdownTimer() {
    	if (timerTimeline != null) {
    		timerTimeline.stop();
    	}
        timerTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                ae -> { 
                	timer--;
                	timeDisplay.setText(String.valueOf(timer));
                	if (timer == 0) {
                		gameOver(false);
                	}
                }
        ));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }
    
    public void increaseTimer(int time) {
    	timer += time;
    }

	private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

            ghostBrickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getxPosition() * ghostBrickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            ghostBrickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getLandingYPosition() * ghostBrickPanel.getHgap() + brick.getLandingYPosition() * BRICK_SIZE);
            
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                    
                    if (brick.getBrickData()[i][j] != 0) {
                    	ghostRectangles[i][j].setFill(Color.GRAY.deriveColor(0, 1, 1, 0.3));
                    	ghostRectangles[i][j].setArcHeight(9);
                    	ghostRectangles[i][j].setArcWidth(9);
                    } else {
                    	ghostRectangles[i][j].setFill(Color.TRANSPARENT);
                    }
                }
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
            	if (GameModes.gameMode == GameModes.INVISIBLE) {
            		setRectangleData(0, displayMatrix[i][j]);
            	} else {
            		setRectangleData(board[i][j], displayMatrix[i][j]);
            	}
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    private void hardDrop(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onHardDropEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
    	score.bind(integerProperty);
    	scoreDisplay.getStyleClass().add("gameOverStyleFinal");
    	scoreDisplay.textProperty().bind(score.asString());
    }
    
    public void bindLevel(IntegerProperty integerProperty) {
    	level.bind(integerProperty);
    	levelDisplay.getStyleClass().add("gameOverStyleFinal");
    	levelDisplay.textProperty().bind(level.add(1).asString());
    }
    
    public void updateLines(int lines) {
    	linesDisplay.getStyleClass().add("gameOverStyleFinal");
    	linesDisplay.setText(String.valueOf(lines));
    }

    public void gameOver(boolean diedByOverflow) {
        timeLine.stop();
        timerTimeline.stop();
        if (GameModes.gameMode == GameModes.SPRINT) {
        	if (diedByOverflow) {
        		gameOverPanel.setScore(0);
        	} else {
        		gameOverPanel.setScore(timer);
        	}
        } else {
        	gameOverPanel.setScore(score.get());
        }
        isGameOver.setValue(Boolean.TRUE);
        gameOverPanel.setVisible(true);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        if (GameModes.gameMode == GameModes.TIME) {
        	timer = 20;
            timeDisplay.setText(String.valueOf(timer));
        	countdownTimer();
        } else if (GameModes.gameMode == GameModes.ULTRA) {
        	timer = 120;
            timeDisplay.setText(String.valueOf(timer));
        	countdownTimer();
        } else {
            timer = 0;
            timeDisplay.setText(String.valueOf(timer));
            startTimer();
        }
        gameOverPanel.setScore(0);
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }
    
    private void drawNextBrickPreview(Pane pane, Brick brick) {
    	pane.getChildren().removeIf(node -> node instanceof Rectangle);
    	
    	if (brick == null) {
    		return;
    	}
    	
    	int[][] shape = brick.getShapeMatrix().get(0);
    	int rows = shape.length;
    	int columns = shape[0].length;
    	double width = columns * BRICK_SIZE;
    	double height = rows * BRICK_SIZE;
    	double offsetX = (pane.getPrefWidth() - width) / 2;
    	double offsetY = (pane.getPrefHeight() - height) / 2;
    	

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setLayoutX((j * BRICK_SIZE) + offsetX);
                r.setLayoutY((i * BRICK_SIZE) + offsetY);
                setRectangleData(shape[i][j], r);
                pane.getChildren().add(r);
            }
        }
    }
    
    public void updateNextPreview(Brick brick) {
    	drawNextBrickPreview(nextBrickPane, brick);
    }
    
    public void updateHoldPreview(Brick brick) {
    	drawNextBrickPreview(holdBrickPane, brick);
    }

    public void pauseGame(ActionEvent actionEvent) {
    	if (isGameOver.getValue() == false) {
    		if (isPause.getValue() == false) {
    			isPause.setValue(true);
    			timeLine.pause();
    			timerTimeline.pause();
    			pausePane.setVisible(true);
    		} else {
    			isPause.setValue(false);
    			timeLine.play();
    			timerTimeline.play();
    			pausePane.setVisible(false);
    		}
    	}
        gamePanel.requestFocus();
    }
    
    public void returnToMainMenu() {
    	timeLine.stop();
    	GameStates.SetGameState(GameStates.MENU);
    	try {
			main.loadScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setMain(Main main) {
    	this.main = main;
    }
}

