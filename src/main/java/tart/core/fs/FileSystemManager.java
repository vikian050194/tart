package tart.core.fs;

import java.io.File;
import java.util.List;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public interface FileSystemManager {

    public boolean inspect(File dir, List<FileMatcher> matchers);

    public List<FileWrapper> getFiles();

    // TODO refactor code and remove this method
    public File getRoot();

    public File moveTo(File sourceFile, File targetDir);

    public void delete(FileWrapper targetFile);
}
