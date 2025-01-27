package tart.app;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tart.app.components.Transporter;
import tart.app.components.filter.*;
import tart.app.components.footer.*;
import tart.app.components.zoom.*;
import tart.core.fs.RealFileSystemManager;
import tart.core.logger.Logger;

public final class Compositor implements ChangeListener {

    private final static String APP_TITLE = "Tart";
    private final int DEFAULT_SCALE = 25;
//    private final double DEFAULT_SCALE_STEP = 0.1;

    private final AppModel model;

    private final JFrame frame;

    private final Zoom zoomComp;

    private final JMenuBar bar;

    private final JPanel header;
    private final Filter yearsFilter;
    private final Filter monthsFilter;
    private final Filter daysFilter;
    private final Filter dirsFilter;

    private final Transporter transporter;
    private final List<File> directions;

    private final ActionListener transportActionListener;

    private final Footer footer;

    @Override
    public void stateChanged(ChangeEvent ce) {
        updateTitle();
    }

    private final class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            var key = ke.getKeyCode();

            var msg = String.format("%c key is pressed", (char) key);
            Logger.getLogger().finest(msg);

            switch (key) {
                case KeyEvent.VK_LEFT: {
                    previousImage();
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    nextImage();
                    break;
                }
                case KeyEvent.VK_DELETE: {
                    deleteImage();
                    break;
                }
                case KeyEvent.VK_0:
                case KeyEvent.VK_1:
                case KeyEvent.VK_2:
                case KeyEvent.VK_3:
                case KeyEvent.VK_4:
                case KeyEvent.VK_5:
                case KeyEvent.VK_6:
                case KeyEvent.VK_7:
                case KeyEvent.VK_8:
                case KeyEvent.VK_9:
                case KeyEvent.VK_NUMPAD0:
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_NUMPAD9: {
                    var index = 0;
                    // TODO simplify conditions
                    if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {
                        index = key - KeyEvent.VK_0;
                    }

                    if (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) {
                        index = key - KeyEvent.VK_NUMPAD0;
                    }

                    File direction = directions.get(index);

                    if (direction == null) {
                        transporter.getButton(index).doClick();
                        direction = directions.get(index);
                    }

                    // TODO is scanner ready?
                    model.moveTo(direction);
                    break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    class MenuHandler implements ActionListener {

        private void onOpen() {
            Logger.getLogger().finest("open directory dialog is called");

            var fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Select directory with images");

            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                var absPath = fc.getSelectedFile().getAbsolutePath();
                model.scan(absPath);

                // TODO refactor reset method usage - just set full array of empty values?
                // TODO disable on close
                transporter.setEnabled(true);
                transporter.reset();

                var msg = String.format("%s directory is selected", absPath);
                Logger.getLogger().finest(msg);
            } else {
                Logger.getLogger().finest("directory selection is failed");
                // TODO show message
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            var command = ae.getActionCommand();

            var msg = String.format("%s action is performed", command);
            Logger.getLogger().finest(msg);

            // TODO extract magic strings
            switch (command) {
                case "Exit": {
                    System.exit(0);
                    break;
                }
                case "Open": {
                    onOpen();
                    break;
                }
                case "Next": {
                    nextImage();
                    break;
                }
                case "Previous": {
                    previousImage();
                    break;
                }
                case "Reset Zoom": {
                    zoomReset();
                    break;
                }
                case "Delete": {
                    deleteImage();
                    break;
                }
                default:
                    throw new AssertionError();
            }

        }

    }

    class TransporterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Logger.getLogger().finest("transporter is called");

            var index = (String) ae.getActionCommand();
            var i = parseInt(index);

            var msg = String.format("%d transporter is used", i);
            Logger.getLogger().finest(msg);

            var button = (JButton) ae.getSource();

            var fc = new JFileChooser();
            fc.setCurrentDirectory(model.getRoot());
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Select directory with images");

            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                var target = fc.getSelectedFile();

                directions.set(i, target);

                button.setText(String.format("%d: %s", i, target.getName()));

                var successMsg = String.format("%d transporter target directory is %s", i, target.getAbsolutePath());
                Logger.getLogger().finest(successMsg);
            } else {
                // TODO show message
                var failMsg = String.format("%d transporter target directory selection is failed", i);
                Logger.getLogger().finest(failMsg);
            }
        }

    }

    public Compositor() {
        try {
            // TODO test all available L&F
            // TODO select L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger().finest(ex.toString());
        }

        UIManager.getDefaults().put("ZoomComponentUI", BasicZoomUI.class.getName());
//        UIManager.getDefaults().put("FooterUI", BasicFooterUI.class.getName());
//        UIManager.getDefaults().put("MultiFilterComponentUI", BasicMultiFilterUI.class.getName());
        String title = "Tart";
        model = new AppModel(new RealFileSystemManager());
        model.addChangeListener(this);

        DefaultZoomModel zoomModel = new DefaultZoomModel();
        zoomModel.setValue(DEFAULT_SCALE);
        zoomComp = new Zoom(model);
        zoomComp.setModel(zoomModel);

        JSlider slider = new JSlider(zoomModel);
        // TODO Remove focusable-false hack
        slider.setFocusable(false);

        var menuHandler = new MenuHandler();
        var keyHandler = new KeyHandler();

        bar = new JMenuBar();

        var filtersLayout = new FlowLayout();
        filtersLayout.setAlignment(FlowLayout.LEFT);

        transportActionListener = new TransporterActionListener()::actionPerformed;

        var initialRowsCount = 1;
        var singleColumn = 1;
        var headerLayout = new GridLayout(initialRowsCount, singleColumn);

        header = new JPanel(headerLayout);

        yearsFilter = new YearFilter(model);
        monthsFilter = new MonthFilter(model);
        daysFilter = new DayFilter(model);
        dirsFilter = new DirFilter(model);

        var count = 10;
        transporter = new Transporter(count, transportActionListener);
        directions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            directions.add(null);
        }

        header.add(yearsFilter);
        header.add(monthsFilter);
        header.add(daysFilter);
        header.add(dirsFilter);
        header.add(transporter);

        // TODO refactor it if possible
        headerLayout.setRows(header.getComponents().length);

        makeFileMenu(menuHandler);
        makeImageMenu(menuHandler);
        makeViewMenu(menuHandler);

        frame = new JFrame(title);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addKeyListener(keyHandler);

        frame.setLayout(new BorderLayout());

        // TODO extract magic numbers
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(bar);

        footer = new Footer(model);

        frame.add(header, BorderLayout.NORTH);
        frame.add(new JScrollPane(zoomComp), BorderLayout.CENTER);
        // TODO uncomment footer
        frame.add(footer, BorderLayout.SOUTH);
        // TODO move slider somewhere - inside component?
