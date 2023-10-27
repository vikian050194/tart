package tart.matcher.type;

import tart.matcher.FileMatcher;

public class JpegFileMatcher extends FileMatcher {

    public JpegFileMatcher() {
        this(null);
    }

    public JpegFileMatcher(FileMatcher matcher) {
        super(".*\\.jpeg", matcher);
    }
}
