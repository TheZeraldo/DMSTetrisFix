package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;


/**
 * Generates random bricks for the game.
 * Uses an internal queue to provide the current brick and preview the next brick.
 * Supports different game modes that affect the available brick types.
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Creates a new random brick generator and loads the first bricks to queue based on the active game mode.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        if (GameModeManager.getGameMode() == GameModes.TONLY) {
            brickList.add(new TBrick());
        } else {
	        brickList.add(new IBrick());
	        brickList.add(new JBrick());
	        brickList.add(new LBrick());
	        brickList.add(new OBrick());
	        brickList.add(new SBrick());
	        brickList.add(new TBrick());
	        brickList.add(new ZBrick());
        }
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Returns the next brick and removes it from the queue.
     * Ensures the queue is refilled when running low.
     *
     * @return the next brick in queue
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Returns the next brick without removing it from queue.
     *
     * @return the next brick in queue
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