//        frame.add(slider, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void makeFileMenu(MenuHandler handler) {
        var file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        var open = file.add(new JMenuItem("Open", KeyEvent.VK_O));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        open.addActionListener(handler);

        var close = file.add(new JMenuItem("Close", KeyEvent.VK_C));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        close.addActionListener(handler);
        close.setEnabled(false);

        var save = file.add(new JMenuItem("Save", KeyEvent.VK_S));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(handler);
        save.setEnabled(false);

        var exit = file.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exit.addActionListener(handler);

        bar.add(file);
    }

    private void makeImageMenu(MenuHandler handler) {
        var image = new JMenu("Image");
        image.setMnemonic(KeyEvent.VK_V);

        var next = image.add(new JMenuItem("Next", KeyEvent.VK_N));
        next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK));
        next.addActionListener(handler);

        var previous = image.add(new JMenuItem("Previous", KeyEvent.VK_P));
        previous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK));
        previous.addActionListener(handler);

        image.addSeparator();

        var delete = image.add(new JMenuItem("Delete", KeyEvent.VK_D));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK));
        delete.addActionListener(handler);

        bar.add(image);
    }

    private void makeViewMenu(MenuHandler handler) {
        var view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_V);

        var zoomIn = view.add(new JMenuItem("Zoom In", KeyEvent.VK_I));
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        zoomIn.addActionListener(handler);

        var zoomOut = view.add(new JMenuItem("Zoom Out", KeyEvent.VK_U));
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        zoomOut.addActionListener(handler);

        var resetZoom = view.add(new JMenuItem("Reset Zoom", KeyEvent.VK_R));
        resetZoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        resetZoom.addActionListener(handler);

        bar.add(view);
    }

    private void zoomReset() {
        zoomComp.getModel().setValue(DEFAULT_SCALE);
        var msg = String.format("zoom is reseted to %d", DEFAULT_SCALE);
        Logger.getLogger().finest(msg);
    }

    private void previousImage() {
        model.gotoPreviousFile();
        Logger.getLogger().finest("previous image is selected");
    }

    private void nextImage() {
        // TODO use SwingWorker
        model.gotoNextFile();
        Logger.getLogger().finest("next image is selected");
    }

    private void deleteImage() {
        model.deleteFile();
        Logger.getLogger().finest("current image is deleted");
    }

    private void updateTitle() {
        var elements = new ArrayList<String>();

        if (model.isReady()) {
            var rootDirs = model.getRoot().getAbsolutePath().split("/");
            var endDir = rootDirs[rootDirs.length - 1];

            if (model.getFile() != null) {
                // TODO getFile two times in a row
                var fileName = model.getFile().getFile().getName();

                elements.add(fileName);
            }

            elements.add(endDir);
        }

        elements.add(APP_TITLE);

        var title = String.join(" - ", elements);

        frame.setTitle(title);
    }
}
