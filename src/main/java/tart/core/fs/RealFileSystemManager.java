package tart.core.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.core.matcher.FileMatcher;

public class RealFileSystemManager implements FileSystemManager {

    private final ArrayList<File> files = new ArrayList<>();
    private File root;

    private List<File> listFiles(File dir, FileMatcher fileMather) {
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

    @Override
    public boolean inspect(File dir, FileMatcher fileMather) {
        if (!dir.exists()) {
            // TODO remove System.out
            System.out.printf("%s is not found%n", dir);
            return false;
        }

        if (dir.isFile()) {
            // TODO remove System.out
            System.out.printf("%s is not directory%n", dir);
            return false;
        }

        files.clear();
        files.addAll(listFiles(dir, fileMather));
        files.sort((a, b) -> a.getName().compareTo(b.getName()));

        root = dir;

        return true;
    }

    @Override
    public List<File> getFiles() {
        return new ArrayList<>(files);
    }

    @Override
    public File getRoot() {
        return root;
    }
}
