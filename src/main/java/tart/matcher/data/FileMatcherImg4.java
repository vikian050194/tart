package tart.matcher.data;

import tart.matcher.FileMatcher;

public class FileMatcherImg4 extends FileMatcher {

    public FileMatcherImg4() {
        super(null);
    }

    public FileMatcherImg4(FileMatcher matcher) {
        super("img_\\d{4}..*", matcher);
    }
}
