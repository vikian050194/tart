package tart.app.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class ButtonFilter extends JPanel {

    private final ActionListener handler;
    private final JPanel buttons;

    public ButtonFilter(String n, ActionListener h) {
        handler = h;

        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);

        setLayout(layout);

        var name = new JLabel(n);
        var cont = new JPanel(layout);
        cont.add(name);
        buttons = new JPanel(layout);
        cont.add(buttons);

        add(cont);

        setButtons(new String[0]);
    }

    public void setButtons(String[] values) {
        buttons.removeAll();

        var all = new JButton("ALL");
        all.setFocusable(false);
        all.addActionListener(handler);
        buttons.add(all);

        for (String value : values) {
            var newButton = new JButton(value);
            newButton.setFocusable(false);
            newButton.addActionListener(handler);
            buttons.add(newButton);
        }

        buttons.updateUI();
    }
}
