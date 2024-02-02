package tart.core.normalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.data.FileMatcher86All;

public class FileNormalizer86All extends FileMatcher86All implements FileNormalizer {

    public FileNormalizer86All() {
        this(null);
    }

    public FileNormalizer86All(FileMatcher matcher) {
        super(matcher);
    }

    @Override
    public LocalDateTime getTimestamp(File file) {
        var oldName = file.getName().toLowerCase();
        oldName = oldName.substring(0, 15);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(oldName, formatter);

        return dateTime;
    }
}
