package com.comp2042.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;


/**
 * Stores and manages key bindings for player actions.
 * Allows retrieval and reassignment of control keys.
 */
public class KeyBinds {
	private static Map<String, KeyCode> keyBindMap = new HashMap<>();

	static {
		keyBindMap.put("Move Left", KeyCode.LEFT);
        keyBindMap.put("Move Right", KeyCode.RIGHT);
        keyBindMap.put("Rotate", KeyCode.UP);
        keyBindMap.put("Soft Drop", KeyCode.DOWN);
        keyBindMap.put("Hard Drop", KeyCode.SPACE);
        keyBindMap.put("Hold", KeyCode.C);
        keyBindMap.put("Pause", KeyCode.P);
        keyBindMap.put("Main Menu", KeyCode.ESCAPE);
        keyBindMap.put("New Game", KeyCode.N);
	}
	
    /**
     * Returns the key assigned to a specific action.
     *
     * @param action: the action name
     * @return the assigned KeyCode
     */
	public static KeyCode getKey(String action) {
		return keyBindMap.get(action);
	}
	
    /**
     * Assigns a new key to an action if it is not already in use.
     *
     * @param action: the action name to update
     * @param key: the new key to bind
     * @return true if the key was successfully assigned, false otherwise
     */
	public static boolean setKey(String action, KeyCode key) {
		for (Map.Entry<String, KeyCode> entry : keyBindMap.entrySet()) {
			String otherAction = entry.getKey();
			KeyCode usedKey = entry.getValue();
			
			if (otherAction != action && usedKey == key) {
				return false;
			}
		}
		
		keyBindMap.put(action, key);
		return true;
	}
	
    /**
     * Returns the current map of key bindings.
     *
     * @return a map of action names to key codes
     */
	public static Map<String, KeyCode> getKeyBindMap() {
		return keyBindMap;
	}
}
