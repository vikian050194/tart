package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcher42 extends FileMatcher {

    public FileMatcher42() {
        this(null);
    }

    public FileMatcher42(FileMatcher matcher) {
        super("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}..*", matcher);
    }
}
