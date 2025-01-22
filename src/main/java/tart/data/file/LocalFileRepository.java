package tart.data.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.app.core.wrapper.FileWrapper;
import tart.core.logger.Logger;
import tart.core.matcher.FileMatcher;
import tart.domain.file.*;

public class LocalFileRepository implements FileRepository {

    private DirectoryDescription mapFileToDirectoryDescription(File f) {
        return new DirectoryDescription(List.of(f.getAbsolutePath().split(File.separator)));
    }

    @Override
    public List<DirectoryDescription> getDirectories() {
        var home = System.getProperty("user.home");
        var root = new File(home);

        var result = Stream.of(root.listFiles())
                .filter(file -> file.isDirectory())
                .map(d -> mapFileToDirectoryDescription(d))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<DirectoryDescription> getDirectories(DirectoryDescription d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FileDescription> getDescriptions(DirectoryDescription d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getFullName(FileDescription f) {
        var fullName = new ArrayList<String>();
        fullName.add(File.separator);
        fullName.addAll(f.getDirs());
        fullName.add(f.getName());
        return String.join(File.separator, fullName);
    }

    @Override
    public FileData getData(FileDescription f) throws IOException, FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(getFullName(f), "r");
        byte[] bytes = new byte[(int) raf.length()];
        raf.readFully(bytes);
        return new FileData(bytes);
    }

    @Override
    public File update(FileDescription f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(FileDescription f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private final ArrayList<FileWrapper> files = new ArrayList<>();
    private boolean changed = false;

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

    public boolean inspect(File dir, List<FileMatcher> matchers) {
        if (!dir.exists()) {
            Logger.getLogger().warning(String.format("%s is not found", dir));
            return false;
        }

        if (dir.isFile()) {
            Logger.getLogger().warning(String.format("%s is not directory", dir));
            return false;
        }

        files.clear();

        for (FileMatcher matcher : matchers) {
            files.addAll(listFiles(dir, matcher).stream().map((f) -> matcher.wrap(f)).toList());
        }

        files.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        return !files.isEmpty();
    }

    public List<FileWrapper> getFiles() {
        if (changed) {
            // TODO refactor this non optimal last file mather storing
            // TODO full inspect is heavy - update only changed File?
        }

        return files;
    }

    public File moveTo(File sourceFile, File targetDir) {
        var targetFile = new File(targetDir, sourceFile.getName());

        changed = !sourceFile.equals(targetFile);

        if (changed) {
            sourceFile.renameTo(targetFile);
            return targetFile;
        }

        return sourceFile;
    }

    public void delete(FileWrapper targetFile) {
        // TODO what is safest way to remove T instance from ArrayList<T> where T is class?
        files.remove(targetFile);
        targetFile.getFile().delete();
    }

    public boolean inspect(DirectoryDescription dir, List<FileMatcher> matchers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
