package tart.domain.image;

import tart.data.image.TestImageRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.domain.image.ImageService;

public class ImageServiceIsReadyTests {

    @Test
    public void notReady() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        var expected = false;

        // Act
        var actual = is.isReady();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void ready() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        var expected = true;

        // Act
        is.scan("test");
        var actual = is.isReady();

        // Assert
        assertEquals(expected, actual);
    }

}
