package tart.core.matcher.data;

import java.io.File;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.matcher.FileMatcher;

public class FileMatcher86 extends FileMatcher {

    public FileMatcher86() {
        this(null);
    }

    public FileMatcher86(FileMatcher matcher) {
        super("\\d{8}_\\d{6}.*", matcher);
    }

    @Override
    public FileWrapper wrap(File file) {
        return new FileWrapper86(file);
    }
}
