package com.comp2042.logic.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;

class SimpleBoardTest {

	private SimpleBoard board;

    @BeforeEach
    void setUp() {
        board = new SimpleBoard(25, 10);
        board.createNewBrick();
    }

    @Test
    void testMoveBrickDown() {
        boolean moved = board.moveBrickDown();
        assertTrue(moved, "Brick should be able to move down initially");
    }

    @Test
    void testMoveBrickLeftRight() {
        boolean movedLeft = board.moveBrickLeft();
        boolean movedRight = board.moveBrickRight();
        assertTrue(movedLeft || movedRight, "Brick should be able to move left or right");
    }

    @Test
    void testRotateBrick() {
        boolean rotated = board.rotateLeftBrick();
        assertTrue(rotated, "Brick should be able to rotate initially");
    }

    @Test
    void testCreateNewBrick() {
        boolean gameOver = board.createNewBrick();
        assertFalse(gameOver, "Creating a new brick at the start should not trigger game over");
    }

    @Test
    void testMergeAndClearRows() {
        board.mergeBrickToBackground();
        ClearRow result = board.clearRows();
        assertNotNull(result, "Clearing rows should return a ClearRow object");
        assertTrue(result.getNewMatrix().length > 0, "The cleared matrix should have valid size");
    }

    @Test
    void testScoreObject() {
        Score score = board.getScore();
        assertNotNull(score, "Score object should not be null");
        assertEquals(0, score.scoreProperty().get(), "Initial score should be zero");
    }

    @Test
    void testHoldBrickFunctionality() {
        if (board.canHold()) {
            Brick firstHeld = board.getHeldbrick();
            board.holdBrick();
            assertNotNull(board.getHeldbrick(), "After holding a brick, held brick should not be null");
            assertNotEquals(firstHeld, board.getHeldbrick(), "Held brick should update after hold");
        }
    }

    @Test
    void testGetNextBrick() {
        Brick nextBrick = board.getNextBrick();
        assertNotNull(nextBrick, "Next brick should not be null");
    }

    @Test
    void testGetLandingYPosition() {
        int landingY = board.getLandingYPosition();
        assertTrue(landingY >= 0, "Landing Y position should be non-negative");
    }

    @Test
    void testNewGameResetsScore() {
        board.getScore().add(50);
        board.newGame();
        assertEquals(0, board.getScore().scoreProperty().get(), "Score should reset to zero on new game");
    }

    @Test
    void testCanHold() {
        boolean canHold = board.canHold();
        assertTrue(canHold, "Initially, a brick should be able to be held");
    }

}
