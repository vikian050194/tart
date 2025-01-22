package tart.core.wrapper;

import java.io.File;
import java.time.LocalDateTime;

public abstract class FileWrapper {

    protected final File file;

    public FileWrapper(String p) {
        file = new File(p);
    }

    public FileWrapper(File f) {
        file = f;
    }

    public File getFile() {
        return file;
    }

    public abstract FileWrapper cloneWith(File value);

    public int getYear() {
        return getTimestamp().getYear();
    }

    public int getMonth() {
        return getTimestamp().getMonth().getValue();
    }

    public int getDay() {
        return getTimestamp().getDayOfMonth();
    }

    public abstract String getDate();

    public abstract String getTime();

    public abstract LocalDateTime getTimestamp();
}
