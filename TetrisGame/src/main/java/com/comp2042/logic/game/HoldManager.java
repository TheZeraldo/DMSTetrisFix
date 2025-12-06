package com.comp2042.logic.game;

import com.comp2042.logic.bricks.Brick;

public class HoldManager {
	private final BrickManager brickManager;
	
	private Brick heldBrick;
	private boolean canHoldThisTurn = true;
	
	public HoldManager(BrickManager brickManager) {
		this.brickManager = brickManager;
	}
	
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
	
	public void reset() {
		heldBrick = null;
		canHoldThisTurn = true;
	}
	
	public Brick getHeldBrick() {
		return heldBrick;
	}
	
	public void setCanHold(boolean canHold) {
		canHoldThisTurn = canHold;
	}
	
	public boolean canHold() {
		return canHoldThisTurn;
	}
}
