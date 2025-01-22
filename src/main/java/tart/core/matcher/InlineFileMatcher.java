package tart.core.matcher;

import java.io.File;
import tart.core.wrapper.FileWrapper;

public class InlineFileMatcher extends FileMatcher {

    public InlineFileMatcher(String pattern) {
        super(pattern);
    }

    public InlineFileMatcher(String pattern, FileMatcher matcher) {
        super(pattern, matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
