package com.comp2042.utils;

public class GameModeManager {

	private static GameModes gameMode = GameModes.CLASSIC;
	
	public static void setGameMode(GameModes mode) {
		gameMode = mode;
	}
	
	public static String getGameModeName() {
		return gameMode.name();
	}
	
	public static GameModes getGameMode() {
		return gameMode;
	}
}
