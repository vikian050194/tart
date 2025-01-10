package tart.core.matcher.type;

import java.io.File;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class JpgFileMatcher extends FileMatcher {

    public JpgFileMatcher() {
        this(null);
    }

    public JpgFileMatcher(FileMatcher matcher) {
        super(".*\\.jpg", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
