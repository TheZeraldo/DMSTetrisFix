package com.comp2042.logic.bricks;

import com.comp2042.logic.game.MatrixOperations;
import com.comp2042.utils.GameModes;

import java.util.ArrayList;
import java.util.List;

final class ZBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
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
