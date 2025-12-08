package com.comp2042.logic.game;

import java.awt.Point;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.model.ClearRow;
import com.comp2042.model.NextShapeInfo;
import com.comp2042.model.ViewData;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;


/**
 * Manages the current falling brick and its interaction with the game board.
 * Handles spawning, movement offsets, merging, row clearing, and landing position prediction.
 */
public class BrickManager {
	private final int width;
	private final int height;
	
	private final BrickGenerator brickGenerator;
	private final BrickRotator brickRotator;
    private Point currentOffset;
    private int[][] currentGameMatrix;
	

    /**
     * Creates a new BrickManager.
     *
     * @param width: the width of the board
     * @param height: the height of the board
     */
	public BrickManager(int width, int height) {
		this.width = width;
		this.height = height;
		brickGenerator = new RandomBrickGenerator();
		brickRotator = new BrickRotator();
        currentGameMatrix = new int[width][height];
	}
	
    /**
     * Spawns a new brick and resets its position.
     *
     * @return true if the new brick immediately collides with the board (game over condition)
     */
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        resetOffset();
        
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Gets visual data for the current falling brick and preview.
     *
     * @return a ViewData object containing brick shape and positions
     */
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0), getLandingYPosition());
    }
    

    /**
     * Merges the current brick into the background matrix.
     */
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    
    /**
     * Clears full rows from the board.
     *
     * @return information about cleared rows
     */
    public ClearRow clearRows() {
        ClearRow cleared = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = cleared.getNewMatrix();
        return cleared;
    }

    /**
     * Calculates the Y-coordinate where the current brick would land.
     *
     * @return the predicted landing Y-position
     */
	public int getLandingYPosition() {
		int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
		int landingY = (int) currentOffset.getY();
		
		while (true) {
			boolean collision = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), landingY + 1);
			
			if (collision) {
				break;
			} else {
				landingY++;
			}
		}
		return landingY;
	}

    /**
     * Resets the game and creates a new brick.
     */
    public void newGame() {
        currentGameMatrix = new int[width][height];
        createNewBrick();
    }
    
    /**
     * Gets the current game matrix.
     *
     * @return the current game matrix
     */
    public int[][] getGameMatrix() {
    	return currentGameMatrix;
    }
    

    /**
     * Resets the brick's starting position based on the game mode.
     */
    public void resetOffset() {
        if (GameModeManager.getGameMode() == GameModes.BIG) {
            currentOffset = new Point(2, 1);
        } else {
            currentOffset = new Point(4, 1);
        }
    }
    

    /**
     * Sets the current position offset of the active brick.
     *
     * @param offset: the new position offset
     */
    public void setOffset(Point offset) {
    	currentOffset = offset;
    }
    
    /**
     * Gets the current position offset of the active brick.
     *
     * @return the current offset
     */
    public Point getOffset() {
    	return currentOffset;
    }

    /**
     * Gets the next brick in the generated bricks queue.
     *
     * @return the next Brick
     */
	public Brick getNextBrick() {
		return brickGenerator.getNextBrick();
	}
    
    /**
     * Sets the current active brick.
     *
     * @param brick: the brick to set as active
     */
    public void setBrick(Brick brick) {
    	brickRotator.setBrick(brick);
    }
    
    /**
     * Gets the currently active brick.
     *
     * @return the active Brick
     */
    public Brick getBrick() {
    	return brickRotator.getBrick();
    }
    

    /**
     * Sets the rotation index of the current brick.
     *
     * @param shape: the shape rotation index to set to
     */
    public void setCurrentShape(int shape) {
    	brickRotator.setCurrentShape(shape);
    }
    
    /**
     * Gets the current shape matrix of the active brick.
     *
     * @return a 2D matrix of the current brick rotation
     */
    public int[][] getCurrentShape() {
    	return brickRotator.getCurrentShape();
    }
    
    /**
     * Gets information about the next rotation shape.
     *
     * @return next shape rotation and position
     */
    public NextShapeInfo getNextShape() {
    	return brickRotator.getNextShape();
    }
}
