package com.comp2042.utils;


/**
 * Manages the currently selected game mode for the application.
 * Provides static methods to set and retrieve the active mode.
 */
public class GameModeManager {

	private static GameModes gameMode = GameModes.CLASSIC;
	
    /**
     * Sets the current game mode.
     *
     * @param mode: the game mode to set to
     */
	public static void setGameMode(GameModes mode) {
		gameMode = mode;
	}
	
    /**
     * Returns the name of the currently selected game mode.
     *
     * @return the name of the current game mode
     */
	public static String getGameModeName() {
		return gameMode.name();
	}
	
    /**
     * Returns the currently active game mode.
     *
     * @return the current gameMode value
     */
	public static GameModes getGameMode() {
		return gameMode;
	}
}
