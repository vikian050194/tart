package tart.core.matcher;

import java.io.File;
import java.util.regex.Pattern;
import tart.app.core.wrapper.FileWrapper;

public abstract class FileMatcher {

    private final Pattern pattern;
    protected final FileMatcher matcher;

    public FileMatcher(String pattern) {
        this(pattern, null);
    }

    public FileMatcher(String pattern, FileMatcher matcher) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        this.matcher = matcher;
    }

    protected boolean isMatch(String string) {
        var isCurrentMatch = pattern.matcher(string).matches();
        var isExternalMatch = matcher != null ? matcher.isMatch(string) : true;
        return isCurrentMatch && isExternalMatch;
    }

    public boolean isNameMatch(File file) {
        return isMatch(file.getName());
    }

    public boolean isAbsoluteMatch(File file) {
        return isMatch(file.getAbsolutePath());
    }

    public abstract FileWrapper wrap(File file);
}
