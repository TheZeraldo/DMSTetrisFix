package com.comp2042.main;


/**
 * Represents the different states of the game.
 * This enum is used to control which screen or mode the application is currently displaying.
 */
public enum GameStates {

	MENU,
	PLAYING;
	
	public static GameStates gameState = MENU;
	
    /**
     * Updates the current game state.
     *
     * @param state: the new game state to set to
     */
	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	
}
