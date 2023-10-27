package tart.matcher.wrapper;

import java.io.File;
import tart.matcher.FileMatcher;

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
