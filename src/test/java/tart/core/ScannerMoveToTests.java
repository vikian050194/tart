package tart.core;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerMoveToTests {

    @Test
    public void updateMiddle() {
        // Arrange
        var testTarget = new File("foo");
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.jpg"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        var expected = new File("foo/20240101_120000.jpg");

        // Act
        s.scan("test");
        s.moveTo(testTarget);
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

}
