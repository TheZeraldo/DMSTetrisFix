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


/**
 * Acts as the main controller for game logic and UI interaction.
 * Handles user input events and updates the game board.
 */
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController guiController;

    /**
     * Creates a new GameController and initializes the game and UI bindings.
     *
     * @param c: GUI Controller used to update the UI
     */
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

    /**
     * Handles a downward movement event.
     * Updates the board, clears rows, updates score, lines, and timer, and refreshes UI.
     *
     * @param event: the movement event
     * @return data describing the updated view and cleared rows
     */
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

    /**
     * Handles a hard drop event.
     * Instantly drops the brick to the lowest valid position.
     * Updates the board, clears rows, updates score, lines, and timer, and refreshes UI.
     *
     * @param event: the movement event
     * @return data describing the updated view and cleared rows
     */
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

    /**
     * Handles a move left input event.
     *
     * @param event: the movement event
     * @return updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles a move right input event.
     *
     * @param event: the movement event
     * @return updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles a rotate input event.
     *
     * @param event: the movement event
     * @return updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Handles a hold brick input event.
     *
     * @param event: the movement event
     * @return updated view data
     */
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

    /**
     * Starts a new game and resets all game and UI elements.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        guiController.refreshGameBackground(board.getBoardMatrix());
        guiController.updateNextPreview(board.getNextBrick());
        guiController.updateHoldPreview(null);
        guiController.updateLines(board.getScore().linesLeft());
    }
}
