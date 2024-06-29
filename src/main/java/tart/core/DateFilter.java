package tart.core;

import java.util.ArrayList;
import java.util.List;

public class DateFilter {

    private final List<String> items = new ArrayList<>();

        // TODO fix strange naming - filter or mask?
    public boolean add(String mask) {

        if (items.contains(mask)) {
            return false;
        }

        items.add(mask);

        return true;
    }

    public boolean remove(String mask) {
        return items.remove(mask);
    }

    public List<String> get() {
        return items;
    }

}
