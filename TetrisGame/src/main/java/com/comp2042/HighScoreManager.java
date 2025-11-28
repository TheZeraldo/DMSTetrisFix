package com.comp2042;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighScoreManager {
	private static final String HIGH_SCORE_FILENAME = "highscore.txt";
	
	public static void saveHighScore(int highScore) {
		File highScoreFile = new File(HIGH_SCORE_FILENAME);
		if (highScoreFile.exists()) {
			System.out.println("File " + HIGH_SCORE_FILENAME + " already exists");
		} else {
			try {
				highScoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		try {
			PrintWriter pw = new PrintWriter(highScoreFile);
			pw.print(highScore);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getHighScore() {
		File highScoreFile = new File(HIGH_SCORE_FILENAME);
		int highScore = 0;
		if (highScoreFile.exists()) {
			try {
				Scanner sc = new Scanner(highScoreFile);
				//highScore = sc.nextInt();
				highScore = Integer.parseInt(sc.nextLine());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return highScore;
	}
}
