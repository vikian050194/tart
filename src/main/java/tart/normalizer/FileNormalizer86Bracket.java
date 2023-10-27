package tart.normalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tart.matcher.FileMatcher;
import tart.matcher.data.FileMatcher86Brackets;

public class FileNormalizer86Bracket extends FileMatcher86Brackets implements FileNormalizer {

    public FileNormalizer86Bracket() {
        this(null);
    }

    public FileNormalizer86Bracket(FileMatcher matcher) {
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
