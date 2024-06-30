package tart.app.components;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import tart.core.DateFilterItemValue;

public final class ButtonFilter extends JPanel {

    private boolean enabled;
    private final ActionListener handler;
    private final JPanel buttons;

    public ButtonFilter(String n, ActionListener h) {
        enabled = true;

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

        setButtons(List.of(new DateFilterItemValue[0]));
    }

    public void setButtons(List<DateFilterItemValue> values) {
        buttons.removeAll();

        var all = new JToggleButton("ALL");
        all.setFocusable(false);
        all.addActionListener(handler);
        all.setEnabled(false);
        all.setFont(new Font("Dialog", Font.BOLD, 12));
        buttons.add(all);

        for (DateFilterItemValue value : values) {
            var newButton = new JToggleButton(value.text);

            newButton.setSelected(value.selected);
            newButton.setEnabled(value.enabled);
            newButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            newButton.setFocusable(false);
            newButton.addActionListener(handler);
            buttons.add(newButton);

        }

        if (!enabled) {
            setEnabled(enabled);
        }

        buttons.updateUI();
    }

    @Override
    public void setEnabled(boolean e) {
        enabled = e;

        for (Object o : buttons.getComponents()) {
            var button = (JToggleButton) o;

            if (button.getText().equals("ALL")) {
                continue;
            }

            button.setEnabled(e);
        }
    }
}
