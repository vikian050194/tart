package tart.core.matcher.type;

import java.io.File;
import tart.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class Mp4FileMatcher extends FileMatcher {

    public Mp4FileMatcher() {
        this(null);
    }

    public Mp4FileMatcher(FileMatcher matcher) {
        super(".*\\.mp4", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
