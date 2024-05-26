package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class GifFileMatcher extends FileMatcher {
    
    public GifFileMatcher() {
        this(null);
    }
    
    public GifFileMatcher(FileMatcher matcher) {
        super(".*\\.gif", matcher);
    }
}
