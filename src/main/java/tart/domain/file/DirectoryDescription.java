package tart.domain.file;

import java.util.List;

public class DirectoryDescription {

    private final List<String> dirs;

    public DirectoryDescription(List<String> d) {
        dirs = d;
    }

    public String getName() {
        var index = dirs.size() - 1;
        return dirs.get(index);
    }

    public List<String> getAncestors() {
        var index = dirs.size() - 1;
        return dirs.subList(0, index);
    }
}
