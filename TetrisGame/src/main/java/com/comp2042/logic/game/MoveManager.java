package com.comp2042.logic.game;

import java.awt.Point;

import com.comp2042.model.NextShapeInfo;

public class MoveManager {

    public boolean moveDown(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, 0, 1);
    }

    public boolean moveLeft(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, -1, 0);
    }

    public boolean moveRight(int[][] matrix, BrickManager brickManager) {
    	return move(matrix, brickManager, 1, 0);
    }
    
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
