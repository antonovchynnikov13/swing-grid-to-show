package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class GridModel {
    public static final int PANELS_COUNT = 16;
    public static final int COLS = 4;
    public static final int ROWS = 4;
    private static final String EMPTY = "";

    //data
    private int highlightIndex = 0;
    private SelectionMode selectionMode = SelectionMode.HOVER;
    private final String[] cells = new String[PANELS_COUNT];

    //observer
    private final List<ModelObserver> observers = new ArrayList<>();
    public void addObserver(ModelObserver o) { if (o != null) observers.add(o); }
    public void removeObserver(ModelObserver o) { observers.remove(o); }
    private void notifyObservers() { for (ModelObserver o : observers) o.onModelChanged(); }


    public GridModel() {
        validateConfig();
        for (int i = 0; i < PANELS_COUNT; i++) cells[i] = String.valueOf(i);
    }

    public int getHighlightIndex() {
        return highlightIndex;
    }

    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public String getCell(int i) {
        return cells[i];
    }

    public boolean isEmptyCell(int i) {
        String t = cells[i];
        return t == null || t.isEmpty();
    }

    public void toggleMode() {
        this.selectionMode = (selectionMode == SelectionMode.HOVER ? SelectionMode.CLICK : SelectionMode.HOVER);
        notifyObservers();
    }

    public void setHighlight(int idx) {
        if (idx >= 0 && idx < PANELS_COUNT) {
            this.highlightIndex = idx;
            notifyObservers();
        }
    }

    public void keyLeft() {
        int i = highlightIndex;
        while (i > 0) {
            i--;
            if (!isEmptyCell(i)) {
                highlightIndex = i;
                break;
            }
        }
        notifyObservers();
    }

    public void keyRight() {
        int i = highlightIndex;
        while (i < PANELS_COUNT - 1) {
            i++;
            if (!isEmptyCell(i)) {
                highlightIndex = i;
                break;
            }
        }
        notifyObservers();
    }

    public void deleteAt(int i) {
        if (i < 0 || i >= PANELS_COUNT) return;
        if (isEmptyCell(i)) return;

        final int col = i % COLS;
        final int row = i / COLS;

        final int lastPossibleRow = ((PANELS_COUNT - 1) - col) / COLS;

        int bottom = row;
        for (int r = lastPossibleRow; r > row; r--) {
            int idx = r * COLS + col;
            if (!isEmptyCell(idx)) { bottom = r; break; }
        }

        for (int r = row; r < bottom; r++) {
            cells[r * COLS + col] = cells[(r + 1) * COLS + col];
        }
        cells[bottom * COLS + col] = EMPTY;

        notifyObservers();
    }



    private void validateConfig() {
        if (COLS <= 0 || ROWS <= 0) throw new IllegalArgumentException("ROWS and COLS must be > 0");
        int capacity = ROWS * COLS;
        if (capacity < PANELS_COUNT) {
            throw new IllegalArgumentException("Invalid config: " + ROWS + "x" + COLS + " < " + PANELS_COUNT);
        }
        if (highlightIndex < 0 || highlightIndex >= PANELS_COUNT) {
            throw new IllegalArgumentException("highlightIndex out of bounds: 0.." + (PANELS_COUNT - 1));
        }
        int filledRows = (PANELS_COUNT + COLS - 1) / COLS; // ceil
        if (filledRows < ROWS) {
            throw new IllegalArgumentException(
                    "Not enough panels for "+ROWS+" rows: have only "+filledRows+" filled rows"
            );
        }
    }
}

