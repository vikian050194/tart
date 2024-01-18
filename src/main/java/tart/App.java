package tart;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import tart.matcher.*;
import tart.matcher.data.*;
import tart.matcher.type.*;
import tart.matcher.wrapper.*;
import tart.normalizer.*;

public class App extends Frame {

    CheckboxMenuItem debug;
    CheckboxMenuItem test;

    private final HashMap<String, Integer> distinctFiles = new HashMap<>();

    public App() {
        setLayout(new GridLayout(0, 1, 0, 10));

        var bar = new MenuBar();
        setMenuBar(bar);

        var file = new Menu("File");
        MenuItem i1 = file.add(new MenuItem("New..."));
        var openShortcut = new MenuShortcut(KeyEvent.VK_O, false);
        MenuItem i2 = file.add(new MenuItem("Open...", openShortcut));
        MenuItem i3 = file.add(new MenuItem("Close"));
        MenuItem i4 = file.add(new MenuItem("-"));
        var quitShortcut = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('Q'), true);
        MenuItem i5 = file.add(new MenuItem("Quit...", quitShortcut));
        bar.add(file);

        var edit = new Menu("Edit");
        MenuItem i6 = edit.add(new MenuItem("Cut"));
        MenuItem i7 = edit.add(new MenuItem("Copy"));
        MenuItem i8 = edit.add(new MenuItem("Paste"));
        MenuItem i9 = edit.add(new MenuItem("-"));

        var sub = new Menu("Special");
        var i10 = sub.add(new MenuItem("First"));
        var i11 = sub.add(new MenuItem("Second"));
        var i12 = sub.add(new MenuItem("Third"));
        edit.add(sub);

        debug = new CheckboxMenuItem("Debug");
        edit.add(debug);
        test = new CheckboxMenuItem("Test");
        edit.add(test);

        bar.add(edit);

        var handler = new MenuHandler();

        i1.addActionListener(handler);
        i2.addActionListener(handler);
        i3.addActionListener(handler);
        i4.addActionListener(handler);
        i5.addActionListener(handler);
        i6.addActionListener(handler);
        i7.addActionListener(handler);
        i8.addActionListener(handler);
        i9.addActionListener(handler);
        i10.addActionListener(handler);
        i11.addActionListener(handler);
        i12.addActionListener(handler);

        debug.addItemListener(handler);
        test.addItemListener(handler);

        i5.addActionListener((ae) -> System.exit(0));

        i2.addActionListener((ae) -> onOpen());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public List<File> listFiles(File dir, FileMatcher fileMather) {
        var files = new ArrayList<File>();
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
            files.addAll(currentDirFiles);
        }

        return files;
    }

    private String printAll(File dir, FileMatcher matcher, boolean printFiles) {
        var matchedFiles = listFiles(dir, matcher);
        var uniqueFiles = new HashSet<String>();

        for (var file : matchedFiles) {
            if (printFiles) {
                System.out.println(file.getName());
            }
            uniqueFiles.add(file.getName());
        }

        return String.format("%s: %d -> %d", matcher.getClass().getSimpleName(), matchedFiles.size(), uniqueFiles.size());
    }

    private void onOpen() {
        var isDebug = debug.getState();

        removeAll();

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

        var matchers = new ArrayList<FileMatcher>();

        matchers.add(new AllFileMatcher());
        matchers.add(new JpgFileMatcher());
        matchers.add(new JpegFileMatcher());
        matchers.add(new PngFileMatcher());
        matchers.add(new Mp4FileMatcher());
        matchers.add(new GifFileMatcher());
        matchers.add(new FileMatcher14());
        matchers.add(new FileMatcher42());
        matchers.add(new FileMatcher42All());
        matchers.add(new FileMatcher86());
        matchers.add(new FileMatcher86Brackets());
        matchers.add(new FileMatcher86All());
        matchers.add(new FileMatcherWp82());
        matchers.add(new FileMatcherImg4());
        matchers.add(new InvertedFileMatcherWrapper(new FileMatcher86All()));
        matchers.add(new InlineFileMatcher("(?!skip).*"));

        for (var m : matchers) {
            var result = printAll(rootDir, m, isDebug);
            if (isDebug) {
                System.out.println(result);
            }
            var newLabel = new Label();
            newLabel.setText(result);
            add(newLabel);
        }

        setVisible(true);
    }

    private void transformAll(File dir, FileNormalizer transformator, boolean printFiles) {
        var files = listFiles(dir, (FileMatcher) transformator);
        var total = files.size();
        var counter = 0;

        for (var file : files) {
            var oldName = file.getName().toLowerCase();
            var fileTimestamp = transformator.getTimestamp(file);
            if (fileTimestamp == null) {
                if (printFiles) {
                    System.out.printf("%s -> skipped%n", oldName);
                }
                continue;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            var fileExtension = oldName.split("\\.")[1];
            var pureName = fileTimestamp.format(formatter);
            var newName = pureName + "." + fileExtension;
            var oldFullName = file.getAbsolutePath();

            if (distinctFiles.containsKey(pureName)) {
                var index = distinctFiles.get(pureName);
                newName = pureName + "(" + index + ")" + "." + fileExtension;
                distinctFiles.put(pureName, index + 1);
            } else {
                distinctFiles.put(pureName, 0);
            }

            if (printFiles) {
                System.out.printf("%s -> %s%n", oldName, newName);
            }

            Path source = Paths.get(oldFullName);

            try {
                Files.move(source, source.resolveSibling(newName));
                counter++;
            } catch (IOException ex) {
                System.out.printf("%s -> %s -> exception%n", oldName, newName);
            }
        }

        System.out.printf("%d/%d%n", counter, total);
        System.out.println(distinctFiles.size());
    }

    public static void main(String[] args) {
        var app = new App();

        app.setSize(new Dimension(720, 480));
        app.setTitle("Tart");
        app.setVisible(true);
    }

    class MenuHandler implements ActionListener, ItemListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            var isDebug = debug.getState();

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
