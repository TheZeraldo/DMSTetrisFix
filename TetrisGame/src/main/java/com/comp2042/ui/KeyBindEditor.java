package com.comp2042.ui;

import com.comp2042.utils.KeyBinds;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyBindEditor {
    private static Button waitingForKey = null;
    
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
