package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class PngFileMatcher extends FileMatcher {

    public PngFileMatcher() {
        this(null);
    }

    public PngFileMatcher(FileMatcher matcher) {
        super(".*\\.png", matcher);
    }
}
