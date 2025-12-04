package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;
import com.comp2042.model.ViewData;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();
    
    int getLandingYPosition();
    
    Brick getNextBrick();

    Score getScore();

    void newGame();
    
    boolean canHold();
    
    void holdBrick();
    
    Brick getHeldbrick();
}
