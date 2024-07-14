package tart.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.swing.*;
import tart.app.components.ButtonFilter;
import tart.app.components.Footer;
import tart.core.Mask;
import tart.core.Scanner;
import tart.core.fs.RealFileSystemManager;

public final class App {

    private final static String APP_TITLE = "Tart";
    private final int DEFAULT_SCALE = 4;
    private final double DEFAULT_SCALE_STEP = 0.1;

    private final Scanner scanner;

    private final JFrame frame;

    private double scale = DEFAULT_SCALE;

    private final JLabel image;

    private final JMenuBar bar;

    private final JPanel header;
    private final ButtonFilter yearsFilter;
    private final ButtonFilter monthsFilter;
    private final ButtonFilter daysFilter;
    private final ButtonFilter dirsFilter;

    private final ActionListener yearActionLisener;
    private final ActionListener monthActionLisener;
    private final ActionListener dayActionLisener;
    private final ActionListener dirActionLisener;

    private final Footer footer;

    private final class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            var key = ke.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                    previousImage();
                    break;
                case KeyEvent.VK_RIGHT:
                    nextImage();
                    break;
                case KeyEvent.VK_ADD:
                    zoomIn();
                    break;
                case KeyEvent.VK_SUBTRACT:
                    zoomOut();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    class MenuHandler implements ActionListener {

        private void onOpen() {
            var fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Select directory with images");

            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                scanner.scan(fc.getSelectedFile().getAbsolutePath());

                updateUI();

                updateComboYear(scanner.getYears());
                updateComboMonth(scanner.getMonths());
                updateComboDay(scanner.getDays());

                footer.setTotal(scanner.getFilesCount());
            } else {
// TODO show message
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            var command = ae.getActionCommand();

            switch (command) {
                case "Exit": {
                    System.exit(0);
                    break;
                }
                case "Open": {
                    onOpen();
                    break;
                }
                case "Zoom In": {
                    zoomIn();
                    break;
                }
                case "Zoom Out": {
                    zoomOut();
                    break;
                }
                case "Reset Zoom": {
                    zoomReset();
                    break;
                }
                default:
                    throw new AssertionError();
            }

        }

    }

    class DateFilterListener implements ActionListener {

        private final Consumer<String> addMask;
        private final Consumer<String> removeMask;

        public DateFilterListener(Consumer<String> a, Consumer<String> r) {
            addMask = a;
            removeMask = r;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            var mask = (String) ae.getActionCommand();

            AbstractButton abstractButton = (AbstractButton) ae.getSource();
            ButtonModel buttonModel = abstractButton.getModel();

            if (buttonModel.isSelected()) {
                addMask.accept(mask);
            } else {
                removeMask.accept(mask);
            }

            if (scanner.isYearsUpdated()) {
                updateComboYear(scanner.getYears());
            }

            if (scanner.isMonthsUpdated()) {
                updateComboMonth(scanner.getMonths());
            }

            if (scanner.isDaysUpdated()) {
                updateComboDay(scanner.getDays());
            }

            updateUI();
        }

    }

