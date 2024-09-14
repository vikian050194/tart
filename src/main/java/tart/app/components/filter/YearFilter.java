package tart.app.components.filter;

import java.util.List;
import tart.app.AppModel;

public class YearFilter extends Filter {

    public YearFilter(AppModel m) {
        super("Year", m);
    }

    @Override
    void addMask(String m) {
        model.addYearFilter(m);
    }

    @Override
    void removeMask(String m) {
        model.removeYearFilter(m);
    }

    @Override
    List<Mask> getValues() {
        return model.getYears();
    }
}
