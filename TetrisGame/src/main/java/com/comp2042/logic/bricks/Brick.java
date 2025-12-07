package com.comp2042.logic.bricks;

import java.util.List;


/**
 * Represents a game brick and its possible rotation states.
 * Classes that implement this interface provide the shape data used to render and place bricks.
 */
public interface Brick {

    /**
     * Returns all rotation states of the brick as matrices.
     *
     * @return a list of 2D integer arrays representing the brick's shape
     */
    List<int[][]> getShapeMatrix();
}
