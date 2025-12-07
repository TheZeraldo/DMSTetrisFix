package com.comp2042.ui.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.main.GameStates;
import com.comp2042.main.Main;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.managers.BrickRenderer;
import com.comp2042.ui.managers.ScoreManager;
import com.comp2042.ui.managers.TimerManager;
import com.comp2042.ui.panels.GameOverPanel;
import com.comp2042.ui.panels.NotificationPanel;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;
import com.comp2042.utils.KeyBinds;


/**
 * Controller class for the main game GUI.
 * Handles user input, updates the game view, and coordinates UI components, such as score display, timers, brick rendering, and game over panel.
 */
public class GuiController implements Initializable {

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
    
    @FXML
    private Label quitLabel;
    
    @FXML
    private Label pauseLabel;
    
    @FXML
    private Label restartLabel;
    
    @FXML
    private Label holdLabel;
    
    @FXML
    private Label rotateLabel;
    
    @FXML
    private Label leftLabel;
    
    @FXML
    private Label rightLabel;
    
    @FXML
    private Label softDropLabel;
    
    @FXML
    private Label hardDropLabel;
    
    private Main main;
    
    private TimerManager timerManager;
    
    private ScoreManager scoreManager;
    
    private BrickRenderer brickRenderer;

    private InputEventListener eventListener;

    private final BooleanProperty isPaused = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /**
     * Initializes the UI components, key handlers, fonts, and game managers.
     *
     * @param location: the location of the FXML file
     * @param resources: the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPaused.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyBinds.getKey("Move Left")) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyBinds.getKey("Move Right")) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyBinds.getKey("Rotate")) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyBinds.getKey("Soft Drop")) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyBinds.getKey("Hard Drop")) {
                        hardDrop(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyBinds.getKey("Hold")) {
                        refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                }
                if (isGameOver.getValue() == Boolean.FALSE) {
                	if (keyEvent.getCode() == KeyBinds.getKey("Pause")) {
                		pauseGame(null);
                        keyEvent.consume();
                    }
                }
            	if (keyEvent.getCode() == KeyBinds.getKey("Main Menu")) {
            		returnToMainMenu();
                    keyEvent.consume();
                }
                if (keyEvent.getCode() == KeyBinds.getKey("New Game")) {
                    newGame(null);
                }
            }
        });
        timerManager = new TimerManager(timeDisplay, this);
        scoreManager = new ScoreManager();
        brickRenderer = new BrickRenderer(this, gamePanel, brickPanel, ghostBrickPanel);
        updateControlsPane();
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    /**
     * Initializes the game view with the board and current brick state.
     *
     * @param boardMatrix: the initial game board matrix
     * @param brick: the initial view data for the active brick
     */
	public void initGameView(int[][] boardMatrix, ViewData brick) {
		if (GameModeManager.getGameMode() == GameModes.SPRINT) {
        	scoreVBox.setVisible(false);
        	levelVBox.setVisible(false);
        } else {
			scoreVBox.setVisible(true);
        	levelVBox.setVisible(true);
		}
		brickRenderer.init(boardMatrix, brick);
        startTime();
        updateFallSpeed(400);
    }
	
    /**
     * Starts the timer depending on the game mode.
     */
	private void startTime() {
		switch (GameModeManager.getGameMode()) {
			case TIME -> timerManager.startCountdown(20);
			case ULTRA -> timerManager.startCountdown(120);
			default -> timerManager.startTimer();
		}
	}

    /**
     * Updates the falling speed of the active brick.
     *
     * @param speed: the new fall speed in milliseconds
     */
    public void updateFallSpeed(int speed) {
    	brickRenderer.updateFallSpeed(speed);
	}
    
    /**
     * Increases the game timer by the specified amount of time.
     *
     * @param time: the amount of time to add in seconds
     */
    public void increaseTimer(int time) {
    	timerManager.increaseTimer(time);
    }

    /**
     * Refreshes the position and appearance of the current brick.
     *
     * @param brick: the updated view data for the brick
     */
    private void refreshBrick(ViewData brick) {
        if (isPaused.getValue() == Boolean.FALSE) {
        	brickRenderer.refreshBrick(brick);
        }
    }

    /**
     * Updates the background game board with the latest board state.
     *
     * @param board: the updated board matrix
     */
    public void refreshGameBackground(int[][] board) {
    	brickRenderer.refreshGameBackground(board);
    }

    /**
     * Updates the controls help panel to reflect current key bindings.
     */
    private void updateControlsPane() {
		quitLabel.setText(KeyBinds.getKey("Main Menu") + " - Quit Game");
		pauseLabel.setText(KeyBinds.getKey("Pause") + " - Pause Game");
		restartLabel.setText(KeyBinds.getKey("New Game") + " - Restart Game");
		holdLabel.setText(KeyBinds.getKey("Hold") + " - Hold Piece");
		rotateLabel.setText(KeyBinds.getKey("Rotate") + " - Rotate Piece");
		leftLabel.setText(KeyBinds.getKey("Move Left") + " - Move Left");
		rightLabel.setText(KeyBinds.getKey("Move Right") + " - Move Right");
		softDropLabel.setText(KeyBinds.getKey("Soft Drop") + " - Soft Drop");
		hardDropLabel.setText(KeyBinds.getKey("Hard Drop") + " - Hard Drop");
	}

