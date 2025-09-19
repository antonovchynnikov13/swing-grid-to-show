package test;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();  // Let the constructor do the job
            }
        });
    }
}
