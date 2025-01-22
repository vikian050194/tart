package tart.domain.file;

import java.util.ArrayList;
import java.util.List;

// TODO is it better to make parent and child classes instead of interface NodeDescription?
public class FileDescription implements NodeDescription {

    private final String name;
    private final List<String> dirs;
    private static final String EXTENSION_DELIMITER = ".";

    public FileDescription(List<String> d, String n) {
        name = n;
        dirs = d;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getExtension() {
        var index = name.indexOf(EXTENSION_DELIMITER);
        return name.substring(index + 1);
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
