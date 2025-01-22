package tart.core.matcher;

import java.io.File;
import tart.core.wrapper.FileWrapper;

public class InvertedFileMatcher extends FileMatcher {

    private final FileMatcher wrappedMatcher;

    public InvertedFileMatcher(FileMatcher matcher) {
        super(".*");
        this.wrappedMatcher = matcher;
    }

    @Override
    public boolean isNameMatch(File file) {
        return !wrappedMatcher.isNameMatch(file);
    }

    @Override
    public boolean isAbsoluteMatch(File file) {
        return !wrappedMatcher.isAbsoluteMatch(file);
    }

    @Override
    public FileWrapper wrap(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
