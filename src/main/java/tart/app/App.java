package tart.app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import tart.core.Scanner;

public final class App implements ActionListener {

    private final Scanner scanner;

    private final JFrame frame;

    private JLabel displayPhoto;

    private final JMenuBar bar;
    private final JComboBox<String> filterYear; // TODO: rename to comboYear
    private final JComboBox<String> filterMonth; // TODO: rename to comboMonth
//
//    private final Panel header;
//    private final Panel filters;
//    private final Panel tags;
//    private final LoadedImage image;
//
//    private final CheckboxMenuItem viewHidden;
//    private final CheckboxMenuItem viewDebug;
//
//    private double scale = 3;
//
//    public App() {
//        scanner = new Scanner();
//
//        setLayout(new BorderLayout());
//        setFocusable(true);
//
//        header = new Panel(new GridLayout(0, 1));
////        header.addKeyListener(this);
//
//        // TODO fix focus issue
//        filters = new Panel(new FlowLayout(FlowLayout.LEFT, 4, 4));
//        // TODO extract panel leading label
//        filters.add(new Label("Filters:"));
//        filters.add(filterYear);
//        filters.add(filterMonth);
//        header.add(filters);
//
//        tags = new Panel(new FlowLayout(FlowLayout.LEFT, 4, 4));
//        // TODO extract panel leading label
//        tags.add(new Label("Tags:"));
//        header.add(tags);
//        add(header, BorderLayout.NORTH);
//
//        image = new LoadedImage();
//        add(image, BorderLayout.CENTER);
////        image.setFocusable(false);
//
//        var next = new Button(">");
//        next.addActionListener((ae) -> nextImage());
//        add(next, BorderLayout.EAST);
//
//        var previous = new Button("<");
//        previous.addActionListener((ae) -> previousImage());
//        add(previous, BorderLayout.WEST);
//
//        var bar = new MenuBar();
//        setMenuBar(bar);
//
//        var file = new Menu("Files");
//
//        MenuItem fileNew = file.add(new MenuItem("New..."));
//
//        var openShortcut = new MenuShortcut(KeyEvent.VK_O, false);
//        MenuItem fileOpen = file.add(new MenuItem("Open...", openShortcut));
//
//        MenuItem fileClose = file.add(new MenuItem("Close"));
//        fileClose.setEnabled(false);
//
//        MenuItem fileSplit = file.add(new MenuItem("-"));
//
//        var quitShortcut = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('Q'), true);
//        MenuItem fileQuit = file.add(new MenuItem("Quit...", quitShortcut));
//
//        bar.add(file);
//
//        var view = new Menu("View");
//
//        var zinShortcut = new MenuShortcut(KeyEvent.VK_EQUALS, false);
//        var viewZoomIn = view.add(new MenuItem("Zoom in", zinShortcut));
//        viewZoomIn.addActionListener((ae) -> zoomIn());
//
//        var zoutShortcut = new MenuShortcut(KeyEvent.VK_MINUS, false);
//        var viewZoomOut = view.add(new MenuItem("Zoom out", zoutShortcut));
//        viewZoomOut.addActionListener((ae) -> zoomOut());
//
//        bar.add(view);
//
//        viewHidden = new CheckboxMenuItem("Hidden");
//        view.add(viewHidden);
//
//        viewDebug = new CheckboxMenuItem("Debug");
//        view.add(viewDebug);
//
//        var handler = new MenuHandler();
//
//        fileNew.addActionListener(handler);
//        fileOpen.addActionListener(handler);
//        fileClose.addActionListener(handler);
//        fileSplit.addActionListener(handler);
//        fileQuit.addActionListener(handler);
//        viewZoomIn.addActionListener(handler);
//        viewZoomOut.addActionListener(handler);
//
//        viewHidden.addItemListener(handler);
//        viewDebug.addItemListener(handler);
//
//        fileQuit.addActionListener((ae) -> System.exit(0));
//
//        fileOpen.addActionListener((ae) -> onOpen());
//
//        addKeyListener(this);
//
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent we) {
//                System.exit(0);
//            }
//        });
//    }
//
//    private void updateZoom(double delta) {
//        scale += delta;
//
//        showCurrentImage();
//    }
//
//    private void zoomIn() {
//        updateZoom(-0.1);
//    }
//
//    private void zoomOut() {
//        updateZoom(0.1);
//    }
//
//    private void previousImage() {
//        scanner.gotoPreviousFile();
//
//        showCurrentImage();
//    }
//
//    private void nextImage() {
//        scanner.gotoNextFile();
//
//        showCurrentImage();
//    }
//
//    private void updateTitle() {
//
//        if (scanner.getRoot() != null) {
//            var rootDirs = scanner.getRoot().getAbsolutePath().split("/");
//            var endDir = rootDirs[rootDirs.length - 1];
//
//            setTitle(String.format("%s - Tart ", endDir));
//
//            if (scanner.getFile() != null) {
//                var fileName = scanner.getFile().getName();
//
//                setTitle(String.format("%s - %s - Tart", fileName, endDir));
//            }
//
//            return;
//        }
//
//        // TODO extract title value
//        setTitle("Tart");
//    }
//
//    private void showCurrentImage() {
//        var file = scanner.getFile();
//        Image img;
//
//        try {
//            img = ImageIO.read(file);
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//
//        var w = img.getWidth(null);
//        var h = img.getHeight(null);
//        var scaledImage = img.getScaledInstance((int) (w / scale), (int) (h / scale), Image.SCALE_FAST);
//
//        image.set(scaledImage);
//
//        var pathChunks = file.getAbsolutePath().substring(scanner.getRoot().getAbsolutePath().length()).split("/");
//        tags.removeAll();
//        // TODO extract panel leading label
//        tags.add(new Label("Tags:"));
//
//        for (String pathChunk : pathChunks) {
//            if (pathChunk.isEmpty()) {
//                continue;
//            }
//
//            if (pathChunk.equals(file.getName())) {
//                continue;
//            }
//
//            tags.add(new Button(String.format("[%s]", pathChunk)));
//        }
//
//        updateTitle();
//
//        setVisible(true);
//    }
//
//    private void onOpen() {
//        var dialog = new FileDialog(this, "Select directory", FileDialog.LOAD);
//        dialog.setVisible(true);
//        var dir = dialog.getDirectory();
//
//        scanner.scan(dir);
//
//        updateTitle();
//        showCurrentImage();
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        var app = new App();
//
//        app.setSize(new Dimension(1280, 720));
//        app.setTitle("Tart");
//        app.setExtendedState(Frame.MAXIMIZED_BOTH);
//        app.setVisible(true);
//    }
//
//    @Override
//    public void keyTyped(KeyEvent ke) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent ke) {
//        var key = ke.getKeyCode();
//
//        var isRenderingNeeded = false;
//
//        switch (key) {
//            case KeyEvent.VK_LEFT:
//                previousImage();
//                isRenderingNeeded = true;
//                break;
//            case KeyEvent.VK_RIGHT:
//                nextImage();
//                isRenderingNeeded = true;
//                break;
//            case KeyEvent.VK_PLUS:
//                zoomIn();
//                isRenderingNeeded = true;
//                break;
//            case KeyEvent.VK_MINUS:
//                zoomOut();
//                isRenderingNeeded = true;
//                break;
//        }
//
//        if (isRenderingNeeded) {
//            showCurrentImage();
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent ke) {
//
//    }
//
//    @Override
//    public void itemStateChanged(ItemEvent ie) {
//
//    }
//
//    class MenuHandler implements ActionListener, ItemListener {
//
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            var isDebug = viewDebug.getState();
//
//            var command = ae.getActionCommand();
//
//            if (isDebug) {
//                System.out.printf(command);
//            }
//
//            repaint();
//        }
//
//        @Override
//        public void itemStateChanged(ItemEvent ie) {
//            repaint();
//        }
//    }

    public App() {
        String title = "Tart";
        String msg = "Hello, World!";

        scanner = new Scanner();

        bar = new JMenuBar();

        filterYear = new JComboBox<>();
        filterMonth = new JComboBox<>();

        makeFileMenu();

        frame = new JFrame(title);

        frame.setLayout(new BorderLayout());

        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(bar);

        displayPhoto = new JLabel(msg);

        frame.add(displayPhoto);
        frame.setVisible(true);
    }

    private void makeFileMenu() {
        var file = new JMenu("File");

        file.setMnemonic(KeyEvent.VK_F);
        var open = file.add(new JMenuItem("Open", KeyEvent.VK_O));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        open.addActionListener(this);
        var close = file.add(new JMenuItem("Close", KeyEvent.VK_C));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        close.addActionListener(this);
        var save = file.add(new JMenuItem("Save", KeyEvent.VK_S));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(this);
        var exit = file.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exit.addActionListener(this);

        bar.add(file);
    }

    private void updateComboYear(String[] years) {
        filterYear.removeAllItems();

        for (String year : years) {
            filterYear.addItem(year);
        }
    }

    private void updateComboMonth(String[] months) {
        filterMonth.removeAllItems();

        for (String month : months) {
            filterMonth.addItem(month);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        var command = ae.getActionCommand();

        if (command.equals("Exit")) {
            System.exit(0);
        }

        if (command.equals("Open")) {
            var fc = new JFileChooser();
            fc.showOpenDialog(frame);
            scanner.scan(fc.getSelectedFile().getParent());
            frame.setTitle(String.format("%d files", scanner.getFilesCount()));

            var file = scanner.getFile();
            displayPhoto.setIcon(new ImageIcon(file.getAbsolutePath(), file.getName()));
            displayPhoto.setHorizontalAlignment(JLabel.CENTER);
            displayPhoto.setText(file.getName());

            return;
        }

        frame.setTitle(ae.getActionCommand());
    }
}
