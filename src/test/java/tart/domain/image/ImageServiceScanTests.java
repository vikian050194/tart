package tart.domain.image;

import tart.data.image.TestImageRepository;
import org.junit.jupiter.api.Test;
import tart.domain.image.ImageService;

public class ImageServiceScanTests {

    @Test
    public void notReady() {
        // Arrange
        var ir = new TestImageRepository();
        ir.setInspectReturnValue(false);
        var is = new ImageService(ir);

        // Act
        is.scan("test");

        // Assert
    }

    @Test
    public void ready() {
        // Arrange
        var ir = new TestImageRepository();
        ir.setInspectReturnValue(true);
        var is = new ImageService(ir);

        // Act
        is.scan("test");

        // Assert
    }
}
