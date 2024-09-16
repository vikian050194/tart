package tart.app.components.footer;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tart.app.AppModel;

public final class Footer extends JComponent implements ChangeListener {

    private final JLabel data;

// TODO make and use minimal interface instead of big solid single class AppModel
    private final AppModel model;

    private int previousIndex = 0;
    private int previousSize = 0;

    public Footer(AppModel i) {
        model = i;
        model.addChangeListener(this);

        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        setLayout(layout);

//        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        data = new JLabel();
        // TODO move string to resources
        data.setToolTipText("index/size");

        add(data);
//        add(Box.createHorizontalStrut(5));
//        add(new JSeparator(SwingConstants.VERTICAL));
//        add(Box.createHorizontalStrut(5));

        updateText();
//        updateUI();
    }

    private String getText() {
        var index = previousSize > 0 ? previousIndex + 1 : 0;
        var size = previousSize;
        return String.format("%s/%s", index, size);
    }

    public void updateText() {
        data.setText(getText());
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        var currentIndex = model.getIndex();
        var currentSize = model.getSize();

        if (currentIndex == previousIndex && currentSize == previousSize) {
            return;
        }

        previousIndex = currentIndex;
        previousSize = currentSize;

        updateText();
    }
}
