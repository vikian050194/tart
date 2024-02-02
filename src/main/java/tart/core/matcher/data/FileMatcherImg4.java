package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class FileMatcherImg4 extends FileMatcher {

    public FileMatcherImg4() {
        this(null);
    }

    public FileMatcherImg4(FileMatcher matcher) {
        super("img_\\d{4}..*", matcher);
    }
}
