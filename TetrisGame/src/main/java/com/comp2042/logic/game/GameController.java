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

import javafx.beans.property.IntegerProperty;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLevel(board.getScore().levelProperty());
        viewGuiController.updateLines(board.getScore().linesLeft());
        viewGuiController.updateNextPreview(board.getNextBrick());
        viewGuiController.updateHoldPreview(board.getHeldbrick());
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
            		viewGuiController.increaseTimer(clearRow.getLinesRemoved() * 5);
            	}
                board.getScore().add(clearRow.getScoreBonus());
                board.getScore().addLines(clearRow.getLinesRemoved());
                if (GameModeManager.getGameMode() == GameModes.SPRINT && board.getScore().linesLeft() == 0) {
                    viewGuiController.gameOver(false);
                }
                viewGuiController.updateLines(board.getScore().linesLeft());
                viewGuiController.updateFallSpeed(board.getScore().getFallSpeed());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver(true);
            }

            viewGuiController.updateNextPreview(board.getNextBrick());
            viewGuiController.refreshGameBackground(board.getBoardMatrix());

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
        		viewGuiController.increaseTimer(clearRow.getLinesRemoved() * 10);
        	}
            board.getScore().add(clearRow.getScoreBonus());
            board.getScore().addLines(clearRow.getLinesRemoved());
            //board.getScore().addLines(10);
            if (GameModeManager.getGameMode() == GameModes.SPRINT && board.getScore().linesLeft() == 0) {
                viewGuiController.gameOver(false);
            } else {
            	viewGuiController.updateFallSpeed(board.getScore().getFallSpeed());
            }
        	viewGuiController.updateLines(board.getScore().linesLeft());
        }
        if (board.createNewBrick()) {
            viewGuiController.gameOver(true);
        }

        viewGuiController.updateNextPreview(board.getNextBrick());
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        
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
	        viewGuiController.updateNextPreview(board.getNextBrick());
	        viewGuiController.updateHoldPreview(board.getHeldbrick());
	        viewGuiController.refreshGameBackground(board.getBoardMatrix());
		}
        return board.getViewData();
	}


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.updateNextPreview(board.getNextBrick());
        viewGuiController.updateHoldPreview(null);
        viewGuiController.updateLines(board.getScore().linesLeft());
    }
    
    public Board getBoard() {
    	return board;
    }
}
