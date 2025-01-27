package tart.app;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelGetFileTests {

    @Test
    public void noFile() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
        File expected = null;

        // Act
        s.scan("test");
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void sameFile() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = f.get(0);

        // Act
        s.scan("test");
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }
}
