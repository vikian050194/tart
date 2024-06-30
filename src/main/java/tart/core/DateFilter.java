package tart.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DateFilter {

    private final List<String> items = new ArrayList<>();
    private final String defaultMask;

    public DateFilter(String d) {
        defaultMask = d;
    }

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
        if (isEmpty()) {
            return List.of(defaultMask);
        }

        return items;
    }

    public boolean contains(String v) {
        return items.contains(v);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
