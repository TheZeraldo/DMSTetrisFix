package com.comp2042.logic.bricks;

import com.comp2042.logic.game.MatrixOperations;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the O-shaped brick.
 * Provides all rotation states of the brick as 2D matrices.
 * Supports scaling when Big Mode is active.
 */
final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new O-shaped brick and initializes its rotation states.
     */
    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
        
        if (GameModeManager.getGameMode() == GameModes.BIG) {
        	List<int[][]> temp = MatrixOperations.bigModeList(brickMatrix);
        	brickMatrix.clear();
        	brickMatrix.addAll(temp);
        }
    }

    /**
     * Returns a copy of all rotation states for this brick.
     *
     * @return a list of 2D matrices representing the OBrick shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
