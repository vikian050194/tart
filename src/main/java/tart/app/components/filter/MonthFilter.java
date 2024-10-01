package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;
import tart.core.logger.Logger;

public class MonthFilter extends Filter {

    public MonthFilter(AppModel m) {
        super("Month", m);
    }

    @Override
    void addMask(String m) {
        model.addMonthFilter(m);
        var msg = String.format("%s month mask is added", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    void removeMask(String m) {
        model.removeMonthFilter(m);
        var msg = String.format("%s month mask is removed", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    List<Mask> getValues() {
        return model.getMonths();
    }
}
