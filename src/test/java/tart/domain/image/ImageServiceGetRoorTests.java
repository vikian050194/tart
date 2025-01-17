package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.domain.image.ImageService;

public class ImageServiceGetRoorTests {

    @Test
    public void sameFile() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        var expected = new File("test");

        // Act
        is.scan("test");
        var actual = is.getRoot();

        // Assert
        assertEquals(expected, actual);
    }

}
