package tart.core.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.core.matcher.FileMatcher;

public class RealFileSystemManager implements FileSystemManager {

    // TODO how to unit test this class?
    private final ArrayList<File> files = new ArrayList<>();
    private File root;
    private boolean changed = false;
    private FileMatcher lastFileMatcher;

    private List<File> listFiles(File dir, FileMatcher fileMatcher) {
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
                    .filter(file -> !file.isDirectory() && fileMatcher.isMatch(file))
                    .collect(Collectors.toList());
            result.addAll(currentDirFiles);
        }

        return result;
    }

    @Override
    public boolean inspect(File dir, FileMatcher fileMatcher) {
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
        files.addAll(listFiles(dir, fileMatcher));
        files.sort((a, b) -> a.getName().compareTo(b.getName()));

        root = dir;
        lastFileMatcher = fileMatcher;

        return !files.isEmpty();
    }

    @Override
    public List<File> getFiles() {
        if (changed) {
            // TODO refactor this non optimal last file mather storing
            // TODO full inspect is heavy - update only changed File?
            inspect(root, lastFileMatcher);
        }

        return new ArrayList<>(files);
    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
    public File moveTo(File sourceFile, File targetDir) {
        var targetFile = new File(targetDir, sourceFile.getName());

        changed = !sourceFile.equals(targetFile);

        if (changed) {
            sourceFile.renameTo(targetFile);
            return targetFile;
        }

        return sourceFile;
    }
}
