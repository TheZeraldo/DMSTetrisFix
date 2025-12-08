package com.comp2042.logic.bricks;

/**
 * Defines the behaviour of a brick generator.
 * Classes that implement this interface are responsible for providing the current and upcoming bricks.
 */
public interface BrickGenerator {

    /**
     * Returns the next brick to be placed in the game and removes it from queue.
     *
     * @return the next brick in the queue
     */
    Brick getBrick();

    /**
     * Returns the next brick without removing it from the queue.
     *
     * @return the next brick in the queue
     */
    Brick getNextBrick();
}
