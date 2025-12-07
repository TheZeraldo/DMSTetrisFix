package com.comp2042.events;


/**
 * Represents an input event that describes a movement or action in the game.
 * Stores both the type of action and the source of the event.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Creates a new move event with the specified type and source.
     *
     * @param eventType: the type of action being performed
     * @param eventSource: the source that triggered the event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of this event.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the source of this event.
     *
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
