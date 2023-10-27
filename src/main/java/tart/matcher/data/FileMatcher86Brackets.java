package tart.matcher.data;

import tart.matcher.FileMatcher;

public class FileMatcher86Brackets extends FileMatcher {

    public FileMatcher86Brackets() {
        this(null);
    }

    public FileMatcher86Brackets(FileMatcher matcher) {
        super("\\d{8}_\\d{6}\\(\\d*\\)\\..*", matcher);
    }
}
