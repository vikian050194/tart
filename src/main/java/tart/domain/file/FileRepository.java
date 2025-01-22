package tart.domain.file;

import java.io.*;
import java.util.List;

public interface FileRepository {

    public List<DirectoryDescription> getDirectories();

    public List<DirectoryDescription> getDirectories(DirectoryDescription d);

    public List<FileDescription> getDescriptions(DirectoryDescription d);
    
    public List<FileData> getDatas(DirectoryDescription d);

    public File update(FileDescription f);

    public void delete(FileDescription f);

    public byte[] getData(FileDescription f) throws IOException, FileNotFoundException ;

}
