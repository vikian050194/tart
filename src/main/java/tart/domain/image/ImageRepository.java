package tart.domain.image;

import java.util.List;
import tart.app.core.wrapper.FileWrapper;
import tart.domain.user.*;

public interface ImageRepository {

    boolean setupRoot();
    
    List<FileWrapper> get10();
    
    String create(NewUser user);
}
