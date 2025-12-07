package com.comp2042.logic.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import com.comp2042.model.ClearRow;


/**
 * Utility class providing matrix operations for the game board.
 * Includes collision detection, merging bricks, copying matrices, clearing rows, and applying big mode.
 */
public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    /**
     * Checks whether a brick intersects with the board or boundaries.
     *
     * @param matrix: the current game board matrix
     * @param brick: the brick matrix
     * @param x: the x position of the brick
     * @param y: the y position of the brick
     * @return true if there is a collision, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a brick goes out of bounds of the game board.
     *
     * @param matrix: the current game board matrix
     * @param targetX: the x position of the brick if action is done
     * @param targetY: the y position of the brick if action is done
     * @return true if out of bounds, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Creates a copy of a matrix.
     *
     * @param original: the original matrix
     * @return a new copied matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into the background matrix.
     *
     * @param filledFields: the current board matrix
     * @param brick: the shape of the brick
     * @param x: the x position
     * @param y: the y position
     * @return a new matrix with the brick merged in
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Removes full rows from the board and returns the result.
     *
     * @param matrix: the current board matrix
     * @return a ClearRow object containing the updated matrix and data about cleared rows
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }
    
    /**
     * Scales a shape to "big mode" by doubling its size.
     *
     * @param shape: the original shape matrix
     * @return the enlarged shape matrix
     */
    public static int[][] applyBigMode(int[][] shape) {
    	int rows = shape.length;
    	int columns = shape[0].length;
    	int[][] bigShape = new int[rows*2][columns*2];
    	
    	for (int i = 0; i < rows; i++) {
    		for (int j = 0; j < columns; j++) {
    			int value = shape[i][j];
    			bigShape[i * 2][j * 2] = value;
    			bigShape[i * 2 + 1][j * 2] = value;
    			bigShape[i * 2][j * 2 + 1] = value;
    			bigShape[i * 2 + 1][j * 2 + 1] = value;
    		}
    	}
    		
    	return bigShape;
    }
    
    /**
     * Applies big mode transformation to a list of shapes.
     * Applies big mode to all rotations.
     *
     * @param shapeList: list of original shape matrices
     * @return list of enlarged shape matrices
     */
    public static List<int[][]> bigModeList(List<int[][]> shapeList) {
    	List<int[][]> bigList = new ArrayList<>();
    	for (int[][] shape : shapeList) {
    		bigList.add(applyBigMode(shape));
    	}
    	return bigList;
    }

    /**
     * Creates a deep copy of a list of matrices.
     *
     * @param list: the list to copy
     * @return a new list containing copied matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
