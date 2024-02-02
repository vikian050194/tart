package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class Mp4FileMatcher extends FileMatcher {

    public Mp4FileMatcher() {
        this(null);
    }

    public Mp4FileMatcher(FileMatcher matcher) {
        super(".*\\.mp4", matcher);
    }
}
