package com.comp2042.events;


/**
 * Defines the origin of a game input event.
 * This enum is used to distinguish whether an event was triggered by the user or by an internal game thread.
 */
public enum EventSource {
    USER, THREAD
}
