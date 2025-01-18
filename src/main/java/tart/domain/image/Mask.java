package tart.domain.image;

import java.util.Objects;

public class Mask {

    private String text;
    private String value;
    public boolean enabled;
    public boolean selected;

    public Mask(String t) {
        this(t, false, false);
    }

    public Mask(String v, String t) {
        this(t, false, false);
        value = v;
    }

    public Mask(String t, boolean e, boolean s) {
        text = t;
        enabled = e;
        selected = s;
    }

    public Mask(String v, String t, boolean e, boolean s) {
        this(t, e, s);
        value = v;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        if (value != null) {
            return value;
        }

        return text;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Mask)) {
            return false;
        }

        Mask c = (Mask) o;

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
