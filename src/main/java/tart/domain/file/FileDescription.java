package tart.domain.file;

import java.util.List;

public class FileDescription {

    private final String name;
    private final List<String> dirs;

    public FileDescription(String n, List<String> d) {
        name = n;
        dirs = d;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getDirs() {
        return dirs;
    }
}