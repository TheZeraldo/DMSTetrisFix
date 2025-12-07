package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;
import com.comp2042.model.ViewData;


/**
 * Represents the game board and core game logic.
 * Handles brick movement, rotation, collision detection, scoring, and game state.
 * Classes that implement this interface handle all board actions, events, and interactions.
 */
public interface Board {

    /**
     * Moves the current brick down by one row.
     *
     * @return true if the brick moved successfully, false if a collision occurred
     */
    boolean moveBrickDown();

    /**
     * Moves the current brick one column to the left.
     *
     * @return true if the brick moved successfully, false if movement was blocked
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick one column to the right.
     *
     * @return true if the brick moved successfully, false if movement was blocked
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick to the next orientation.
     *
     * @return true if the rotation was successful, false if rotation was blocked
     */
    boolean rotateLeftBrick();

    /**
     * Creates and spawns a new brick at the starting position.
     *
     * @return true if the new brick collides immediately (game over condition)
     */
    boolean createNewBrick();

    /**
     * Gets the current board matrix, including fixed bricks.
     *
     * @return a 2D array representing the board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets the current visual data for rendering the active brick.
     *
     * @return a ViewData object containing shape and position information
     */
    ViewData getViewData();


    /**
     * Merges the active brick into the background board matrix.
     */
    void mergeBrickToBackground();

    /**
     * Clears any completed rows from the board.
     *
     * @return a ClearRow object containing information about removed rows and score
     */
    ClearRow clearRows();

    /**
     * Gets the current score data.
     *
     * @return the current Score object
     */
    Score getScore();

    /**
     * Resets the game board and starts a new game.
     */
    void newGame();
    
    /**
     * Calculates the Y position where the current brick would land.
     *
     * @return the landing Y co-ordinates
     */
    int getLandingYPosition();
    

    /**
     * Gets the next brick in the queue.
     *
     * @return the next Brick
     */
    Brick getNextBrick();
    
    /**
     * Checks whether the player is allowed to hold a brick.
     *
     * @return true if holding is allowed, false otherwise
     */
    boolean canHold();
    
    /**
     * Stores the current brick in the hold slot.
     */
    void holdBrick();
    
    /**
     * Gets the currently held brick.
     *
     * @return the held Brick, or null if no held brick
     */
    Brick getHeldbrick();
}
