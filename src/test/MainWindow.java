package test;


import javax.swing.*;
import java.awt.*;

/**
 * Main application window.
 * Sets up the frame and places the game container inside.
 */


public class MainWindow extends JFrame {
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    MyContainer container;

    public MainWindow() {
        Container cp = this.getContentPane();

        container = new MyContainer(WINDOW_WIDTH, WINDOW_HEIGHT, this);
        cp.add(container);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);
    }
}
