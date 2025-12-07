package com.comp2042.model;


/**
 * Stores the result of a downward movement action.
 * This includes both row clear information and updated view data for rendering.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Creates a DownData object.
     *
     * @param clearRow: information about any cleared rows (may be null if none were cleared)
     * @param viewData: the updated view data for rendering the game state
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets information about cleared rows.
     *
     * @return the ClearRow object, or null if no rows were cleared
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the updated view data.
     *
     * @return the ViewData object used for rendering
     */
    public ViewData getViewData() {
        return viewData;
    }
}
