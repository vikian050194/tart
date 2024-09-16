package tart.core.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import tart.core.matcher.FileMatcher;

public class TestFileSystemManager implements FileSystemManager {

    private boolean inspectResultValue = true;
    public final List<File> files;

    public TestFileSystemManager() {
        files = new ArrayList<>();
    }

    public TestFileSystemManager(List<File> f) {
        files = f;
    }

    @Override
    public boolean inspect(File dir, FileMatcher fileMather) {
        return inspectResultValue;
    }

    @Override
    public List<File> getFiles() {
        return new ArrayList(files);
    }

    @Override
    public File getRoot() {
        return new File("test");
    }

    public void setInspectReturnValue(boolean v) {
        inspectResultValue = v;
    }

    @Override
    public File moveTo(File sourceFile, File targetDir) {
        // TODO refactor production code duplication
        var targetFile = new File(targetDir, sourceFile.getName());
        sourceFile.renameTo(targetFile);
        return targetFile;
    }

    @Override
    public void delete(File targetFile) {

    }
}
