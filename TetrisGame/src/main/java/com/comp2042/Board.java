package com.comp2042;

import com.comp2042.logic.bricks.Brick;

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