    public App() {
        String title = "Tart";

        scanner = new Scanner(new RealFileSystemManager());
        var menuHandler = new MenuHandler();
        var keyHandler = new KeyHandler();

        bar = new JMenuBar();

        var filtersLayout = new FlowLayout();
        filtersLayout.setAlignment(FlowLayout.LEFT);

        yearActionLisener = new DateFilterListener(scanner::addYearFilter, scanner::removeYearFilter)::actionPerformed;
        monthActionLisener = new DateFilterListener(scanner::addMonthFilter, scanner::removeMonthFilter)::actionPerformed;
        dayActionLisener = new DateFilterListener(scanner::addDayFilter, scanner::removeDayFilter)::actionPerformed;
        dirActionLisener = (ae) -> {
            if (!scanner.isReady()) {
                return;
            }

            var mask = (String) ae.getActionCommand();
            System.out.println(mask);
        };

        var initialRowsCount = 1;
        var singleColumn = 1;
        var headerLayout = new GridLayout(initialRowsCount, singleColumn);

        header = new JPanel(headerLayout);

        yearsFilter = new ButtonFilter("Years", yearActionLisener);
        monthsFilter = new ButtonFilter("Months", monthActionLisener);
        daysFilter = new ButtonFilter("Days", dayActionLisener);
        dirsFilter = new ButtonFilter("Dirs", dirActionLisener);

        dirsFilter.setEnabled(false);

        header.add(yearsFilter);
        header.add(monthsFilter);
        header.add(daysFilter);
        header.add(dirsFilter);

        // TODO refactor it if possible
        headerLayout.setRows(header.getComponents().length);

        var emptyList = List.of(new Mask[0]);
        updateComboYear(emptyList);
        updateComboMonth(emptyList);
        updateComboDay(emptyList);

        makeFileMenu(menuHandler);
        makeViewMenu(menuHandler);

        frame = new JFrame(title);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addKeyListener(keyHandler);

        frame.setLayout(new BorderLayout());

        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(bar);

        image = new JLabel();

        footer = new Footer();

        frame.add(header, BorderLayout.NORTH);
        frame.add(image, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);

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

    private void updateUI() {
        updateTitle();
        showCurrentImage();
        updateFooter();
        updateDirsFilter();
    }

    private void updateComboYear(List<Mask> years) {
        yearsFilter.setButtons(years);
    }

    private void updateComboMonth(List<Mask> months) {
        monthsFilter.setButtons(months);
    }

    private void updateComboDay(List<Mask> days) {
        daysFilter.setButtons(days);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

    private void updateZoom(double delta) {
        scale += delta;
        showCurrentImage();
    }

    private void zoomIn() {
        updateZoom(-DEFAULT_SCALE_STEP);
    }

    private void zoomOut() {
        updateZoom(DEFAULT_SCALE_STEP);
    }

    private void zoomReset() {
        updateZoom(DEFAULT_SCALE - scale);
    }

    private void previousImage() {
        scanner.gotoPreviousFile();

        updateUI();
    }

    private void nextImage() {
        // TODO use SwingWorker
        scanner.gotoNextFile();

        updateUI();
    }

    private ImageIcon getScaledImageIcon(ImageIcon source) {
        var initialW = source.getIconWidth();
        var initialH = source.getIconHeight();
        var scaledW = (int) (initialW / scale);
        var scaledH = (int) (initialH / scale);
        var sourceImage = source.getImage();
        var newimg = sourceImage.getScaledInstance(scaledW, scaledH, Image.SCALE_FAST);
        var result = new ImageIcon(newimg);
        return result;
    }

    private void updateFooter() {
        footer.setTotal(scanner.getFilesCount());
        footer.setIndex(scanner.getFileIndex() + 1);
    }

    private void updateDirsFilter() {
        var file = scanner.getFile();

        var pathChunks = file.getAbsolutePath()
                .substring(scanner.getRoot().getAbsolutePath().length())
                .split("/");
        var partChunksStream = Stream.of(pathChunks)
                .filter(c -> !c.isEmpty() && !c.equals(file.getName()))
                .map((p) -> new Mask(p, false, false));

        dirsFilter.setButtons(partChunksStream.toList());
    }

    private void showCurrentImage() {
        var file = scanner.getFile();
        var icon = new ImageIcon(file.getAbsolutePath(), file.getName());
        ImageIcon scaledIcon = getScaledImageIcon(icon);

        image.setIcon(scaledIcon);
        image.setHorizontalAlignment(JLabel.CENTER);
    }

    private void updateTitle() {
        var elements = new ArrayList<String>();

        if (scanner.isReady()) {
            var rootDirs = scanner.getRoot().getAbsolutePath().split("/");
            var endDir = rootDirs[rootDirs.length - 1];

            if (scanner.getFile() != null) {
                var fileName = scanner.getFile().getName();

                elements.add(fileName);
            }

            elements.add(endDir);
        }

        elements.add(APP_TITLE);

        var title = String.join(" - ", elements);

        frame.setTitle(title);
    }
}
