package main.java.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.function.IntConsumer;

public class GridView extends JPanel {
    private static final Border BORDER_BLUE = BorderFactory.createLineBorder(Color.blue);
    private static final Border BORDER_GREEN = BorderFactory.createLineBorder(Color.green, 3);
    private static final float CELL_FONT_PT = 30f;

    private final int rows;
    private final int cols;
    private final int panelsCount;

    private final JLabel modeLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel[] panels;

    private final String[] lastTexts;
    private int lastHighlight = -1;

    private Runnable onLeft, onRight, onToggle;
    private IntConsumer onClick, onEnter;

    public GridView(int width, int height, int rows, int cols, int panelsCount) {
        this.rows = rows;
        this.cols = cols;
        this.panelsCount = panelsCount;
        this.panels = new JLabel[panelsCount];
        this.lastTexts = new String[panelsCount];

        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        add(modeLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(rows, cols));
        Font myFont = getFont().deriveFont(Font.PLAIN, CELL_FONT_PT );

        for (int i = 0; i < panelsCount; i++) {
            final int idx = i;
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            lbl.setFont(myFont);
            lbl.setBorder(BORDER_BLUE);
            lbl.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) && onClick != null) onClick.accept(idx);
                }
                @Override public void mouseEntered(MouseEvent e) {
                    if (onEnter != null) onEnter.accept(idx);
                }
            });
            panels[i] = lbl;
            grid.add(lbl);
        }
        add(grid, BorderLayout.CENTER);
        setupKeyBindings();
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    public void render(GridRenderData d) {
        modeLabel.setText("Mode: " + d.getModeText() + " (SPACE to toggle)");

        int newHighlight = d.getHighlightIndex();
        for (int i = 0; i < panelsCount; i++) {
            String newText = d.getCell(i);
            boolean textChanged = !Objects.equals(lastTexts[i], newText);

            boolean oldHighlighted = (i == lastHighlight) && !isEmpty(lastTexts[i]);
            boolean newHighlighted = (i == newHighlight) && !d.isEmptyCell(i);
            boolean borderChanged = (oldHighlighted != newHighlighted);

            if (textChanged) {
                panels[i].setText(newText);
                lastTexts[i] = newText;
            }
            if (borderChanged) {
                panels[i].setBorder(newHighlighted ? BORDER_GREEN : BORDER_BLUE);
            }
        }

        lastHighlight = newHighlight;
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        am.put("moveLeft", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { if (onLeft != null) onLeft.run(); }
        });
        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        am.put("moveRight", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { if (onRight != null) onRight.run(); }
        });
        im.put(KeyStroke.getKeyStroke("SPACE"), "toggleMode");
        am.put("toggleMode", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { if (onToggle != null) onToggle.run(); }
        });
    }

    public void setOnLeft(Runnable r)  { onLeft = r; }
    public void setOnRight(Runnable r) { onRight = r; }
    public void setOnToggle(Runnable r){ onToggle = r; }
    public void setOnClick(IntConsumer c){ onClick = c; }
    public void setOnEnter(IntConsumer c){ onEnter = c; }
}


