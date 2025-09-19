package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * Main task window.
 * Sets up the panel and places all elements inside.
 */
public class MyContainer extends JPanel {
    public static final int PANELS_COUNT = 16;
    private static final int COLS = 4;
    private static final int ROWS = 4;

    private static final Border BORDER_BLUE = BorderFactory.createLineBorder(Color.blue);
    private static final Border BORDER_GREEN = BorderFactory.createLineBorder(Color.green, 3);

    private SelectionMode selectionMode = SelectionMode.HOVER;

    private JLabel modeLabel;
    int highlightIndex = 6;

    JLabel[] panels = new JLabel[PANELS_COUNT];
    private final MainWindow window;

    public MyContainer(int windowWidth, int windowHeight, MainWindow window) {

        this.window = window;
        validateConfig();
        this.setSize(windowWidth, windowHeight);
        this.setLayout(new BorderLayout());

        modeLabel = new JLabel("Mode: " + selectionMode + " To change press SPACE", SwingConstants.CENTER);
        this.add(modeLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));
        setupKeyListener();
        Font labelFont = this.getFont();
        Font myFont = new Font(labelFont.getName(), Font.PLAIN, 30);

        for (int i = 0; i < PANELS_COUNT; i++) {
            panels[i] = new JLabel();
            panels[i].setFont(myFont);
            panels[i].setText(String.valueOf(i));
            panels[i].setHorizontalAlignment(SwingConstants.CENTER);
            panels[i].setBorder(BorderFactory.createLineBorder(Color.blue));
            panels[i].addMouseListener(createSelectionListener(i));
            gridPanel.add(panels[i]);

        }
        this.add(gridPanel, BorderLayout.CENTER);
        updateView();
    }

    private MouseAdapter createSelectionListener(int index) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEmptyCell(index)) return;
                if (selectionMode == SelectionMode.CLICK && SwingUtilities.isLeftMouseButton(e)) {
                    highlightIndex = index;
                    updateView();
                } else if (selectionMode == SelectionMode.HOVER && SwingUtilities.isLeftMouseButton(e)) {
                    highlightIndex = index;
                    deleteAt(index);
                    updateView();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (selectionMode == SelectionMode.HOVER && !isEmptyCell(index)) {
                    highlightIndex = index;
                    updateView();
                }
            }

        };
    }

    private boolean isEmptyCell(int idx) {
        String t = panels[idx].getText();
        return t == null || t.isEmpty();
    }

    public void setSelectionMode(SelectionMode mode) {
        this.selectionMode = mode;
        modeLabel.setText("Mode: " + mode + " To change press SPACE");
    }

    public SelectionMode getSelectionMode() {
        return selectionMode;

    }

    private void updateView() {
        for (int i = 0; i < PANELS_COUNT; i++) {
            if (i == highlightIndex && !isEmptyCell(i)) {
                panels[i].setBorder(BORDER_GREEN);
            } else {
                panels[i].setBorder(BORDER_BLUE);
            }
        }

        String activeText;
        if (!isEmptyCell(highlightIndex)) {
            activeText = panels[highlightIndex].getText();
        } else {
            activeText = "-";
        }
        window.setTitle("Selected cell: " + activeText);
    }


    private void deleteAt(int i) {
        int col = i % COLS;
        int row = i / COLS;

        int bottomRow = -1;
        for (int idx = col; idx < PANELS_COUNT; idx += COLS) {
            bottomRow = idx / COLS;
        }

        for (int r = row; r < bottomRow; r++) {
            int from = (r + 1) * COLS + col;
            int to = r * COLS + col;
            panels[to].setText(panels[from].getText());
        }

        int bottomIndex = bottomRow * COLS + col;
        panels[bottomIndex].setText("");

    }

    private void setupKeyListener() {
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_LEFT:
                        keyLeft();
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        keyRight();
                        break;
                    case java.awt.event.KeyEvent.VK_SPACE:
                        setSelectionMode(getSelectionMode() == SelectionMode.HOVER ? SelectionMode.CLICK : SelectionMode.HOVER);
                        break;
                }
            }
        });
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
        updateView();
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
        updateView();
    }

    private void validateConfig() {
        if (COLS <= 0 || ROWS <= 0) {
            throw new IllegalArgumentException("ROWS and COLS must be > 0");
        }
        int capacity = ROWS * COLS;
        if (capacity < PANELS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid configuration: ROWS*COLS = " + ROWS + "x" + COLS + " = " + capacity +
                            " < PANELS_COUNT = " + PANELS_COUNT + ". Increase ROWS/COLS or decrease PANELS_COUNT."
            );
        }
        if (highlightIndex < 0 || highlightIndex >= PANELS_COUNT) {
            throw new IllegalArgumentException("highlightIndex out of bounds: 0.." + (PANELS_COUNT - 1));
        }
    }
}

