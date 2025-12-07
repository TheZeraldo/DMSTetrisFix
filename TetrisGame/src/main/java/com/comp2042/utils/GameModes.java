package com.comp2042.utils;


/**
 * Defines the available game modes and provides descriptions for each mode.
 * Used to configure different rule sets and behaviours in the game.
 */
public enum GameModes {
	CLASSIC,
	HARDCORE,	//Fast gravity
	TIME,		//Adds time for lines cleared
	SPRINT,		//Clear 40 lines, time is the score
	ULTRA,		//Set time, get as many points in the time limit
	INVISIBLE,	//Blocks become invisible after they land
	BIG,		//Pieces are 2x as large
	TONLY;		//Only T-pieces
	

    /**
     * Returns a description for a given game mode.
     *
     * @param mode: the game mode to describe
     * @return a short description of the selected game mode
     */
	public static String getGameModeDescription(GameModes mode) {       
		return switch (mode) {
		    case CLASSIC -> "Standard Tetris, clear lines to score, speed increases per level";
		    case HARDCORE -> "Game starts at max fall speed";
		    case TIME -> "Game starts with a countdown of 20 seconds, clear lines to add time";
		    case SPRINT -> "Clear 40 lines as fast as possible, time is the score";
		    case ULTRA -> "2 minutes to score as many points as possible";
		    case INVISIBLE -> "Blocks become invisible once they land";
		    case BIG -> "Piece are twice as big";
		    case TONLY -> "Only T-Shaped pieces spawn";
		};
	}
}
