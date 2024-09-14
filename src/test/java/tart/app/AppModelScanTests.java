package tart.app;

import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class AppModelScanTests {

    @Test
    public void notReady() {
        // Arrange
        var fsm = new TestFileSystemManager();
        fsm.setInspectReturnValue(false);
        var s = new AppModel(fsm);

        // Act
        s.scan("test");

        // Assert
    }

    @Test
    public void ready() {
        // Arrange
        var fsm = new TestFileSystemManager();
        fsm.setInspectReturnValue(true);
        var s = new AppModel(fsm);

        // Act
        s.scan("test");

        // Assert
    }
}
