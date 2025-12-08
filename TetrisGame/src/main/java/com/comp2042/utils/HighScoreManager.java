package com.comp2042.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Handles loading, saving, and retrieving high scores for each game mode.
 * High scores are saved in a local text file.
 */
public class HighScoreManager {
	private static final String HIGH_SCORE_FILENAME = "highscore.txt";
	private static Map<String, Integer> highScoresMap = new HashMap<>();
	
    /**
     * Loads all saved high scores from the storage file into a Hash Map.
     */
	public static void loadAllHighScores() {
		highScoresMap.clear();
		File highScoreFile = new File(HIGH_SCORE_FILENAME);
		if (!highScoreFile.exists()) {
			return;
		}
		
		try {
			Scanner sc = new Scanner(highScoreFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] parts = line.split("=");
				String mode = parts[0];
				int score = Integer.parseInt(parts[1]);
				
				highScoresMap.put(mode, score);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Saves a new high score for the current game mode into the external file.
     *
     * @param highScore: the score to save
     */
	public static void saveHighScore(int highScore) {
		String mode = GameModeManager.getGameModeName();
		highScoresMap.put(mode, highScore);
		saveAllHighScores();
	}
	
    /**
     * Writes all stored high scores to the file.
     */
	public static void saveAllHighScores() {
		File highScoreFile = new File(HIGH_SCORE_FILENAME);
		try {
			PrintWriter pw = new PrintWriter(highScoreFile);
			for (Map.Entry<String, Integer> entry : highScoresMap.entrySet()) {
				pw.println(entry.getKey() + "=" + entry.getValue());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
    /**
     * Returns the saved high score for the current game mode.
     *
     * @return the high score, or 0 if none is stored
     */
	public static int getHighScore() {
		loadAllHighScores();
		String mode = GameModeManager.getGameModeName();
		return highScoresMap.getOrDefault(mode, 0);
	}
	
    /**
     * Returns an unmodifiable map of all high scores.
     *
     * @return a copy of the high score map
     */
	public static Map<String, Integer> getAllHighScores() {
		loadAllHighScores();
		return Map.copyOf(highScoresMap);
	}
	
    /**
     * Checks whether a given score is a new high score.
     * Sprint mode uses lower time as a better score.
     *
     * @param score: the new score to compare
     * @param highScore: the existing high score
     * @return true if the new score beats the stored high score, false otherwise
     */
	public static boolean isHighScore(int score, int highScore) {
		if (GameModeManager.getGameMode() == GameModes.SPRINT && highScore != 0 && score != 0) {
			return score < highScore;
		} else {
			return score > highScore;
		}
	}
}
