package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;
import com.comp2042.model.ViewData;

public class SimpleBoard implements Board {

    private final BrickManager brickManager;
    private final HoldManager holdManager;
    private final MoveManager moveManager;
    private final Score score;

    public SimpleBoard(int width, int height) {
        this.brickManager = new BrickManager(width, height);
        this.holdManager = new HoldManager(brickManager);
        this.moveManager = new MoveManager();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        return moveManager.moveDown(brickManager.getGameMatrix(), brickManager);
    }


    @Override
    public boolean moveBrickLeft() {
        return moveManager.moveLeft(brickManager.getGameMatrix(), brickManager);
    }

    @Override
    public boolean moveBrickRight() {
        return moveManager.moveRight(brickManager.getGameMatrix(), brickManager);
    }

    @Override
    public boolean rotateLeftBrick() {
        return moveManager.rotateBrick(brickManager.getGameMatrix(), brickManager);
    }

    @Override
    public boolean createNewBrick() {
        holdManager.setCanHold(true);
        return brickManager.createNewBrick();
    }

    @Override
    public int[][] getBoardMatrix() {
        return brickManager.getGameMatrix();
    }

    @Override
    public ViewData getViewData() {
        return brickManager.getViewData();
    }

    @Override
    public void mergeBrickToBackground() {
        brickManager.mergeBrickToBackground();
    }

    @Override
    public ClearRow clearRows() {
        return brickManager.clearRows();
    }

	@Override
	public int getLandingYPosition() {
		return brickManager.getLandingYPosition();
	}

	@Override
	public void holdBrick() {
		holdManager.hold();
	}


    @Override
    public void newGame() {
    	brickManager.newGame();
        score.reset();
        holdManager.reset();
    }

    @Override
    public Score getScore() {
        return score;
    }

	@Override
	public Brick getNextBrick() {
		return brickManager.getNextBrick();
	}

	@Override
	public Brick getHeldbrick() {
		return holdManager.getHeldBrick();
	}

	@Override
	public boolean canHold() {
		return holdManager.canHold();
	}
}
