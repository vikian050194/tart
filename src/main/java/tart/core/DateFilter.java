package tart.core;

import java.util.ArrayList;
import java.util.Collection;
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

    public boolean addAll(Collection<String> masks) {
        for (String mask : masks) {
            var result = add(mask);

            if (result == false) {
                return false;
            }
        }

        return true;
    }

    public boolean remove(String mask) {
        return items.remove(mask);
    }

    public List<String> get() {
        return items;
    }

    public boolean contains(String v) {
        return items.contains(v);
    }
}
