package tart.domain.file;

import java.io.*;
import java.util.List;

public interface FileRepository {

    public List<DirectoryDescription> getDirectories();

    public List<DirectoryDescription> getDirectories(DirectoryDescription d);

    public List<FileDescription> getDescriptions(DirectoryDescription d);

    public FileData getData(FileDescription f) throws IOException, FileNotFoundException;

    public File update(FileDescription f);

    public void delete(FileDescription f);

}
