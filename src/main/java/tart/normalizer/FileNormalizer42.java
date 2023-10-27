package tart.normalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tart.matcher.FileMatcher;
import tart.matcher.data.FileMatcher42;

public class FileNormalizer42 extends FileMatcher42 implements FileNormalizer {

    public FileNormalizer42(){
        this(null);
    }
    
    public FileNormalizer42(FileMatcher matcher){
        super(matcher);
    }
    
    @Override
    public LocalDateTime getTimestamp(File file) {
        var oldName = file.getName().toLowerCase();
        oldName = oldName.substring(0, 19);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime dateTime = LocalDateTime.parse(oldName, formatter);

        return dateTime;
    }
}
