package com.comp2042.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Map;

import org.junit.jupiter.api.*;

class HighScoreManagerTest {

    private static final String TEST_FILE = "highscore.txt";

    @BeforeEach
    void cleanFile() throws Exception {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        HighScoreManager.loadAllHighScores();
    }

    @AfterEach
    void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }

    @Test
    void testSaveAndGetHighScore() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        
        HighScoreManager.saveHighScore(100);
        int retrieved = HighScoreManager.getHighScore();
        assertEquals(100, retrieved, "Saved high score should be retrievable");
    }

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

    @Test
    void testIsHighScoreClassic() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        // higher score is better
        assertTrue(HighScoreManager.isHighScore(100, 50));
        assertFalse(HighScoreManager.isHighScore(50, 100));
    }

    @Test
    void testIsHighScoreSprint() {
        GameModeManager.setGameMode(GameModes.SPRINT);
        // lower time is better
        assertTrue(HighScoreManager.isHighScore(30, 50));
        assertFalse(HighScoreManager.isHighScore(50, 30));
    }

    @Test
    void testGetHighScoreReturnsZeroIfNone() {
        GameModeManager.setGameMode(GameModes.CLASSIC);
        assertEquals(0, HighScoreManager.getHighScore());
    }
}
