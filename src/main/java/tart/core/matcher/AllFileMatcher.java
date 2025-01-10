package tart.core.matcher;

import java.io.File;
import tart.app.core.wrapper.FileWrapper;

public class AllFileMatcher extends FileMatcher {

    public AllFileMatcher() {
        this(null);
    }

    public AllFileMatcher(FileMatcher matcher) {
        super(".*\\..*", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
