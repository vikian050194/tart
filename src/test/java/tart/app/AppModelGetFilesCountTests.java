package tart.app;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelGetFilesCountTests {

    @Test
    public void zero() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
        var expected = 0;

        // Act
        s.scan("test");
        var actual = s.getFilesCount();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void three() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        f.add(new FileWrapper86("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = 3;

        // Act
        s.scan("test");
        var actual = s.getFilesCount();

        // Assert
        assertEquals(expected, actual);
    }
}
