package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceGetFileTests {

    @Test
    public void noFile() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        File expected = null;

        // Act
        is.scan("test");
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void sameFile() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = f.get(0);

        // Act
        is.scan("test");
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }
}
