package tart.app.components.zoom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tart.app.AppModel;

public final class Zoom extends JComponent implements ChangeListener {

    private static final String uiClassID = "ZoomComponentUI";
    private ZoomModel model;
    private final AppModel appModel;
    private Image previousImage = null;

    public Zoom(AppModel m) {
        appModel = m;
        appModel.addChangeListener(this);

        setBackground(Color.black);
        // TODO remove this focusable-false hack
        setFocusable(false);
        // TODO unnecessary call? it was added in example
        updateUI();
    }

    public void setModel(ZoomModel newModel) {
        if (model != newModel) {
            ZoomModel old = model;
            this.model = newModel;
            firePropertyChange("model", old, newModel);
        }
    }

    public ZoomModel getModel() {
        return model;
    }

    @Override
    public Dimension getPreferredSize() {
        ZoomModel model = getModel();
        // TODO extract magic numbers
        Dimension size = new Dimension(100, 100);
        if (model != null) {
            size = model.getScaledSize();
        }
        return size;
    }

    public void setUI(BasicZoomUI ui) {
        super.setUI(ui);
    }

    @Override
    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            ZoomUI ui = (ZoomUI) UIManager.getUI(this);
            setUI(ui);
        } else {
            setUI(new BasicZoomUI());
        }
    }

    @Override
    public BasicZoomUI getUI() {
        return (BasicZoomUI) ui;
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        var currentImage = appModel.getImage();

        if (previousImage != null && previousImage.equals(currentImage)) {
            return;
        }

        previousImage = currentImage;
        model.setImage(previousImage);
    }
}
