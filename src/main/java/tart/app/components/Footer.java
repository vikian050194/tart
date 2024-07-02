package tart.app.components;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Footer extends JPanel {

    private final JLabel data;
    private int index;
    private int total;

    public Footer() {
        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);

        setLayout(layout);

        // TODO remove magic inline string
        data = new JLabel("0/0");

        add(data);
    }

    private String getText() {
        return String.format("%s out of %s", index, total);
    }

    public void setIndex(int value) {
        index = value;
        data.setText(getText());
    }

    public void setTotal(int value) {
        index = 1;
        total = value;
        data.setText(getText());
    }

}
