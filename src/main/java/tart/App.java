package tart;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;
import javax.imageio.ImageIO;
import tart.matcher.*;

public class App extends Frame implements KeyListener {

    private File root;

    private final Label lab;
    private final LoadedImage lim;
    private final Panel tags;

    private final CheckboxMenuItem viewHidden;
    private final CheckboxMenuItem viewDebug;

    private double scale = 3;

    private final ArrayList<File> files = new ArrayList<>();
    private int index = 0;

    public App() {
        setLayout(new BorderLayout());
        setFocusable(true);

        lab = new Label("<EMPTY>");
        add(lab, BorderLayout.NORTH);

        lim = new LoadedImage();
        add(lim, BorderLayout.CENTER);
        lim.setFocusable(false);

        tags = new Panel();
        add(tags, BorderLayout.SOUTH);

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
        index--;

        if (index < 0) {
            index = files.size() - 1;
        }

        showCurrentImage();
    }

    private void nextImage() {
        index++;

        if (index >= files.size()) {
            index = 0;
        }

        showCurrentImage();
    }

    private void showCurrentImage() {
        var file = files.get(index);
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

        lim.set(scaledImage);
        lab.setText(file.getName());

        var pathChunks = file.getAbsolutePath().substring(root.getAbsolutePath().length()).split("/");
        tags.removeAll();

        for (String pathChunk : pathChunks) {
            if (pathChunk.isEmpty()) {
                continue;
            }

            if (pathChunk.equals(file.getName())) {
                continue;
            }

            tags.add(new Button(String.format("[%s]", pathChunk)));
        }

        setVisible(true);
    }

    public List<File> listFiles(File dir, FileMatcher fileMather) {
        var result = new ArrayList<File>();
        var queue = new LinkedList<File>();
        queue.add(dir);

        while (!queue.isEmpty()) {
            var currentDir = queue.poll();
            var dirs = Stream.of(currentDir.listFiles())
                    .filter(file -> file.isDirectory())
                    .collect(Collectors.toList());
            queue.addAll(dirs);
            var currentDirFiles = Stream.of(currentDir.listFiles())
                    .filter(file -> !file.isDirectory() && fileMather.isMatch(file))
                    .collect(Collectors.toList());
            result.addAll(currentDirFiles);
        }

        return result;
    }

    private List<File> sortFiles(List<File> source) {
        var result = new ArrayList<File>(source.size());
        result.addAll(source);
        result.sort((a, b) -> a.getName().compareTo(b.getName()));
        return result;
    }

    private void onOpen() {
        var dialog = new FileDialog(this, "Select directory", FileDialog.LOAD);
        dialog.setVisible(true);
        var dir = dialog.getDirectory();

        var dirs = dir.split("/");
        var endDir = dirs[dirs.length - 1];
        setTitle(String.format("%s - Tart ", endDir));

        var rootDir = new File(dir);

        if (!rootDir.exists()) {
            System.out.printf("%s not exists%n", dir);
            return;
        }

        root = rootDir;

        var unsortedFiles = listFiles(rootDir, new AllFileMatcher());
        var sortedFiles = sortFiles(unsortedFiles);
        files.addAll(sortedFiles);

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
