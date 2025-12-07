package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;


/**
 * Manages the hold functionality in the game.
 * Allows the player to store a brick for later use and swap it with the current brick.
 * Ensures that holding can only occur once per turn.
 */
public class HoldManager {
	private final BrickManager brickManager;
	
	private Brick heldBrick;
	private boolean canHoldThisTurn = true;
	
    /**
     * Creates a new HoldManager linked to a BrickManager.
     *
     * @param brickManager: the BrickManager controlling the active bricks
     */
	public HoldManager(BrickManager brickManager) {
		this.brickManager = brickManager;
	}
	
    /**
     * Holds the current brick or swaps it with the previously held brick.
     * Prevents multiple holds in the same turn.
     */
	public void hold() {
		if (!canHoldThisTurn) {
			return;
		}

		canHoldThisTurn = false;
		Brick currentBrick = brickManager.getBrick();
		if (heldBrick == null) {
			heldBrick = currentBrick;
			canHoldThisTurn = true;
			brickManager.createNewBrick();
		} else {
			Brick temp = heldBrick;
			heldBrick = currentBrick;
	        brickManager.setBrick(temp);
	        brickManager.resetOffset();
		}
	}
	
    /**
     * Resets the held brick and allows holding again.
     */
	public void reset() {
		heldBrick = null;
		canHoldThisTurn = true;
	}
	
    /**
     * Gets the currently held brick.
     *
     * @return the held Brick, or null if none is held
     */
	public Brick getHeldBrick() {
		return heldBrick;
	}
	
    /**
     * Sets whether holding is allowed in the current turn.
     *
     * @param canHold: boolean value, true to allow holding, false to not allow it
     */
	public void setCanHold(boolean canHold) {
		canHoldThisTurn = canHold;
	}
	
    /**
     * Checks whether the player can hold a brick this turn.
     *
     * @return true if holding is allowed, false if not
     */
	public boolean canHold() {
		return canHoldThisTurn;
	}
}
