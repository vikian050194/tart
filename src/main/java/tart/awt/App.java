package tart.awt;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tart.awt.components.LoadedImage;
import tart.core.Scanner;

public class App extends Frame implements KeyListener, ItemListener {

    private final Scanner scanner;

    private final Choice filterYear;
    private final Choice filterMonth;

    private final Panel header;
    private final Panel filters;
    private final Panel tags;
    private final LoadedImage image;

    private final CheckboxMenuItem viewHidden;
    private final CheckboxMenuItem viewDebug;

    private double scale = 3;

    public App() {
        scanner = new Scanner();

        setLayout(new BorderLayout());
        setFocusable(true);

        header = new Panel(new GridLayout(0, 1));
//        header.addKeyListener(this);

        filterYear = new Choice();
        var minYear = 2012;
        var maxYear = 2024;

        for (int i = minYear; i <= maxYear; i++) {
            filterYear.add(String.format("%d", i));
        }

        filterMonth = new Choice();
        var minMonth = 1;
        var maxMonth = 12;

        for (int i = minMonth; i <= maxMonth; i++) {
            filterMonth.add(String.format("%d", i));
        }

        // TODO fix focus issue
        filters = new Panel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        // TODO extract panel leading label
        filters.add(new Label("Filters:"));
        filters.add(filterYear);
        filters.add(filterMonth);
        header.add(filters);

        tags = new Panel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        // TODO extract panel leading label
        tags.add(new Label("Tags:"));
        header.add(tags);
        add(header, BorderLayout.NORTH);

        image = new LoadedImage();
        add(image, BorderLayout.CENTER);
//        image.setFocusable(false);

        var next = new Button(">");
        next.addActionListener((ae) -> nextImage());
        add(next, BorderLayout.EAST);

        var previous = new Button("<");
        previous.addActionListener((ae) -> previousImage());
        add(previous, BorderLayout.WEST);

        var bar = new MenuBar();
        setMenuBar(bar);

        var file = new Menu("Files");

        MenuItem fileNew = file.add(new MenuItem("New..."));

        var openShortcut = new MenuShortcut(KeyEvent.VK_O, false);
        MenuItem fileOpen = file.add(new MenuItem("Open...", openShortcut));

        MenuItem fileClose = file.add(new MenuItem("Close"));
        fileClose.setEnabled(false);

        MenuItem fileSplit = file.add(new MenuItem("-"));

        var quitShortcut = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('Q'), true);
        MenuItem fileQuit = file.add(new MenuItem("Quit...", quitShortcut));

        bar.add(file);

        var view = new Menu("View");

        var zinShortcut = new MenuShortcut(KeyEvent.VK_EQUALS, false);
        var viewZoomIn = view.add(new MenuItem("Zoom in", zinShortcut));
        viewZoomIn.addActionListener((ae) -> zoomIn());

        var zoutShortcut = new MenuShortcut(KeyEvent.VK_MINUS, false);
        var viewZoomOut = view.add(new MenuItem("Zoom out", zoutShortcut));
        viewZoomOut.addActionListener((ae) -> zoomOut());

        bar.add(view);

        viewHidden = new CheckboxMenuItem("Hidden");
        view.add(viewHidden);

        viewDebug = new CheckboxMenuItem("Debug");
        view.add(viewDebug);

        var handler = new MenuHandler();

        fileNew.addActionListener(handler);
        fileOpen.addActionListener(handler);
        fileClose.addActionListener(handler);
        fileSplit.addActionListener(handler);
        fileQuit.addActionListener(handler);
        viewZoomIn.addActionListener(handler);
        viewZoomOut.addActionListener(handler);

        viewHidden.addItemListener(handler);
        viewDebug.addItemListener(handler);

        fileQuit.addActionListener((ae) -> System.exit(0));

        fileOpen.addActionListener((ae) -> onOpen());

        addKeyListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    private void updateZoom(double delta) {
        scale += delta;

        showCurrentImage();
    }

    private void zoomIn() {
        updateZoom(-0.1);
    }

    private void zoomOut() {
        updateZoom(0.1);
    }

    private void previousImage() {
        scanner.gotoPreviousFile();

        showCurrentImage();
    }

    private void nextImage() {
        scanner.gotoNextFile();

        showCurrentImage();
    }

    private void updateTitle() {

        if (scanner.getRoot() != null) {
            var rootDirs = scanner.getRoot().getAbsolutePath().split("/");
            var endDir = rootDirs[rootDirs.length - 1];

            setTitle(String.format("%s - Tart ", endDir));

            if (scanner.getFile() != null) {
                var fileName = scanner.getFile().getName();

                setTitle(String.format("%s - %s - Tart", fileName, endDir));
            }

            return;
        }

        // TODO extract title value
        setTitle("Tart");
    }

    private void showCurrentImage() {
        var file = scanner.getFile();
        Image img;

        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        var w = img.getWidth(null);
        var h = img.getHeight(null);
        var scaledImage = img.getScaledInstance((int) (w / scale), (int) (h / scale), Image.SCALE_FAST);

        image.set(scaledImage);

        var pathChunks = file.getAbsolutePath().substring(scanner.getRoot().getAbsolutePath().length()).split("/");
        tags.removeAll();
        // TODO extract panel leading label
        tags.add(new Label("Tags:"));

        for (String pathChunk : pathChunks) {
            if (pathChunk.isEmpty()) {
                continue;
            }

            if (pathChunk.equals(file.getName())) {
                continue;
            }

            tags.add(new Button(String.format("[%s]", pathChunk)));
        }

        updateTitle();

        setVisible(true);
    }

    private void onOpen() {
        var dialog = new FileDialog(this, "Select directory", FileDialog.LOAD);
        dialog.setVisible(true);
        var dir = dialog.getDirectory();

        scanner.scan(dir);

        updateTitle();
        showCurrentImage();

        setVisible(true);
    }

    public static void main(String[] args) {
        var app = new App();

        app.setSize(new Dimension(1280, 720));
        app.setTitle("Tart");
        app.setExtendedState(Frame.MAXIMIZED_BOTH);
        app.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        var key = ke.getKeyCode();

        var isRenderingNeeded = false;

        switch (key) {
            case KeyEvent.VK_LEFT:
                previousImage();
                isRenderingNeeded = true;
                break;
            case KeyEvent.VK_RIGHT:
                nextImage();
                isRenderingNeeded = true;
                break;
            case KeyEvent.VK_PLUS:
                zoomIn();
                isRenderingNeeded = true;
                break;
            case KeyEvent.VK_MINUS:
                zoomOut();
                isRenderingNeeded = true;
                break;
        }

        if (isRenderingNeeded) {
            showCurrentImage();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

    }

    class MenuHandler implements ActionListener, ItemListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            var isDebug = viewDebug.getState();

            var command = ae.getActionCommand();

            if (isDebug) {
                System.out.printf(command);
            }

            repaint();
        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            repaint();
        }
    }
}
