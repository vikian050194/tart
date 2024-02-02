package tart.core.normalizer;

import java.io.File;
import java.time.LocalDateTime;

public interface FileNormalizer {

    public LocalDateTime getTimestamp(File file);
}
