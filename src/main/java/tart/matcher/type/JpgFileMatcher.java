package tart.matcher.type;

import tart.matcher.FileMatcher;

public class JpgFileMatcher extends FileMatcher {

    public JpgFileMatcher() {
        this(null);
    }

    public JpgFileMatcher(FileMatcher matcher) {
        super(".*\\.jpg", matcher);
    }
}
