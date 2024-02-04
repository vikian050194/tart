package tart.core;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.core.matcher.AllFileMatcher;
import tart.core.matcher.FileMatcher;

public class Scanner {

    private File root;

    private final ArrayList<File> files = new ArrayList<>();
    private int index = 0;

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

    public void scan(String dir) {
        var rootDir = new File(dir);

        if (!rootDir.exists()) {
            System.out.printf("%s not exists%n", dir);
            return;
        }

        files.clear();

        root = rootDir;

        var unsortedFiles = listFiles(rootDir, new AllFileMatcher());
        var sortedFiles = sortFiles(unsortedFiles);
        files.addAll(sortedFiles);

    }

    public void gotoPreviousFile() {
        index--;

        if (index < 0) {
            index = files.size() - 1;
        }
    }

    public void gotoNextFile() {
        index++;

        if (index >= files.size()) {
            index = 0;
        }
    }

    public File getFile() {
        return files.get(index);
    }

    public File getRoot() {
        return root;
    }
}
