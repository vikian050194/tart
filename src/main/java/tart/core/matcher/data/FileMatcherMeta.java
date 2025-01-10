package tart.core.matcher.data;

import java.io.File;
import tart.app.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class FileMatcherMeta extends FileMatcher {

    public FileMatcherMeta() {
        super(null);
    }

    public FileMatcherMeta(FileMatcher matcher) {
        super(".*\\.(jpg|jpeg|png|mp4|gif)", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
