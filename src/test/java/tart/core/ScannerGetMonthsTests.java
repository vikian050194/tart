package tart.core;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerGetMonthsTests {

    @Test
    public void noValue() {
        // Arrange
        var f = new ArrayList<File>();
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = new String[]{};

        // Act
        var actual = s.getMonths();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneValue() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = new String[]{"01"};

        // Act
        var actual = s.getMonths();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeUniqueValues() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240301_120000.jpg"));
        f.add(new File("20240501_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = new String[]{"01", "03", "05"};

        // Act
        var actual = s.getMonths();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueValue() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240201_120000.png"));
        f.add(new File("20240201_130000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = new String[]{"02"};

        // Act
        var actual = s.getMonths();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
