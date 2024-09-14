package tart.app;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class AppModelGetFileIndexTests {

    @Test
    public void zero() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
        var expected = 0;

        // Act
        s.scan("test");
        var actual = s.getFileIndex();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void three() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240103_120000.jpg"));
        f.add(new File("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = 2;

        // Act
        s.scan("test");
        s.gotoNextFile();
        s.gotoNextFile();
        var actual = s.getFileIndex();

        // Assert
        assertEquals(expected, actual);
    }
}
