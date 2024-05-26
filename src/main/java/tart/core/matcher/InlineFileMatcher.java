package tart.core.matcher;

public class InlineFileMatcher extends FileMatcher {

    public InlineFileMatcher(String pattern) {
        super(pattern);
    }

    public InlineFileMatcher(String pattern, FileMatcher matcher) {
        super(pattern, matcher);
    }
}
