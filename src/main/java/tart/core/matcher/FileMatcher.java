package tart.core.matcher;

import java.io.File;
import java.util.regex.Pattern;

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

    public boolean isMatch(File file) {
        var isCurrentMatch = pattern.matcher(file.getName()).matches();
        var isExternalMatch = matcher != null ? matcher.isMatch(file) : true;
        return isCurrentMatch && isExternalMatch;
    }
}
