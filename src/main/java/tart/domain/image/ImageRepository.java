package tart.domain.image;

import tart.app.core.wrapper.FileWrapper;
import tart.domain.user.*;

public interface ImageRepository {

    boolean setupRoot();
    
    FileWrapper getImage();
    
    String create(NewUser user);
}
