package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceMoveToTests {

    @Test
    public void updateMiddle() {
        // Arrange
        var testTarget = new File("foo");
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.jpg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = new FileWrapper86("foo/20240101_120000.jpg");

        // Act
        is.scan("test");
        is.moveTo(testTarget);
        var actual = is.getFile();

        // Assert
        assertEquals(expected.getFile(), actual.getFile());
    }

}
