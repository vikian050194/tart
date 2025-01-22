package tart.data.image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import tart.domain.file.*;

public class TestFileRepository implements FileRepository {

    @Override
    public List<DirectoryDescription> getDirectories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DirectoryDescription> getDirectories(DirectoryDescription d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FileDescription> getDescriptions(DirectoryDescription d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FileData getData(FileDescription f) throws IOException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(FileDescription f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(FileDescription f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
