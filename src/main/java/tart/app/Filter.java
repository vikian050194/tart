package tart.app;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tart.domain.image.ImageService;

public abstract class Filter extends JPanel implements ActionListener, ChangeListener {

    enum LogicalType {
        AND,
        OR
    }

    private boolean enabled;
    protected final ImageService model;
    private final JPanel buttons;
    protected final List<Mask> previousValues;
    private LogicalType logicalType = LogicalType.AND;

    public Filter(String n, ImageService m) {
        enabled = true;

        model = m;
        model.addChangeListener(this);

        previousValues = new ArrayList<>();

        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);

        setLayout(layout);

        var name = new JLabel(String.format("%s:", n));
        var cont = new JPanel(layout);
        // TODO add proper border
//        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), n));
        cont.add(name);
        buttons = new JPanel(layout);
        cont.add(buttons);

        add(cont);
    }

    abstract List<Mask> getValues();

    @Override
    public void stateChanged(ChangeEvent ce) {
        var currentValues = getValues();

        if (currentValues.equals(previousValues)) {
            return;
        }

        previousValues.clear();
        previousValues.addAll(currentValues);

        updateButtons();
    }

    public void updateButtons() {
        setButtons(previousValues);
    }

    public void setButtons(List<Mask> masks) {
        buttons.removeAll();

        for (Mask mask : masks) {
            var newButton = new JToggleButton(mask.getText());

            newButton.setSelected(mask.selected);
            newButton.setEnabled(mask.enabled);
            newButton.setActionCommand(mask.getValue());
            newButton.setFont(new Font("Dialog", Font.PLAIN, 12));
            newButton.setFocusable(false);
            newButton.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        var mask = (String) ae.getActionCommand();

        AbstractButton abstractButton = (AbstractButton) ae.getSource();
        ButtonModel buttonModel = abstractButton.getModel();

        if (buttonModel.isSelected()) {
            addMask(mask);
        } else {
            removeMask(mask);
        }

        // TODO should I remove this call?
        updateUI();
    }

    abstract void addMask(String m);

    abstract void removeMask(String m);
}
