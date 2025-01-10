package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;
import tart.core.logger.Logger;

public class DirFilter extends Filter {

    public DirFilter(AppModel m) {
        super("Dir", m);
    }

    @Override
    void addMask(String m) {
        model.addDirFilter(m);
        var msg = String.format("%s dir mask is added", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    void removeMask(String m) {
        model.removeDirFilter(m);
        var msg = String.format("%s dir mask is removed", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    List<Mask> getValues() {
        return model.getDirs();
    }
}
