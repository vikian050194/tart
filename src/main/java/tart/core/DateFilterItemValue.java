package tart.core;

import java.util.Objects;

public class DateFilterItemValue {

    public String text;
    public boolean enabled;
    public boolean selected;

    public DateFilterItemValue(String t, boolean e, boolean s) {
        text = t;
        enabled = e;
        selected = s;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof DateFilterItemValue)) {
            return false;
        }

        DateFilterItemValue c = (DateFilterItemValue) o;

        return text.equals(c.text) && enabled == c.enabled && selected == c.selected;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.text);
        hash = 71 * hash + (this.enabled ? 1 : 0);
        hash = 71 * hash + (this.selected ? 1 : 0);
        return hash;
    }
}
