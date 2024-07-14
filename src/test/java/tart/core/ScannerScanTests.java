package tart.core;

import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerScanTests {

    @Test
    public void notReady() {
        // Arrange
        var fsm = new TestFileSystemManager();
        fsm.setInspectReturnValue(false);
        var s = new Scanner(fsm);

        // Act
        s.scan("test");

        // Assert
    }

    @Test
    public void ready() {
        // Arrange
        var fsm = new TestFileSystemManager();
        fsm.setInspectReturnValue(true);
        var s = new Scanner(fsm);

        // Act
        s.scan("test");

        // Assert
    }
}
