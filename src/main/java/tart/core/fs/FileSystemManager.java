package tart.core.fs;

import java.io.File;
import java.util.List;
import tart.core.matcher.FileMatcher;

public interface FileSystemManager {

    public boolean inspect(File dir, FileMatcher fileMather);

    public List<File> getFiles();

    // TODO refactor code and remove this method
    public File getRoot();

    public File moveTo(File sourceFile, File targetDir);

    public void delete(File targetFile);
}
