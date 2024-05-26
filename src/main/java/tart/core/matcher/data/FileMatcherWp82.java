package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcherWp82 extends FileMatcher {

    public FileMatcherWp82() {
        this(null);
    }

    public FileMatcherWp82(FileMatcher matcher) {
        super("wp_\\d{8}_\\d{2}_\\d{2}_\\d{2}_pro\\..*", matcher);
    }
}
