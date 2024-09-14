package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;

public class MonthFilter extends Filter {

    public MonthFilter(AppModel m) {
        super("Month", m);
    }

    @Override
    void addMask(String m) {
        model.addMonthFilter(m);
    }

    @Override
    void removeMask(String m) {
        model.removeMonthFilter(m);
    }

    @Override
    List<Mask> getValues() {
        return model.getMonths();
    }
}
