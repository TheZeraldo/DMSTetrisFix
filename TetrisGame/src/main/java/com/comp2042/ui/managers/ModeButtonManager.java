package com.comp2042.ui.managers;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


/**
 * Manages game mode buttons and their descriptions.
 * Handles hover behaviour and sets game mode to the selected game mode.
 */
public class ModeButtonManager {
    
    /**
     * Configures a button to show a description popup when hovered.
     *
     * @param button: the button to configure
     * @param mode: the game mode associated with the button
     * @param popup: the label used to display the description
     * @param parentPane: the pane containing the button and popup
     */
    public static void setup(Button button, GameModes mode, Label popup, Pane parentPane) {
    	button.setOnMouseEntered(e -> showPopup(button, popup, parentPane, GameModes.getGameModeDescription(mode)));
    	button.setOnMouseExited(e -> popup.setVisible(false));
    }
	
    /**
     * Displays a description popup positioned relative to the button.
     *
     * @param button: the button being hovered
     * @param popup: the label used to display the description
     * @param parentPane: the parent container used for positioning
     * @param description: the text to display in the popup
     */
    private static void showPopup(Button button, Label popup, Pane parentPane, String description) {
    	popup.setText(description);
    	popup.setVisible(true);
    	
    	
    	double buttonX = button.getLayoutX();
    	double buttonY = button.getLayoutY();
    	double buttonHeight = button.getHeight();
    	
    	double popupX = buttonX;
    	double popupY = buttonY + buttonHeight;
    	double popupHeight = popup.getHeight();
    	
    	double paneHeight = parentPane.getHeight();
    	
    	if (popupY + popupHeight > paneHeight) {
    		popupY = buttonY - popupHeight;
    	}
    	
    	popup.setLayoutX(popupX);
    	popup.setLayoutY(popupY);
    }
    
    /**
     * Sets the active game mode based on the text of the selected button.
     *
     * @param button: the button that was clicked
     */
    public static void setMode(Button button) {
    	switch (button.getText()) {
			case "Classic":
				GameModeManager.setGameMode(GameModes.CLASSIC);
				break;
			case "Hardcore":
				GameModeManager.setGameMode(GameModes.HARDCORE);
				break;
			case "Time Limit":
				GameModeManager.setGameMode(GameModes.TIME);
				break;
			case "Sprint":
				GameModeManager.setGameMode(GameModes.SPRINT);
				break;
			case "Ultra":
				GameModeManager.setGameMode(GameModes.ULTRA);
				break;
			case "Invisible":
				GameModeManager.setGameMode(GameModes.INVISIBLE);
				break;
			case "Big Mode":
				GameModeManager.setGameMode(GameModes.BIG);
				break;
			case "T-Mode":
				GameModeManager.setGameMode(GameModes.TONLY);
				break;
		}
    }
}
