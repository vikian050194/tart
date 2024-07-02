package tart.app;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.*;
import tart.app.components.ButtonFilter;
import tart.app.components.Footer;
import tart.core.DateFilterItemValue;
import tart.core.Filters;
import tart.core.Scanner;
import tart.core.fs.RealFileSystemManager;

public final class App {

    private final int DEFAULT_SCALE = 4;

    private final Scanner scanner;

    private final JFrame frame;

    private double scale = DEFAULT_SCALE;

    private final JLabel image; // TODO use just ImageIcon

    private final JMenuBar bar;

    private final JPanel header;
    private final ButtonFilter yearsFilter;
    private final ButtonFilter monthsFilter;
    private final ButtonFilter daysFilter;
    private final ButtonFilter tagsFilter;

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
                case KeyEvent.VK_ADD:
                    zoomIn();
                    isRenderingNeeded = true;
                    break;
                case KeyEvent.VK_SUBTRACT:
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

    }

    class MenuHandler implements ActionListener {

        private void onOpen() {
            // TODO fix issue with buttons unexpected selection on second opening
            var fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Select directory with images");

            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                scanner.scan(fc.getSelectedFile().getAbsolutePath());

                updateTitle();
                showCurrentImage();

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

            if (command.equals("Exit")) {
                System.exit(0);
            }

            if (command.equals("Open")) {
                onOpen();
                return;
            }

            if (command.equals("Zoom In")) {
                zoomIn();
                showCurrentImage(); // TODO refactoring is needed
                return;
            }

            if (command.equals("Zoom Out")) {  // TODO too many conditional blocks
                zoomOut();
                showCurrentImage(); // TODO refactoring is needed
                return;
            }

            if (command.equals("Reset Zoom")) {
                zoomReset();
                showCurrentImage();
            }

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

        yearActionLisener = (ae) -> {
            if (!scanner.isReady()) {
                return;
            }

            var mask = (String) ae.getActionCommand();

            AbstractButton abstractButton = (AbstractButton) ae.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();

            // TODO temp solutions
            // refactoring is needed
            // filter probably chould not be accessible from App class
            var refilter = selected ? scanner.addYearFilter(mask) : scanner.removeYearFilter(mask);

            if (refilter) {
                scanner.filter(Filters.NONE);
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

            updateTitle();
            showCurrentImage();
        };
        monthActionLisener = (ae) -> {
            if (!scanner.isReady()) {
                return;
            }

            var mask = (String) ae.getActionCommand();

            AbstractButton abstractButton = (AbstractButton) ae.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();

            // TODO temp solutions
            // refactoring is needed
            // filter probably chould not be accessible from App class
            var refilter = selected ? scanner.addMonthFilter(mask) : scanner.removeMonthFilter(mask);

            if (refilter) {
                scanner.filter(Filters.NONE);
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

            updateTitle();
            showCurrentImage();
        };
        dayActionLisener = (ae) -> {
            if (!scanner.isReady()) {
                return;
            }

            var mask = (String) ae.getActionCommand();

            AbstractButton abstractButton = (AbstractButton) ae.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();

            // TODO temp solutions
            // refactoring is needed
            // filter probably chould not be accessible from App class
            var refilter = selected ? scanner.addDayFilter(mask) : scanner.removeDayFilter(mask);

            if (refilter) {
                scanner.filter(Filters.NONE);
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

            updateTitle();
            // TODO fix the-same-day-another-year issue
            showCurrentImage();
        };
        dirActionLisener = (ae) -> {
            if (!scanner.isReady()) {
                return;
            }

            var mask = (String) ae.getActionCommand();
            System.out.println(mask);
        };

        // TODO extract magic numbers or refactor it
        var headerLayout = new GridLayout(4, 1);

        header = new JPanel(headerLayout);

        yearsFilter = new ButtonFilter("Years", yearActionLisener);
        monthsFilter = new ButtonFilter("Months", monthActionLisener);
        daysFilter = new ButtonFilter("Days", dayActionLisener);
        tagsFilter = new ButtonFilter("Dirs", dirActionLisener);

        tagsFilter.setEnabled(false);

        header.add(yearsFilter);
        header.add(monthsFilter);
        header.add(daysFilter);
        header.add(tagsFilter);

        var emptyList = List.of(new DateFilterItemValue[0]);
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

        var save = file.add(new JMenuItem("Save", KeyEvent.VK_S));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(handler);

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

    private void updateComboYear(List<DateFilterItemValue> years) {
        yearsFilter.setButtons(years);
    }

    private void updateComboMonth(List<DateFilterItemValue> months) {
        monthsFilter.setButtons(months);
    }

    private void updateComboDay(List<DateFilterItemValue> days) {
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
        updateZoom(-0.1);
    }

    private void zoomOut() {
        updateZoom(0.1);
    }

    private void zoomReset() {
        scale = DEFAULT_SCALE;
        showCurrentImage();
    }

    private void previousImage() {
        scanner.gotoPreviousFile();

        updateTitle();
        showCurrentImage();
    }

    private void nextImage() {
        // TODO use SwingWorker
        scanner.gotoNextFile();

        updateTitle();
        showCurrentImage();
    }

    private ImageIcon getScaledImageIcon(ImageIcon source, int w, int h) {
        Image image = source.getImage();
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_FAST); // TODO calculate scaled w and h inside function
        var result = new ImageIcon(newimg);
        return result;
    }

    private void showCurrentImage() {
        var file = scanner.getFile();
        var icon = new ImageIcon(file.getAbsolutePath(), file.getName());
        var w = icon.getIconWidth();
        var h = icon.getIconHeight();
        ImageIcon scaledIcon = getScaledImageIcon(icon, (int) (w / scale), (int) (h / scale));

        image.setIcon(scaledIcon);
        image.setHorizontalAlignment(JLabel.CENTER);

        // TODO move this code to ceparate method and call it from filter handlers (probably)
        var pathChunks = file.getAbsolutePath()
                .substring(scanner.getRoot().getAbsolutePath().length())
                .split("/");
        var partChunksStream = Stream.of(pathChunks)
                .filter(c -> !c.isEmpty() && !c.equals(file.getName()))
                .map((p) -> new DateFilterItemValue(p, false, false));
        tagsFilter.setButtons(partChunksStream.toList());

        footer.setTotal(scanner.getFilesCount());
        footer.setIndex(scanner.getFileIndex() + 1);
    }

    private void updateTitle() {

        if (scanner.isReady()) {
            var rootDirs = scanner.getRoot().getAbsolutePath().split("/");
            var endDir = rootDirs[rootDirs.length - 1];

            frame.setTitle(String.format("%s - Tart ", endDir));

            if (scanner.getFile() != null) {
                var fileName = scanner.getFile().getName();

                frame.setTitle(String.format("%s - %s - Tart", fileName, endDir));
            }

            return;
        }

        // TODO extract title value
        frame.setTitle("Tart");
    }
}
