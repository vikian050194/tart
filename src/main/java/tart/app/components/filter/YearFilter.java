package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;
import tart.core.logger.Logger;

public class YearFilter extends Filter {

    public YearFilter(AppModel m) {
        super("Year", m);
    }

    @Override
    void addMask(String m) {
        model.addYearFilter(m);
        var msg = String.format("%s year mask is added", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    void removeMask(String m) {
        model.removeYearFilter(m);
        var msg = String.format("%s year mask is removed", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    List<Mask> getValues() {
        return model.getYears();
    }
}
