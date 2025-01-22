package tart.domain.file;

import java.util.List;

public class DirectoryDescription implements NodeDescription{

    private final List<String> dirs;

    public DirectoryDescription(List<String> d) {
        dirs = d;
    }

    @Override
    public String getName() {
        var index = dirs.size() - 1;
        return dirs.get(index);
    }

    @Override
    public List<String> getDirs() {
        var index = dirs.size() - 1;
        return dirs.subList(0, index);
    }
}
