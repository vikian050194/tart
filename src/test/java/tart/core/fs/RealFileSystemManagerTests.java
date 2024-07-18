package tart.core.fs;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RealFileSystemManagerTests {

    @Test
    public void success() {
        // Arrange
        var testFile = new File("20240101_120000.jpg");
        var testDirectory = new File("foo");
        var fsm = new RealFileSystemManager();
        var expected = new File("foo/20240101_120000.jpg");

        // Act
        var actual = fsm.moveTo(testFile, testDirectory);

        // Assert
        assertEquals(expected, actual);
    }

}
