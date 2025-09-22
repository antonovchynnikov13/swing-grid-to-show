package test.java.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.GridModel;
import main.java.model.SelectionMode;
import static org.junit.jupiter.api.Assertions.*;

class GridModelTest {

    private GridModel model;

    @BeforeEach
    void setUp() {
        model = new GridModel();
    }

    @Test
    void getHighlightIndex() {
        assertEquals(0, model.getHighlightIndex());
    }

    @Test
    void getSelectionMode() {
        assertEquals(SelectionMode.HOVER, model.getSelectionMode());
    }

    @Test
    void getCell() {
        assertEquals("0", model.getCell(0));
        assertEquals("5", model.getCell(5));
    }

    @Test
    void toggleMode() {
        assertEquals(SelectionMode.HOVER, model.getSelectionMode());
        model.toggleMode();
        assertEquals(SelectionMode.CLICK, model.getSelectionMode());
    }

    @Test
    void setHighlight() {
        model.setHighlight(5);
        assertEquals(5, model.getHighlightIndex());
    }

    @Test
    void keyLeft() {
        model.setHighlight(5);
        model.keyLeft();
        assertEquals(4, model.getHighlightIndex());
    }

    @Test
    void keyRight() {
        model.setHighlight(5);
        model.keyRight();
        assertEquals(6, model.getHighlightIndex());
    }

    @Test
    void deleteAt() {
        int idx = 15;
        assertFalse(model.isEmptyCell(idx));
        model.deleteAt(idx);
        assertTrue(model.isEmptyCell(idx));
    }

    @Test
    void deleteAt_collapsesColumnUp_andEmptiesBottom() {
        int col = 1; //for 4x4
        int top = 0 * GridModel.COLS + col;  // 1
        int mid = 1 * GridModel.COLS + col;  // 5
        int low = 2 * GridModel.COLS + col;  // 9
        int bot = 3 * GridModel.COLS + col;  // 13

        assertEquals("1",  model.getCell(top));
        assertEquals("5",  model.getCell(mid));
        assertEquals("9",  model.getCell(low));
        assertEquals("13", model.getCell(bot));

        model.deleteAt(mid);

        assertEquals("1",  model.getCell(top));
        assertEquals("9",  model.getCell(mid));
        assertEquals("13", model.getCell(low));
        assertTrue(model.isEmptyCell(bot));
    }
    @Test
    void setHighlight_outOfBounds_isNoOp() {
        int before = model.getHighlightIndex();
        model.setHighlight(-1);
        model.setHighlight(GridModel.PANELS_COUNT);
        assertEquals(before, model.getHighlightIndex());
    }

    @Test
    void deleteAt_onEmptyCell_isNoOp() {
        int col = 2;
        int bottom = (GridModel.ROWS - 1) * GridModel.COLS + col;

        model.deleteAt(bottom);
        assertTrue(model.isEmptyCell(bottom));

        assertDoesNotThrow(() -> model.deleteAt(bottom));
        assertTrue(model.isEmptyCell(bottom));
    }
    @Test
    void keyRight_skipsEmpty() {
        model.deleteAt(13);
        model.setHighlight(12);
        model.keyRight();
        assertEquals(14, model.getHighlightIndex());
    }

    @Test
    void keyLeft_skipsEmpty() {
        model.deleteAt(13);
        model.setHighlight(14);
        model.keyLeft();
        assertEquals(12, model.getHighlightIndex());
    }
    @Test
    void clickMode_onClick_highlightsOnly() {
        model.toggleMode(); // CLICK
        int idx = 4;
        model.getSelectionMode().onClick(model, idx);
        assertEquals(idx, model.getHighlightIndex());
        assertFalse(model.isEmptyCell(idx));
    }
}