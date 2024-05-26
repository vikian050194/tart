package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcher42All extends FileMatcher {

    public FileMatcher42All() {
        this(null);
    }

    public FileMatcher42All(FileMatcher matcher) {
        super("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}(\\(\\d*\\))?..*", matcher);
    }
}
