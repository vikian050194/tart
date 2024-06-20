package tart.app.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

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

        setButtons(new String[0]);
    }

    public void setButtons(String[] values) {
        buttons.removeAll();

        var all = new JToggleButton("ALL");
        all.setFocusable(false);
        all.addActionListener(handler);
        buttons.add(all);

        for (int i = 0; i < values.length; i++) {
            var value = values[i];
            var newButton = new JToggleButton(value);

//            var chunks = new LinkedList<String>();
//
//            for (int j = 0; j <= i; j++) {
//                chunks.add(values[j]);
//            }
//
//            var actionCommand = String.join("/", chunks);
//
//            newButton.setActionCommand(actionCommand);
            newButton.setFocusable(false);
            newButton.addActionListener(handler);
            buttons.add(newButton);
        }

        setEnabled(enabled);

        buttons.updateUI();
    }

    @Override
    public void setEnabled(boolean e) {
        enabled = e;

        for (Object button : buttons.getComponents()) {
            ((JToggleButton) button).setEnabled(e);
        }
    }
}
