package tart.data.image;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;
import tart.domain.image.*;

public class FileSystemImageRepository implements ImageRepository {

    // TODO how to unit test this class?
    private final ArrayList<FileWrapper> files = new ArrayList<>();
    private File root;
    private boolean changed = false;
    private List<FileMatcher> matchers;

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
                    .filter(file -> !file.isDirectory() && fileMatcher.isNameMatch(file))
                    .collect(Collectors.toList());
            result.addAll(currentDirFiles);
        }

        return result;
    }

    @Override
    public boolean inspect(File dir, List<FileMatcher> matchers) {
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

        for (FileMatcher matcher : matchers) {
            files.addAll(listFiles(dir, matcher).stream().map((f) -> matcher.wrap(f)).toList());
        }

        files.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        root = dir;
        this.matchers = matchers;

        return !files.isEmpty();
    }

    @Override
    public List<FileWrapper> getFiles() {
        if (changed) {
            // TODO refactor this non optimal last file mather storing
            // TODO full inspect is heavy - update only changed File?
            inspect(root, matchers);
        }

        return files;
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

    @Override
    public void delete(FileWrapper targetFile) {
        // TODO what is safest way to remove T instance from ArrayList<T> where T is class?
        files.remove(targetFile);
        targetFile.getFile().delete();
    }
}
