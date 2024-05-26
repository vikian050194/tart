package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class JpgFileMatcher extends FileMatcher {

    public JpgFileMatcher() {
        this(null);
    }

    public JpgFileMatcher(FileMatcher matcher) {
        super(".*\\.jpg", matcher);
    }
}
