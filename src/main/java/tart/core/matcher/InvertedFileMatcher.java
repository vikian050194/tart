package tart.core.matcher;

import java.io.File;

public class InvertedFileMatcher extends FileMatcher {
    private final FileMatcher wrappedMatcher;
    
    
    public InvertedFileMatcher(FileMatcher matcher) {
        super(".*");
        this.wrappedMatcher = matcher;
    }
    
    @Override
    public boolean isMatch(File file) {
        return !wrappedMatcher.isMatch(file);
    }
}
