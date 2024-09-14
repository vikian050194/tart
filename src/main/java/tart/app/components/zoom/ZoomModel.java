package tart.app.components.zoom;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BoundedRangeModel;

public interface ZoomModel extends BoundedRangeModel {

    public void setImage(Image i);

    public Image getImage();

    public Dimension getScaledSize();

}
