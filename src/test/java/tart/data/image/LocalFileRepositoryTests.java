package tart.data.image;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.data.file.LocalFileRepository;

public class LocalFileRepositoryTests {

    @Test
    public void moveTo() {
        // Arrange
        var testFile = new File("20240101_120000.jpg");
        var testDirectory = new File("foo");
        var ir = new LocalFileRepository();
        var expected = new File("foo/20240101_120000.jpg");

        // Act
        var actual = ir.moveTo(testFile, testDirectory);

        // Assert
        assertEquals(expected, actual);
    }

}
