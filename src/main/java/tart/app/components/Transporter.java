package tart.app.components;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Transporter extends JPanel {

    private boolean enabled;
    private final int count;
    private final JPanel buttons;
    private final ActionListener al;

    public Transporter(int c, ActionListener al) {
        this.count = c;
        this.al = al;

        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);

        setLayout(layout);

        // TODO extract string
        var name = new JLabel("Move to:");
        var cont = new JPanel(layout);
        cont.add(name);
        buttons = new JPanel(layout);
        cont.add(buttons);

        add(cont);

        reset();
    }

    public void reset() {
        buttons.removeAll();

        for (int i = 1; i <= count; i++) {
            var index = i % count;
            var b = new JButton(String.format("%d: select", index));
            b.setEnabled(enabled);
            b.setFocusable(false);
            // TODO extract font - make factory
            b.setFont(new Font("Dialog", Font.PLAIN, 12));
            b.setToolTipText(String.format("press %d to move image", index));
            b.setActionCommand(String.format("%d", index));
            b.addActionListener(al);
            buttons.add(b);
        }
    }

    @Override
    public void setEnabled(boolean e) {
        enabled = e;

        for (Object o : buttons.getComponents()) {
            var button = (JButton) o;

            button.setEnabled(e);
        }
    }

    public JButton getButton(int i) {
        var targetActionCommand = String.valueOf(i);

        for (Object o : buttons.getComponents()) {
            var button = (JButton) o;

            if (button.getActionCommand().equals(targetActionCommand)) {
                return button;
            }
        }

        return null;
    }
}
