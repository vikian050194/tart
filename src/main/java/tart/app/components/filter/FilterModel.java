package tart.app.components.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {

    protected final List<String> items = new ArrayList<>();
    protected final String defaultMask;

    public FilterModel(String d) {
        defaultMask = d;
    }

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

    public void clear() {
        items.clear();
    }
}
