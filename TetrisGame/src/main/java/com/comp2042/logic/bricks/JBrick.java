package com.comp2042.logic.bricks;

import com.comp2042.logic.game.MatrixOperations;
import com.comp2042.utils.GameModes;

import java.util.ArrayList;
import java.util.List;

final class JBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public JBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });
        
        if (GameModes.gameMode == GameModes.BIG) {
        	List<int[][]> temp = MatrixOperations.bigModeList(brickMatrix);
        	brickMatrix.clear();
        	brickMatrix.addAll(temp);
        }
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
