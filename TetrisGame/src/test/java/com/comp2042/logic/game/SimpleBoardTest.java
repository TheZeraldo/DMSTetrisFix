package com.comp2042.logic.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.model.ClearRow;
import com.comp2042.model.Score;


/**
 * Unit tests for the SimpleBoard class.
 * Tests the core functionality of the Tetris board, including moving, rotating, holding bricks, merging bricks into the background, clearing rows, and scoring.
 */
class SimpleBoardTest {

	private SimpleBoard board;

    /**
     * Initializes a new SimpleBoard instance and creates the first brick before each test.
     */
    @BeforeEach
    void setUp() {
        board = new SimpleBoard(25, 10);
        board.createNewBrick();
    }

    /**
     * Tests that a brick can move down when initially created.
     */
    @Test
    void testMoveBrickDown() {
        boolean moved = board.moveBrickDown();
        assertTrue(moved, "Brick should be able to move down initially");
    }

    /**
     * Tests that a brick can move left or right.
     */
    @Test
    void testMoveBrickLeftRight() {
        boolean movedLeft = board.moveBrickLeft();
        boolean movedRight = board.moveBrickRight();
        assertTrue(movedLeft || movedRight, "Brick should be able to move left or right");
    }

    /**
     * Tests that a brick can rotate when initially placed.
     */
    @Test
    void testRotateBrick() {
        boolean rotated = board.rotateLeftBrick();
        assertTrue(rotated, "Brick should be able to rotate initially");
    }

    /**
     * Tests that creating a new brick at the start of the game does not trigger game over.
     */
    @Test
    void testCreateNewBrick() {
        boolean gameOver = board.createNewBrick();
        assertFalse(gameOver, "Creating a new brick at the start should not trigger game over");
    }

    /**
     * Tests merging the current brick into the background and clearing rows.
     * Verifies that a ClearRow object is returned and has valid matrix size.
     */
    @Test
    void testMergeAndClearRows() {
        board.mergeBrickToBackground();
        ClearRow result = board.clearRows();
        assertNotNull(result, "Clearing rows should return a ClearRow object");
        assertTrue(result.getNewMatrix().length > 0, "The cleared matrix should have valid size");
    }

    /**
     * Tests the Score object functionality.
     * Verifies that it is not null and starts at zero.
     */
    @Test
    void testScoreObject() {
        Score score = board.getScore();
        assertNotNull(score, "Score object should not be null");
        assertEquals(0, score.scoreProperty().get(), "Initial score should be zero");
    }

    /**
     * Tests the hold brick functionality.
     * Verifies that the held brick updates correctly after holding.
     */
    @Test
    void testHoldBrickFunctionality() {
        if (board.canHold()) {
            Brick firstHeld = board.getHeldbrick();
            board.holdBrick();
            assertNotNull(board.getHeldbrick(), "After holding a brick, held brick should not be null");
            assertNotEquals(firstHeld, board.getHeldbrick(), "Held brick should update after hold");
        }
    }

    /**
     * Tests that the next brick is available and not null.
     */
    @Test
    void testGetNextBrick() {
        Brick nextBrick = board.getNextBrick();
        assertNotNull(nextBrick, "Next brick should not be null");
    }

    /**
     * Tests that the landing Y position of the current brick is non-negative.
     */
    @Test
    void testGetLandingYPosition() {
        int landingY = board.getLandingYPosition();
        assertTrue(landingY >= 0, "Landing Y position should be non-negative");
    }

    /**
     * Tests that starting a new game resets the score to zero.
     */
    @Test
    void testNewGameResetsScore() {
        board.getScore().add(50);
        board.newGame();
        assertEquals(0, board.getScore().scoreProperty().get(), "Score should reset to zero on new game");
    }

    /**
     * Tests that a brick can initially be held at the start.
     */
    @Test
    void testCanHold() {
        boolean canHold = board.canHold();
        assertTrue(canHold, "Initially, a brick should be able to be held");
    }

}
