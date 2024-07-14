package tart.core;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerGetRoorTests {

    @Test
    public void sameFile() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new File("test");

        // Act
        s.scan("test");
        var actual = s.getRoot();

        // Assert
        assertEquals(expected, actual);
    }

}
