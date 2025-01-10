package tart.core.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class TestFileSystemManager implements FileSystemManager {

    private boolean inspectResultValue = true;
    private File root;
    public final List<FileWrapper> files;

    public TestFileSystemManager() {
        files = new ArrayList<>();
    }

    public TestFileSystemManager(List<FileWrapper> f) {
        files = f;
    }

    @Override
    public boolean inspect(File dir, List<FileMatcher> matcher) {
        root = dir;
        return inspectResultValue;
    }

    @Override
    public List<FileWrapper> getFiles() {
        return new ArrayList(files);
    }

    @Override
    public File getRoot() {
        return root;
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
    public void delete(FileWrapper targetFile) {

    }
}
