package tart.app.components.filter;

import java.util.List;
import java.util.stream.Stream;
import tart.app.AppModel;

public class DirFilter extends Filter {

    public DirFilter(AppModel m) {
        super("Dir", m);
    }

    @Override
    void addMask(String m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void removeMask(String m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    List<Mask> getValues() {
        var file = model.getFile();

        var pathChunks = file.getAbsolutePath()
                .substring(model.getRoot().getAbsolutePath().length())
                .split("/");
        var partChunksStream = Stream.of(pathChunks)
                .filter(c -> !c.isEmpty() && !c.equals(file.getName()))
                .map((p) -> new Mask(p, false, false));

        return partChunksStream.toList();
    }
}
