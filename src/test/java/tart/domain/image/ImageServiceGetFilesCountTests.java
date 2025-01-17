package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceGetFilesCountTests {

    @Test
    public void zero() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        var expected = 0;

        // Act
        is.scan("test");
        var actual = is.getFilesCount();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void three() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        f.add(new FileWrapper86("20240105_120000.jpeg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = 3;

        // Act
        is.scan("test");
        var actual = is.getFilesCount();

        // Assert
        assertEquals(expected, actual);
    }
}
