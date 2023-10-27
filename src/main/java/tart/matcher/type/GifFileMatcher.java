package tart.matcher.type;

import tart.matcher.FileMatcher;

public class GifFileMatcher extends FileMatcher {
    
    public GifFileMatcher() {
        this(null);
    }
    
    public GifFileMatcher(FileMatcher matcher) {
        super(".*\\.gif", matcher);
    }
}
