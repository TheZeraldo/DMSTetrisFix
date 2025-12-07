package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;
import com.comp2042.model.ViewData;


/**
 * Implements the main game board logic.
 * Coordinates brick movement, holding, scoring, and row clearing.
 */
public class SimpleBoard implements Board {

    private final BrickManager brickManager;
    private final HoldManager holdManager;
    private final MoveManager moveManager;
    private final Score score;

    /**
     * Creates a new game board with a given width and height.
     *
     * @param width: the board width
     * @param height: the board height
     */
    public SimpleBoard(int width, int height) {
        this.brickManager = new BrickManager(width, height);
        this.holdManager = new HoldManager(brickManager);
        this.moveManager = new MoveManager();
        score = new Score();
    }

    /**
     * Moves the current brick down.
     *
     * @return true if the brick moved, false otherwise
     */
    @Override
    public boolean moveBrickDown() {
        return moveManager.moveDown(brickManager.getGameMatrix(), brickManager);
    }

    /**
     * Moves the current brick left.
     *
     * @return true if the brick moved, false otherwise
     */
    @Override
    public boolean moveBrickLeft() {
        return moveManager.moveLeft(brickManager.getGameMatrix(), brickManager);
    }

    /**
     * Moves the current brick right.
     *
     * @return true if the brick moved, false otherwise
     */
    @Override
    public boolean moveBrickRight() {
        return moveManager.moveRight(brickManager.getGameMatrix(), brickManager);
    }

    /**
     * Rotates the current brick.
     *
     * @return true if the brick rotated, false otherwise
     */
    @Override
    public boolean rotateLeftBrick() {
        return moveManager.rotateBrick(brickManager.getGameMatrix(), brickManager);
    }

    /**
     * Spawns a new brick at the top of the board.
     *
     * @return true if the new brick immediately collides (game over), false otherwise
     */
    @Override
    public boolean createNewBrick() {
        holdManager.setCanHold(true);
        return brickManager.createNewBrick();
    }

    /**
     * Gets the current board matrix.
     *
     * @return the board matrix
     */
    @Override
    public int[][] getBoardMatrix() {
        return brickManager.getGameMatrix();
    }

    /**
     * Gets the current view data for rendering.
     *
     * @return the ViewData object
     */
    @Override
    public ViewData getViewData() {
        return brickManager.getViewData();
    }

    /**
     * Merges the active brick into the board.
     */
    @Override
    public void mergeBrickToBackground() {
        brickManager.mergeBrickToBackground();
    }


    /**
     * Clears any full rows.
     *
     * @return information about cleared rows
     */
    @Override
    public ClearRow clearRows() {
        return brickManager.clearRows();
    }

    /**
     * Gets the landing y position of brick.
     * 
     * @return landing y position of brick
     */
	@Override
	public int getLandingYPosition() {
		return brickManager.getLandingYPosition();
	}

    /**
     * Performs the hold action
     */
	@Override
	public void holdBrick() {
		holdManager.hold();
	}

    /**
     * Resets everything and starts new game.
     */
    @Override
    public void newGame() {
    	brickManager.newGame();
        score.reset();
        holdManager.reset();
    }

    /**
     * Gets the current score object.
     *
     * @return the Score instance
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Gets next brick in generated brick queue.
     * 
     * @return next brick in queue
     */
	@Override
	public Brick getNextBrick() {
		return brickManager.getNextBrick();
	}

    /**
     * Gets the currently held brick.
     *
     * @return held brick, null if none are held
     */
	@Override
	public Brick getHeldbrick() {
		return holdManager.getHeldBrick();
	}

    /**
     * Checks whether can hold brick.
     *
     * @return boolean value, true if can hold, false if cannot
     */
	@Override
	public boolean canHold() {
		return holdManager.canHold();
	}
}
