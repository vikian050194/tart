package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;
import tart.core.logger.Logger;

public class DayFilter extends Filter {

    public DayFilter(AppModel m) {
        super("Day", m);
    }

    @Override
    void addMask(String m) {
        model.addDayFilter(m);
        var msg = String.format("%s day mask is added", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    void removeMask(String m) {
        model.removeDayFilter(m);
        var msg = String.format("%s day mask is removed", m);
        Logger.getLogger().finest(msg);
    }

    @Override
    List<Mask> getValues() {
        return model.getDays();
    }
}
