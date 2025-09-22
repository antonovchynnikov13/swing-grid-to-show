package main.java.model;

/**
 * SelectionMode defines how user interactions affect the grid:
 * - CLICK: clicking on a non-empty cell highlights it.
 * - HOVER: moving the mouse over a non-empty cell highlights it,
 *          clicking highlights and deletes the cell.
 */

public enum SelectionMode {
    CLICK {
        @Override public void onClick(GridModel m, int idx) {
            if (!m.isEmptyCell(idx)) { m.setHighlight(idx); }
        }
        @Override public void onEnter(GridModel m, int idx) {}
        @Override public String toString() { return "Click"; }
    },
    HOVER {
        @Override public void onClick(GridModel m, int idx) {
            if (!m.isEmptyCell(idx)) { m.setHighlight(idx); m.deleteAt(idx); }
        }
        @Override public void onEnter(GridModel m, int idx) {
            if (!m.isEmptyCell(idx)) { m.setHighlight(idx); }
        }
        @Override public String toString() { return "Hover"; }
    };

    public abstract void onClick(GridModel m, int idx);
    public abstract void onEnter(GridModel m, int idx);
}
