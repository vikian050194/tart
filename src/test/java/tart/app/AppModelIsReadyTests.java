package tart.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class AppModelIsReadyTests {

    @Test
    public void notReady() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
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
        var s = new AppModel(fsm);
        var expected = true;

        // Act
        s.scan("test");
        var actual = s.isReady();

        // Assert
        assertEquals(expected, actual);
    }

}
