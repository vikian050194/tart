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

        data = new JLabel();
        data.setToolTipText("current/total");

        updateText();

        add(data);
    }

    private String getText() {
        return String.format("%s/%s", index, total);
    }

    private void updateText() {
        data.setText(getText());
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
