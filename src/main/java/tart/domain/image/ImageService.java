package tart.domain.image;

import tart.app.core.wrapper.FileWrapper;
import tart.domain.user.*;

public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository ir) {
        imageRepository = ir;
    }
    
    public FileWrapper getImage(){
        return imageRepository.getImage();
    }
    
    public String create(NewUser user) {
        return imageRepository.create(user);
    }

}
