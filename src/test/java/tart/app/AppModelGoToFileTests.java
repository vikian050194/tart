package tart.app;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class AppModelGoToFileTests {

    @Test
    public void nextFileNoFile() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
        File expected = null;

        // Act
        s.scan("test");
        s.gotoNextFile();
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void nextFile() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240103_120000.jpg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = f.get(1);

        // Act
        s.scan("test");
        s.gotoNextFile();
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFileNoFile() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new AppModel(fsm);
        File expected = null;

        // Act
        s.scan("test");
        s.gotoPreviousFile();
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFile() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240103_120000.jpg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = f.get(1);

        // Act
        s.scan("test");
        s.gotoPreviousFile();
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFileNonInitial() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240103_120000.jpg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = f.get(0);

        // Act
        s.scan("test");
        s.gotoNextFile();
        s.gotoPreviousFile();
        var actual = s.getFile();

        // Assert
        assertEquals(expected, actual);
    }

}
