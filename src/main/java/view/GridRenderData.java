package main.java.view;

import java.util.Arrays;

public class GridRenderData {
    private final int rows;
    private final int cols;
    private final int panelsCount;
    private final int highlightIndex;
    private final String modeText;
    private final String[] cells;

    public GridRenderData(int rows, int cols, int panelsCount,
                          int highlightIndex, String modeText, String[] cells) {
        this.rows = rows;
        this.cols = cols;
        this.panelsCount = panelsCount;
        this.highlightIndex = highlightIndex;
        this.modeText = modeText;
        this.cells = cells != null ? Arrays.copyOf(cells, cells.length) : new String[0];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getPanelsCount() { return panelsCount; }
    public int getHighlightIndex() { return highlightIndex; }
    public String getModeText() { return modeText; }
    public String getCell(int i) { return cells[i]; }
    public boolean isEmptyCell(int i) { String t = cells[i]; return t == null || t.isEmpty(); }
}
