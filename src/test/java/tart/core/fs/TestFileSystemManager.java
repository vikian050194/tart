package tart.core.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import tart.core.matcher.FileMatcher;

public class TestFileSystemManager implements FileSystemManager {

    public final List<File> files;

    public TestFileSystemManager() {
        files = new ArrayList<>();
    }

    public TestFileSystemManager(List<File> f) {
        files = f;
    }

    @Override
    public boolean inspect(File dir, FileMatcher fileMather) {
        return true;
    }

    @Override
    public List<File> getFiles() {
        return new ArrayList(files);
    }

    @Override
    public File getRoot() {
        return new File("test");
    }
}
