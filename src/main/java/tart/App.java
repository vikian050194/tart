package tart;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;
import tart.matcher.*;
import tart.matcher.data.*;
import tart.matcher.type.*;
import tart.matcher.wrapper.*;
import tart.normalizer.*;

public class App extends Frame {

    private static final HashMap<String, Integer> distinctFiles = new HashMap<>();

    String msg = "";

    public App() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static java.util.List<File> listFiles(File dir, FileMatcher fileMather) {
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

    private static void printAll(File dir, FileMatcher mather, boolean printFiles) {
        var files = listFiles(dir, mather);

        var fooFiles = new HashSet<String>();

        for (var file : files) {
            if (printFiles) {
                System.out.println(file.getName());
            }
            fooFiles.add(file.getName());
        }

        System.out.printf("%s %d -> %d%n", mather.getClass(), files.size(), fooFiles.size());
    }

    private static void transformAll(File dir, FileNormalizer transformator, boolean printFiles) {
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
        var appwin = new App();

        appwin.setSize(new Dimension(320, 320));
        appwin.setTitle("Tart");
        appwin.setVisible(true);

        if (args.length == 0) {
            System.out.println("Path is not provided");
            return;
        }

        var dir = args[0];

        var rootDir = new File(dir);
        if (!rootDir.exists()) {
            System.out.printf("%s not exists%n", dir);
            return;
        }

        var mathers = new ArrayList<FileMatcher>();

        mathers.add(new AllFileMatcher());
        mathers.add(new JpgFileMatcher());
        mathers.add(new JpegFileMatcher());
        mathers.add(new PngFileMatcher());
        mathers.add(new Mp4FileMatcher());
        mathers.add(new GifFileMatcher());
        mathers.add(new FileMatcher14());
        mathers.add(new FileMatcher42());
        mathers.add(new FileMatcher42All());
        mathers.add(new FileMatcher86());
        mathers.add(new FileMatcher86Brackets());
        mathers.add(new FileMatcher86All());
        mathers.add(new FileMatcherWp82());

        for (var m : mathers) {
            printAll(rootDir, m, false);
        }

        System.out.println();

        printAll(rootDir, new InvertedFileMatcherWrapper(new FileMatcher86All()), true);

        System.out.println();

        var transformations = new ArrayList<FileNormalizer>();
//        transformations.add(new FileNormalizer14());
//        transformations.add(new FileNormalizer42());
//        transformations.add(new FileNormalizer86All());
//        transformations.add(new FileNormalizer86());
//        transformations.add(new FileNormalizer86Bracket());
//        transformations.add(new FileNormalizer42All());
//        transformations.add(new FileNormalizerWp82());
//        transformations.add(new FileNormalizerWp82());
//        transformations.add(new FileNormalizerImg4Jpg());
//        transformations.add(new FileNormalizerMeta(new InlineFileMatcher("(?!skip).*", new InvertedFileMatcherWrapper(new FileMatcher42All()))));

        for (var t : transformations) {
            transformAll(rootDir, t, false);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawString(msg, 20, 80);
    }
}
