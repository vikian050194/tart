package tart.domain.image;

import java.util.List;
import tart.app.core.wrapper.FileWrapper;
import tart.domain.user.*;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository ir) {
        imageRepository = ir;
    }
    
    public List<FileWrapper> get10(){
        return imageRepository.get10();
    }
    
    public String create(NewUser user) {
        return imageRepository.create(user);
    }

}
