package com.comp2042.model;

import com.comp2042.logic.game.MatrixOperations;


/**
 * Stores information about cleared rows after a brick is placed.
 * This class contains the number of lines removed, the updated board matrix, and the score bonus awarded for the clear.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Creates a ClearRow result object.
     *
     * @param linesRemoved: the number of rows that were cleared
     * @param newMatrix: the updated game board matrix after clearing rows
     * @param scoreBonus: the bonus score awarded for clearing rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of rows that were cleared.
     *
     * @return the number of cleared rows
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the updated board matrix.
     *
     * @return a copy of the new game matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus awarded for clearing rows.
     *
     * @return the score bonus value
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
