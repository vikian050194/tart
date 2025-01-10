package tart.core.matcher.type;

import java.io.File;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class GifFileMatcher extends FileMatcher {
    
    public GifFileMatcher() {
        this(null);
    }
    
    public GifFileMatcher(FileMatcher matcher) {
        super(".*\\.gif", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
