package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcher86 extends FileMatcher {

    public FileMatcher86() {
        this(null);
    }

    public FileMatcher86(FileMatcher matcher) {
        super("\\d{8}_\\d{6}\\..*", matcher);
    }
}
