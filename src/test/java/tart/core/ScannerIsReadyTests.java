package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerIsReadyTests {

    @Test
    public void notReady() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = false;

        // Act
        var actual = s.isReady();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void ready() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = true;

        // Act
        s.scan("test");
        var actual = s.isReady();

        // Assert
        assertEquals(expected, actual);
    }

}
