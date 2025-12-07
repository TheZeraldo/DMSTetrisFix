package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.NextShapeInfo;


/**
 * Handles rotation logic for a brick.
 * Tracks the current rotation state and provides the next rotation.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;


    /**
     * Calculates and returns the next rotation shape without applying it.
     *
     * @return next shape and its position
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the currently active shape matrix.
     *
     * @return the current shape as a 2D array
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }


    /**
     * Sets the current rotation index.
     *
     * @param shape: rotation index to set to
     */
    public void setCurrentShape(int shape) {
        this.currentShape = shape;
    }

    /**
     * Sets the current brick and resets its rotation.
     *
     * @param brick: the brick to set to
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
    
    /**
     * Gets the current brick.
     *
     * @return the current Brick
     */
    public Brick getBrick() {
    	return brick;
    }
}
