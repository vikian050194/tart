package tart.domain.file;

import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public FileData getFileData(List<String> fileDir, String fileName) throws IOException {
        return imageRepository.getData(new FileDescription(fileName, fileDir));
    }

}
