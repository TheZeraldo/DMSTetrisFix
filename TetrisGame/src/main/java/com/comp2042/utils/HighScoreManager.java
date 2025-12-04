package com.comp2042.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HighScoreManager {
	private static final String HIGH_SCORE_FILENAME = "highscore.txt";
	private static Map<String, Integer> highScoresMap = new HashMap<>();
	
	public static void loadAllHighScores() {
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
	
	public static void saveHighScore(int highScore) {
		String mode = GameModes.GetGameModeName();
		highScoresMap.put(mode, highScore);
		saveAllHighScores();
	}
	
	public static void saveAllHighScores() {
		File highScoreFile = new File(HIGH_SCORE_FILENAME);
		if (!highScoreFile.exists()) {
			try {
				highScoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
	
	public static int getHighScore() {
		loadAllHighScores();
		String mode = GameModes.GetGameModeName();
		return highScoresMap.getOrDefault(mode, 0);
	}
	
	public static Map<String, Integer> getAllHighScores() {
		loadAllHighScores();
		return highScoresMap;
	}
	
	public static boolean isHighScore(int score, int highScore) {
		if (GameModes.gameMode == GameModes.SPRINT) {
			return score < highScore;
		} else {
			return score > highScore;
		}
	}
}
