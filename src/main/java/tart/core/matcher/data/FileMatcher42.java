package tart.core.matcher.data;

import java.io.File;
import tart.core.wrapper.FileWrapper;
import tart.core.wrapper.FileWrapper42;
import tart.core.matcher.FileMatcher;

public class FileMatcher42 extends FileMatcher {

    public FileMatcher42() {
        this(null);
    }

    public FileMatcher42(FileMatcher matcher) {
        super("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}.*", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        return new FileWrapper42(file);
    }
}
