package tart.core.matcher.data;

import java.io.File;
import tart.core.wrapper.FileWrapper;
import tart.core.matcher.FileMatcher;

public class FileMatcherImg4 extends FileMatcher {

    public FileMatcherImg4() {
        this(null);
    }

    public FileMatcherImg4(FileMatcher matcher) {
        super("img_\\d{4}..*", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
