package tart.app;

import java.awt.Image;

public class DefaultZoomModel extends AbstractZoomModel {

    Image image;

    public DefaultZoomModel() {
        this(null);
    }

    public DefaultZoomModel(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setImage(Image i) {
        image = i;
        // TODO is it needed?
        this.fireStateChanged();
    }

}
