package tart.domain.file;

import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public List<DirectoryDescription> getDirectories() {
        return imageRepository.getDirectories();
    }

    public List<DirectoryDescription> getDirectories(List<String> d) {
        return imageRepository.getDirectories(new DirectoryDescription(d));
    }

    public List<FileDescription> getDescriptions(List<String> d) {
        return imageRepository.getDescriptions(new DirectoryDescription(d));
    }

    public FileData getFileData(List<String> d, String n) throws IOException {
        return imageRepository.getData(new FileDescription(d, n));
    }

}
