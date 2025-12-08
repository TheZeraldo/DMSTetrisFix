package com.comp2042.logic.game;

import java.awt.Point;

import com.comp2042.model.NextShapeInfo;


/**
 * Handles movement and rotation of bricks on the game board.
 * Performs collision checks before applying any movement.
 */
public class MoveManager {

    /**
     * Moves the current brick down by one row if possible.
     *
     * @param matrix: the current game board
     * @param brickManager: the BrickManager controlling the active brick
     * @return true if the move succeeded, false otherwise
     */
    public boolean moveDown(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, 0, 1);
    }

    /**
     * Moves the current brick left by one column if possible.
     *
     * @param matrix: the current game board
     * @param brickManager: the BrickManager controlling the active brick
     * @return true if the move succeeded, false otherwise
     */
    public boolean moveLeft(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, -1, 0);
    }

    /**
     * Moves the current brick right by one column if possible.
     *
     * @param matrix: the current game board
     * @param brickManager: the BrickManager controlling the active brick
     * @return true if the move succeeded, false otherwise
     */
    public boolean moveRight(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, 1, 0);
    }
    
    /**
     * Rotates the current brick if possible.
     *
     * @param matrix: the current game board
     * @param brickManager: the BrickManager controlling the active brick
     * @return true if the rotation succeeded, false otherwise
     */
    public boolean rotateBrick(int[][] matrix, BrickManager brickManager) {
        int[][] currentMatrix = MatrixOperations.copy(matrix);
        NextShapeInfo nextShape = brickManager.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) brickManager.getOffset().getX(), (int) brickManager.getOffset().getY());
        if (conflict) {
            return false;
        } else {
            brickManager.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }
    
    /**
     * Moves the current brick if possible.
     *
     * @param matrix: the current game board
     * @param brickManager: the BrickManager controlling the active brick
     * @param x: x value to translate by
     * @param y: y value to translate by
     * @return true if the rotation succeeded, false otherwise
     */
    private boolean move(int[][] matrix, BrickManager brickManager, int x, int y) {
        int[][] currentMatrix = MatrixOperations.copy(matrix);
        Point p = new Point(brickManager.getOffset());
        p.translate(x, y);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickManager.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            brickManager.setOffset(p);
            return true;
        }
    }
}
