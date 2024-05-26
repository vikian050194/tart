package tart.core.normalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.data.FileMatcher14;

public class FileNormalizer14 extends FileMatcher14 implements FileNormalizer {

    public FileNormalizer14() {
        this(null);
    }

    public FileNormalizer14(FileMatcher matcher) {
        super(matcher);
    }

    @Override
    public LocalDateTime getTimestamp(File file) {
        var oldName = file.getName().toLowerCase();
        oldName = oldName.substring(0, 14);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(oldName, formatter);

        return dateTime;
    }
}
