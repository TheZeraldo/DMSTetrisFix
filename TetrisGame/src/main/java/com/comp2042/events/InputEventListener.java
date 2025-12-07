package com.comp2042.events;

import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;


/**
 * Defines a contract for handling game input events.
 * Classes that implement this interface respond to player actions and game generated events.
 */
public interface InputEventListener {

    /**
     * Handles a downward movement event.
     *
     * @param event: the move event containing type and source information
     * @return data describing the result of the downward movement
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles a left movement event.
     *
     * @param event: the move event containing type and source information
     * @return data describing the result of the left movement
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles a right movement event.
     *
     * @param event: the move event containing type and source information
     * @return data describing the result of the right movement
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles a rotation event.
     *
     * @param event: the move event containing type and source information
     * @return data describing the result of the rotation
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Handles a hard drop that moves brick instantly to landing position.
     *
     * @param event: the move event containing type and source information
     * @return data describing the result of the hard drop
     */
    DownData onHardDropEvent(MoveEvent event);
    
    /**
     * Handles the hold event which stores or swaps the current brick.
     *
     * @param event: the move event containing type and source information
     * @return data used to update the game view
     */
    ViewData onHoldEvent(MoveEvent event);

    /**
     * Creates and initializes a new game.
     */
    void createNewGame();
}
