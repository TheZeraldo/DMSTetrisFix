package com.comp2042.ui.managers;

import com.comp2042.utils.KeyBinds;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * Provides UI functionality for editing keybinds.
 * Allows the user to click a button and assign a new keyboard key to a gameplay action via KeyBinds class.
 */
public class KeyBindEditor {
    private static Button waitingForKey = null;
    
    /**
     * Attaches key binding editing behavior to a button.
     * When clicked, the button waits for the user to press a key, then updates the associated action's keybind if the keybind is unique.
     *
     * @param button: the button used to trigger key selection
     * @param action: the name of the action to rebind
     */
    public static void setup(Button button, String action) {
    	button.setText(KeyBinds.getKey(action).toString());
    	
    	button.setOnAction(e -> {
    		
    		if (waitingForKey != null) {
    			return;
    		}
    		
    		waitingForKey = button;
    		button.setText("Press a key...");
    		
    		Scene scene = button.getScene();
    		
    		EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
    			@Override
    			public void handle(KeyEvent event) {
    				KeyCode key = event.getCode();
    				if (KeyBinds.setKey(action, key)) {
    					button.setText(key.toString());
    				} else {
    					button.setText("Key already in use!");
    				}
    				
    				waitingForKey = null;
    				scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
    			}
    		};
    		
    		scene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
    	});
    }
}
