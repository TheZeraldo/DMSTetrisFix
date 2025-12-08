package com.comp2042.utils;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;


/**
 * Unit tests for the KeyBinds class.
 * Verifies functionality for retrieving, updating, and managing key bindings for player actions. 
 * Ensures proper handling of default keys, updates, and duplicate key assignments.
 */
class KeyBindsTest {


    /**
     * Resets all key bindings to their default values before each test.
     */
    @BeforeEach
    void resetKeyBinds() {
        KeyBinds.setKey("Move Left", KeyCode.LEFT);
        KeyBinds.setKey("Move Right", KeyCode.RIGHT);
        KeyBinds.setKey("Rotate", KeyCode.UP);
        KeyBinds.setKey("Soft Drop", KeyCode.DOWN);
        KeyBinds.setKey("Hard Drop", KeyCode.SPACE);
        KeyBinds.setKey("Hold", KeyCode.C);
        KeyBinds.setKey("Pause", KeyCode.P);
        KeyBinds.setKey("Main Menu", KeyCode.ESCAPE);
        KeyBinds.setKey("New Game", KeyCode.N);
    }

    /**
     * Tests that the default key bindings are correctly returned by getKey().
     */
    @Test
    void testGetDefaultKey() {
        assertEquals(KeyCode.LEFT, KeyBinds.getKey("Move Left"));
        assertEquals(KeyCode.UP, KeyBinds.getKey("Rotate"));
        assertEquals(KeyCode.ESCAPE, KeyBinds.getKey("Main Menu"));
    }

    /**
     * Tests that a key can be successfully reassigned to a new value if it is not already assigned to another action.
     */
    @Test
    void testSetKeySuccessfully() {
        boolean success = KeyBinds.setKey("Move Left", KeyCode.A);
        assertTrue(success, "Should allow setting a new key if not used by another action");
        assertEquals(KeyCode.A, KeyBinds.getKey("Move Left"));
    }

    /**
     * Tests that setting a key fails if the key is already assigned to another action.
     * Ensures the original key remains unchanged.
     */
    @Test
    void testSetKeyFailsIfDuplicate() {
        boolean success = KeyBinds.setKey("Move Right", KeyCode.LEFT);
        assertFalse(success, "Should reject duplicate key assignment");
        assertEquals(KeyCode.RIGHT, KeyBinds.getKey("Move Right"), "Original key should remain unchanged");
    }


    /**
     * Tests that getKeyBindMap() reflects updates correctly and that unrelated key bindings remain unchanged.
     */
    @Test
    void testGetKeyBindMapReflectsUpdates() {
        KeyBinds.setKey("Rotate", KeyCode.W);
        Map<String, KeyCode> map = KeyBinds.getKeyBindMap();
        assertEquals(KeyCode.W, map.get("Rotate"));
        assertEquals(KeyCode.LEFT, map.get("Move Left"), "Other keys should remain unchanged");
    }
}
