package tart.core.matcher.wrapper;

import java.io.File;
import tart.core.matcher.FileMatcher;

public class InvertedFileMatcherWrapper extends FileMatcher {
    private final FileMatcher wrappedMatcher;
    
    
    public InvertedFileMatcherWrapper(FileMatcher matcher) {
        super(".*");
        this.wrappedMatcher = matcher;
    }
    
    @Override
    public boolean isMatch(File file) {
        return !wrappedMatcher.isMatch(file);
    }
}