    /**
     * Moves the active brick down.
     * Also handles row clearing and score notifications.
     *
     * @param event: the movement event
     */
    public void moveDown(MoveEvent event) {
        if (isPaused.getValue() == Boolean.FALSE) {
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

    /**
     * Performs a hard drop, instantly placing the brick at the landing position.
     * Also handles row clearing and score notifications.
     *
     * @param event: the movement event
     */
    private void hardDrop(MoveEvent event) {
        if (isPaused.getValue() == Boolean.FALSE) {
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

    /**
     * Sets the event listener responsible for handling input events.
     *
     * @param eventListener: the input event listener to register
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds the score display to the provided score property.
     *
     * @param integerProperty: the score property to bind to the UI
     */
    public void bindScore(IntegerProperty integerProperty) {
    	scoreManager.getScore().bind(integerProperty);
    	scoreDisplay.getStyleClass().add("gameOverStyleFinal");
    	scoreDisplay.textProperty().bind(scoreManager.getScore().asString());
    }
    
    /**
     * Binds the level display to the provided level property.
     *
     * @param integerProperty: the level property to bind to the UI
     */
    public void bindLevel(IntegerProperty integerProperty) {
		scoreManager.getLevel().bind(integerProperty);
    	levelDisplay.getStyleClass().add("gameOverStyleFinal");
    	levelDisplay.textProperty().bind(scoreManager.getLevel().add(1).asString());
    }
    
    /**
     * Updates the displayed number of remaining lines.
     *
     * @param lines: the number of lines to display
     */
    public void updateLines(int lines) {
    	if (lines < 0) {
    		lines = 0;
    	}
    	linesDisplay.getStyleClass().add("gameOverStyleFinal");
    	linesDisplay.setText(String.valueOf(lines));
    }

    /**
     * Handles the game over state and displays the game over panel.
     *
     * @param diedByOverflow: true if the game ended due to block overflow, false otherwise
     */
    public void gameOver(boolean diedByOverflow) {
        brickRenderer.stopFallTimeline();
        timerManager.gameOver(diedByOverflow);
		if (GameModeManager.getGameMode() != GameModes.SPRINT) {
			gameOverPanel.setScore(scoreManager.getScore().get());
		}
        isGameOver.setValue(Boolean.TRUE);
        gameOverPanel.setVisible(true);
    }

    /**
     * Starts a new game and resets the game state and UI.
     *
     * @param actionEvent: the event that triggered the new game action
     */
    public void newGame(ActionEvent actionEvent) {
        brickRenderer.stopFallTimeline();
        startTime();
		gameOverPanel.setScore(0);
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        brickRenderer.startFallTimeline();
        isPaused.setValue(Boolean.FALSE);
		pausePane.setVisible(false);
        isGameOver.setValue(Boolean.FALSE);
    }
    
    /**
     * Draws a preview of the specified brick in the given pane.
     *
     * @param pane: the pane to draw the preview in
     * @param brick: the brick to preview
     */
    private void drawBrickPreview(Pane pane, Brick brick) {
    	brickRenderer.drawBrickPreview(pane, brick);
    }
    
    /**
     * Updates the next brick preview panel.
     *
     * @param brick: the next brick to display
     */
    public void updateNextPreview(Brick brick) {
    	drawBrickPreview(nextBrickPane, brick);
    }
    
    /**
     * Updates the held brick preview panel.
     *
     * @param brick: the held brick to display
     */
    public void updateHoldPreview(Brick brick) {
    	drawBrickPreview(holdBrickPane, brick);
    }

    /**
     * Toggles the paused state of the game and updates the UI accordingly.
     *
     * @param actionEvent: the event that triggered the pause action
     */
    public void pauseGame(ActionEvent actionEvent) {
    	if (isGameOver.getValue() == false) {
    		if (isPaused.getValue() == false) {
    			pausePane.setVisible(true);
    		} else {
    			pausePane.setVisible(false);
    		}
    		brickRenderer.togglePause(isPaused);
			timerManager.togglePause(isPaused);
    		isPaused.set(!isPaused.get());
    	}
        gamePanel.requestFocus();
    }
    
    /**
     * Returns the user to the main menu and stops the game loop.
     */
    public void returnToMainMenu() {
    	brickRenderer.stopFallTimeline();
    	GameStates.SetGameState(GameStates.MENU);
    	try {
			main.loadScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Sets the main application reference for scene loading.
     *
     * @param main: the main application instance
     */
    public void setMain(Main main) {
    	this.main = main;
    }
    
    /**
     * Gets the game over panel instance.
     *
     * @return the game over panel
     */
    public GameOverPanel getGameOverPanel() {
    	return gameOverPanel;
    }
}

