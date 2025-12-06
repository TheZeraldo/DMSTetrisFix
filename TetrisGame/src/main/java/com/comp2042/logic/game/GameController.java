package com.comp2042.logic.game;

import com.comp2042.events.EventSource;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.ClearRow;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.controllers.GuiController;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController guiController;

    public GameController(GuiController c) {
        guiController = c;
        board.createNewBrick();
        guiController.setEventListener(this);
        guiController.initGameView(board.getBoardMatrix(), board.getViewData());
        guiController.bindScore(board.getScore().scoreProperty());
        guiController.bindLevel(board.getScore().levelProperty());
        guiController.updateLines(board.getScore().linesLeft());
        guiController.updateNextPreview(board.getNextBrick());
        guiController.updateHoldPreview(board.getHeldbrick());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
            	if (GameModeManager.getGameMode() == GameModes.TIME) {
            		guiController.increaseTimer(clearRow.getLinesRemoved() * 5);
            	}
                board.getScore().add(clearRow.getScoreBonus());
                board.getScore().addLines(clearRow.getLinesRemoved());
                if (GameModeManager.getGameMode() == GameModes.SPRINT && board.getScore().linesLeft() == 0) {
                    guiController.gameOver(false);
                }
                guiController.updateLines(board.getScore().linesLeft());
                guiController.updateFallSpeed(board.getScore().getFallSpeed());
            }
            if (board.createNewBrick()) {
                guiController.gameOver(true);
            }

            guiController.updateNextPreview(board.getNextBrick());
            guiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

	@Override
	public DownData onHardDropEvent(MoveEvent event) {
		//Move brick down until it can't move anymore
		while (board.moveBrickDown()) {
			//award double score for hard drop
			board.getScore().add(2);
		}
		board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
        	if (GameModeManager.getGameMode() == GameModes.TIME) {
        		guiController.increaseTimer(clearRow.getLinesRemoved() * 10);
        	}
            board.getScore().add(clearRow.getScoreBonus());
            board.getScore().addLines(clearRow.getLinesRemoved());
            board.getScore().addLines(10);
            if (GameModeManager.getGameMode() == GameModes.SPRINT && board.getScore().linesLeft() <= 0) {
                guiController.gameOver(false);
            } else {
            	guiController.updateFallSpeed(board.getScore().getFallSpeed());
            }
        	guiController.updateLines(board.getScore().linesLeft());
        }
        if (board.createNewBrick()) {
            guiController.gameOver(true);
        }

        guiController.updateNextPreview(board.getNextBrick());
        guiController.refreshGameBackground(board.getBoardMatrix());
        
        return new DownData(clearRow, board.getViewData());
	}

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

	@Override
	public ViewData onHoldEvent(MoveEvent event) {
		if (board.canHold()) {
			board.holdBrick();
	        guiController.updateNextPreview(board.getNextBrick());
	        guiController.updateHoldPreview(board.getHeldbrick());
	        guiController.refreshGameBackground(board.getBoardMatrix());
		}
        return board.getViewData();
	}


    @Override
    public void createNewGame() {
        board.newGame();
        guiController.refreshGameBackground(board.getBoardMatrix());
        guiController.updateNextPreview(board.getNextBrick());
        guiController.updateHoldPreview(null);
        guiController.updateLines(board.getScore().linesLeft());
    }
}
