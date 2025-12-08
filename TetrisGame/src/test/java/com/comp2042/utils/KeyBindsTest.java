package com.comp2042.utils;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class KeyBindsTest {

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

    @Test
    void testGetDefaultKey() {
        assertEquals(KeyCode.LEFT, KeyBinds.getKey("Move Left"));
        assertEquals(KeyCode.UP, KeyBinds.getKey("Rotate"));
        assertEquals(KeyCode.ESCAPE, KeyBinds.getKey("Main Menu"));
    }

    @Test
    void testSetKeySuccessfully() {
        boolean success = KeyBinds.setKey("Move Left", KeyCode.A);
        assertTrue(success, "Should allow setting a new key if not used by another action");
        assertEquals(KeyCode.A, KeyBinds.getKey("Move Left"));
    }

    @Test
    void testSetKeyFailsIfDuplicate() {
        boolean success = KeyBinds.setKey("Move Right", KeyCode.LEFT);
        assertFalse(success, "Should reject duplicate key assignment");
        assertEquals(KeyCode.RIGHT, KeyBinds.getKey("Move Right"), "Original key should remain unchanged");
    }

    @Test
    void testGetKeyBindMapReflectsUpdates() {
        KeyBinds.setKey("Rotate", KeyCode.W);
        Map<String, KeyCode> map = KeyBinds.getKeyBindMap();
        assertEquals(KeyCode.W, map.get("Rotate"));
        assertEquals(KeyCode.LEFT, map.get("Move Left"), "Other keys should remain unchanged");
    }

    @Test
    void testSetKeyAllowsSameKeyForSameAction() {
        // Should allow assigning the same key to the same action
        boolean success = KeyBinds.setKey("Move Left", KeyCode.LEFT);
        assertTrue(success);
        assertEquals(KeyCode.LEFT, KeyBinds.getKey("Move Left"));
    }
}
