package com.comp2042;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;

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
	
	public static KeyCode getKey(String action) {
		return keyBindMap.get(action);
	}
	
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
	
	public static Map<String, KeyCode> getKeyBindMap() {
		return keyBindMap;
	}
}
