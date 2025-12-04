package com.comp2042.main;

public enum GameStates {

	MENU,
	PLAYING;
	
	public static GameStates gameState = MENU;
	
	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	
}
