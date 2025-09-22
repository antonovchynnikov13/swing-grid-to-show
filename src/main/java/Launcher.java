package main.java;

import main.java.controller.GridController;

public class Launcher {
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new GridController(WINDOW_WIDTH, WINDOW_HEIGHT).run());
    }
}

