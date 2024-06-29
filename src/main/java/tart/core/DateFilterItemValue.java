package tart.core;

import java.util.Objects;

public class DateFilterItemValue {

    public String label;
    public String value;
    public boolean enabled;

    public DateFilterItemValue(String l, String v, boolean e) {
        label = l;
        value = v;
        enabled = e;
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

        return label.equals(c.label) && value.equals(c.value) && enabled == c.enabled;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.label);
        hash = 71 * hash + Objects.hashCode(this.value);
        hash = 71 * hash + (this.enabled ? 1 : 0);
        return hash;
    }
}
