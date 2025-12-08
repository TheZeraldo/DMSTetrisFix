package com.comp2042.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import org.junit.jupiter.api.*;


/**
 * Unit tests for the HighScoreManager class.
 * Tests functionality related to saving, retrieving, and managing high scores for different game modes, including file persistence and comparison logic.
 */
class HighScoreManagerTest {

    private static final String TEST_FILE = "highscore.txt";

    /**
     * Cleans and prepares the high score file before each test.
     * Ensures a clean state by creating a new file and loading scores.
     * 
     * @throws Exception if file operations fail
     */
    @BeforeEach
    void cleanFile() throws Exception {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        HighScoreManager.loadAllHighScores();
    }

    /**
     * Cleans up the test high score file after each test.
     */
    @AfterEach
    void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }

    /**
     * Tests saving a high score for a game mode and retrieving it.
     * Verifies that the saved score is correctly retrievable.
     */
    @Test
    void testSaveAndGetHighScore() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        
        HighScoreManager.saveHighScore(100);
        int retrieved = HighScoreManager.getHighScore();
        assertEquals(100, retrieved, "Saved high score should be retrievable");
    }

    /**
     * Tests saving high scores for multiple game modes and retrieving all scores.
     * Verifies that the map contains the correct scores for each mode.
     */
    @Test
    void testGetAllHighScores() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        HighScoreManager.saveHighScore(200);

        GameModeManager.setGameMode(GameModes.ULTRA);
        HighScoreManager.saveHighScore(500);

        Map<String, Integer> all = HighScoreManager.getAllHighScores();
        assertEquals(2, all.size());
        assertEquals(200, all.get("CLASSIC"));
        assertEquals(500, all.get("ULTRA"));
    }

    /**
     * Tests loading high scores from a file into the manager.
     * Verifies that scores from the file are correctly read for each game mode.
     * 
     * @throws Exception if file operations fail
     */
    @Test
    void testLoadAllHighScoresFromFile() throws Exception {
        try (PrintWriter pw = new PrintWriter(TEST_FILE)) {
            pw.println("CLASSIC=150");
            pw.println("SPRINT=30");
        }

        HighScoreManager.loadAllHighScores();
        GameModeManager.setGameMode(GameModes.CLASSIC);
        assertEquals(150, HighScoreManager.getHighScore());

        GameModeManager.setGameMode(GameModes.SPRINT);
        assertEquals(30, HighScoreManager.getHighScore());
    }

    /**
     * Tests the isHighScore() method for the CLASSIC mode.
     * Verifies that higher scores are considered better and lower scores are not.
     */
    @Test
    void testIsHighScoreClassic() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        // higher score is better
        assertTrue(HighScoreManager.isHighScore(100, 50));
        assertFalse(HighScoreManager.isHighScore(50, 100));
    }

    /**
     * Tests the isHighScore() method for the SPRINT mode.
     * Verifies that lower scores are considered better and higher scores are not since time is the score, lower time means faster.
     */
    @Test
    void testIsHighScoreSprint() {
        GameModeManager.setGameMode(GameModes.SPRINT);
        // lower time is better
        assertTrue(HighScoreManager.isHighScore(30, 50));
        assertFalse(HighScoreManager.isHighScore(50, 30));
    }

    /**
     * Tests that getHighScore() returns zero if no score exists.
     */
    @Test
    void testGetHighScoreReturnsZeroIfNone() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        assertEquals(0, HighScoreManager.getHighScore());
    }
}
