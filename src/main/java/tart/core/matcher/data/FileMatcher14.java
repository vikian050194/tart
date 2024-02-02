package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcher14 extends FileMatcher {

    public FileMatcher14() {
        this(null);
    }

    public FileMatcher14(FileMatcher matcher) {
        super("\\d{14}.*", matcher);
    }
}
