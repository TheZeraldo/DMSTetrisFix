package com.comp2042;

public enum GameStates {

	MENU,
	PLAYING,
	GAME_OVER;
	
	public static GameStates gameState = MENU;
	
	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	
}
