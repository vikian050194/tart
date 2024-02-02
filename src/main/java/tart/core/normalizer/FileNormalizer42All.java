package tart.core.normalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.data.FileMatcher42All;

public class FileNormalizer42All extends FileMatcher42All implements FileNormalizer {

    public FileNormalizer42All(){
        this(null);
    }
    
    public FileNormalizer42All(FileMatcher matcher){
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
