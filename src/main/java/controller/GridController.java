package main.java.controller;

import main.java.model.GridModel;
import main.java.model.SelectionMode;
import main.java.model.ModelObserver;
import main.java.view.GridRenderData;
import main.java.view.GridView;

import javax.swing.*;

public class GridController implements ModelObserver {
    private final GridModel model = new GridModel();
    private final GridView view;
    private final JFrame frame;

    public GridController(int width, int height) {
        this.view = new GridView(width, height, GridModel.ROWS, GridModel.COLS, GridModel.PANELS_COUNT);
        this.frame = new JFrame();

        model.addObserver(this);

        //view -> model
        view.setOnLeft(() -> model.keyLeft());
        view.setOnRight(() -> model.keyRight());
        view.setOnToggle(() -> model.toggleMode());
        view.setOnClick(idx -> model.getSelectionMode().onClick(model, idx));
        view.setOnEnter(idx -> {
            if (model.getSelectionMode() == SelectionMode.HOVER) {
                model.getSelectionMode().onEnter(model, idx);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();

        refresh();
    }

    //model -> view
    @Override
    public void onModelChanged() {
        refresh();
    }

    private void refresh() {
        GridRenderData data = new GridRenderData(
                GridModel.ROWS,
                GridModel.COLS,
                GridModel.PANELS_COUNT,
                model.getHighlightIndex(),
                model.getSelectionMode().toString(),
                collectCells()
        );
        view.render(data);

        String active = model.isEmptyCell(model.getHighlightIndex()) ? "-" : model.getCell(model.getHighlightIndex());
        frame.setTitle("Selected cell: " + active);
    }

    private String[] collectCells() {
        String[] arr = new String[GridModel.PANELS_COUNT];
        for (int i = 0; i < GridModel.PANELS_COUNT; i++) arr[i] = model.getCell(i);
        return arr;
    }

    public void run() { frame.setVisible(true); }
}
