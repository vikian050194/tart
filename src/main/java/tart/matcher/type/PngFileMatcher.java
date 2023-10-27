package tart.matcher.type;

import tart.matcher.FileMatcher;

public class PngFileMatcher extends FileMatcher {

    public PngFileMatcher() {
        this(null);
    }

    public PngFileMatcher(FileMatcher matcher) {
        super(".*\\.png", matcher);
    }
}
