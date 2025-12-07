package com.comp2042.ui.managers;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.MoveEvent;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ViewData;
import com.comp2042.ui.controllers.GuiController;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 * Handles all visual rendering of bricks, ghost pieces, and the game board.
 * This class updates brick positions, colors, falling speed, and previews.
 * It communicates with the GUI controller to trigger movement and fall events.
 */
public class BrickRenderer {
	private static final int BRICK_SIZE = 20;
	
	private final GuiController guiController;
	private final GridPane gamePanel;
	private final GridPane brickPanel;
	private final GridPane ghostPanel;
    private Timeline fallTimeline;
    private Rectangle[][] rectangles;
    private Rectangle[][] ghostRectangles;
    private Rectangle[][] displayMatrix;
	
    /**
     * Creates a new BrickRenderer responsible for drawing bricks and board cells.
     *
     * @param guiController: the controller used to trigger game actions
     * @param gamePanel: the grid displaying the background board
     * @param brickPanel: the grid used to display the active falling brick
     * @param ghostPanel: the grid used to display the ghost brick
     */
	public BrickRenderer(GuiController guiController, GridPane gamePanel, GridPane brickPanel, GridPane ghostPanel) {
		this.guiController = guiController;
		this.gamePanel = gamePanel;
		this.brickPanel = brickPanel;
		this.ghostPanel = ghostPanel;
	}
	
    /**
     * Initializes the board display and loads the first brick and ghost piece.
     *
     * @param boardMatrix: the background game matrix
     * @param brick: the view data of the currently active brick
     */
	public void init(int[][] boardMatrix, ViewData brick) {
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
        brickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getXPosition() * brickPanel.getVgap() + brick.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getYPosition() * brickPanel.getHgap() + brick.getYPosition() * BRICK_SIZE);

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
                ghostPanel.add(rectangle, j, i);
            }
        }
        ghostPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getXPosition() * ghostPanel.getVgap() + brick.getXPosition() * BRICK_SIZE);
        ghostPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getLandingYPosition() * ghostPanel.getHgap() + brick.getLandingYPosition() * BRICK_SIZE);
	}

    /**
     * Updates the automatic fall speed of the brick by stopping the timeline and creating a new one.
     *
     * @param speed: the fall delay in milliseconds
     */
    public void updateFallSpeed(int speed) {
    	//stops old timeline and starts new one with new speed
    	if (fallTimeline != null) {
        	fallTimeline.stop();
    	}
    	
    	Runnable moveDownRunnable = () -> guiController.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
    	
    	if (GameModeManager.getGameMode() == GameModes.HARDCORE) {
	        fallTimeline = new Timeline(new KeyFrame(
	                Duration.millis(100),
	                ae -> moveDownRunnable.run()
	        ));
    	} else {
	        fallTimeline = new Timeline(new KeyFrame(
	                Duration.millis(speed),
	                ae -> moveDownRunnable.run()
	        ));
    	}
        fallTimeline.setCycleCount(Timeline.INDEFINITE);
        fallTimeline.play();
	}

    /**
     * Updates the on screen position and shape of the active brick and ghost brick.
     *
     * @param brick: the current brick view data
     */
    public void refreshBrick(ViewData brick) {
        brickPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getXPosition() * brickPanel.getVgap() + brick.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getYPosition() * brickPanel.getHgap() + brick.getYPosition() * BRICK_SIZE);

        ghostPanel.setLayoutX(200 + gamePanel.getLayoutX() + brick.getXPosition() * ghostPanel.getVgap() + brick.getXPosition() * BRICK_SIZE);
        ghostPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getLandingYPosition() * ghostPanel.getHgap() + brick.getLandingYPosition() * BRICK_SIZE);
        
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

    /**
     * Updates the background game grid by redrawing all board cells.
     *
     * @param board: the current board matrix
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
            	if (GameModeManager.getGameMode() == GameModes.INVISIBLE) {
            		setRectangleData(0, displayMatrix[i][j]);
            	} else {
            		setRectangleData(board[i][j], displayMatrix[i][j]);
            	}
            }
        }
    }

    /**
     * Sets the data for drawing the rectangle.
     *
     * @param color: color of the brick
     * @param rectangle: the rectangle whose data is being updated
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
    
    /**
     * Draws a preview of the brick in the given pane.
     *
     * @param pane: the UI container to draw into
     * @param brick: the brick whose preview should be rendered
     */
    public void drawBrickPreview(Pane pane, Brick brick) {
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

    /**
     * Returns a fill color based on a brick's numeric ID.
     *
     * @param i: the numeric color index
     * @return the corresponding Paint color
     */
	public Paint getFillColor(int i) {
		Paint[] colors = {
				Color.TRANSPARENT,
				Color.AQUA,
				Color.BLUEVIOLET,
				Color.DARKGREEN,
				Color.YELLOW,
				Color.RED,
				Color.BEIGE,
				Color.BURLYWOOD,
				Color.WHITE
		};
		if (i < 0 || i > colors.length) {
			return Color.WHITE;
		}
        return colors[i];
    }

    /**
     * Pause the fall timeline of bricks and reverse the value of the isPaused boolean.
     *
     * @param isPaused: the paused/not paused property
     */
    public void togglePause(BooleanProperty isPaused) {
		if (isPaused.getValue() == false) {
			fallTimeline.pause();
		} else {
			fallTimeline.play();
		}
		isPaused.set(!isPaused.get());
    }
    
    /**
     * Stops the fall timeline.
     */
    public void stopFallTimeline() {
    	fallTimeline.stop();
    }
    
    /**
     * Starts or resumes the fall timeline.
     */
    public void startFallTimeline() {
    	fallTimeline.play();
    }
}
