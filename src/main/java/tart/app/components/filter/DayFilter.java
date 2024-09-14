package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;

public class DayFilter extends Filter {

    public DayFilter(AppModel m) {
        super("Day", m);
    }

    @Override
    void addMask(String m) {
        model.addDayFilter(m);
    }

    @Override
    void removeMask(String m) {
        model.removeDayFilter(m);
    }

    @Override
    List<Mask> getValues() {
        return model.getDays();
    }
}
