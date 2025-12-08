package com.comp2042.model;

import com.comp2042.logic.game.MatrixOperations;


/**
 * Stores information about the next rotation state of a brick.
 * This includes the rotated shape matrix and its rotation index.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Creates a NextShapeInfo object.
     *
     * @param shape: the matrix representing the rotated brick shape
     * @param position: the index of the rotation state
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the rotated brick shape matrix.
     *
     * @return a copy of the shape matrix
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the rotation position index.
     *
     * @return the rotation index
     */
    public int getPosition() {
        return position;
    }
}
