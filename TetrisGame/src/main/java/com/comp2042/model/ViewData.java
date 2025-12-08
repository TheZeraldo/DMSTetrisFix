package com.comp2042.model;

import com.comp2042.logic.game.MatrixOperations;


/**
 * Represents all data required to render the current game state.
 * This includes the active brick shape, its position, the next brick preview, and the predicted landing position (for ghost brick).
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private int landingYPosition;

    /**
     * Creates a ViewData object containing rendering information.
     *
     * @param brickData: the matrix of the active brick
     * @param xPosition: the x co-ordinate of the active brick
     * @param yPosition: the y co-ordinate of the active brick
     * @param nextBrickData: the matrix of the next brick preview
     * @param landingYPosition: the predicted landing y position of the active brick
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int landingYPosition) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.landingYPosition = landingYPosition;
    }

    /**
     * Gets a copy of the active brick matrix.
     *
     * @return a copy of the brick data
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the x position of the active brick.
     *
     * @return the x co-ordinate of the brick
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Returns the y position of the active brick.
     *
     * @return the y co-ordinate of the brick
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Gets a copy of the next brick preview matrix.
     *
     * @return a copy of the next brick data
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
    
    /**
     * Gets the predicted landing y position of the active brick.
     *
     * @return the landing y position
     */
    public int getLandingYPosition() {
    	return landingYPosition;
    }
}
