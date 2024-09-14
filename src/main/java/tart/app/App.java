package tart.app;

import javax.swing.SwingUtilities;

public final class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Compositor::new);
    }
}
