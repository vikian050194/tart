package tart.domain.file;

import java.util.ArrayList;
import java.util.List;

public class FileDescription implements NodeDescription {

    private final String name;
    private final List<String> dirs;

    public FileDescription(String n, List<String> d) {
        name = n;
        dirs = d;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getExtension() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getDirs() {
        return dirs;
    }

    @Override
    public List<String> getFullName() {
        var result = new ArrayList<>(dirs);
        result.add(name);
        return List.copyOf(result);
    }
}
