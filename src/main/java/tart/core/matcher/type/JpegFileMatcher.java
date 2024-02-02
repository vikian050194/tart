package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class JpegFileMatcher extends FileMatcher {

    public JpegFileMatcher() {
        this(null);
    }

    public JpegFileMatcher(FileMatcher matcher) {
        super(".*\\.jpeg", matcher);
    }
}
