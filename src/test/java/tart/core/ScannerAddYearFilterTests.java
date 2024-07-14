package tart.core;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerAddYearFilterTests {

    @Test
    public void updated() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240103_120000.jpg"));
        f.add(new File("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = true;

        // Act
        s.addYearFilter("2024");
        var actual = s.isYearsUpdated();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void douplicated() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20220101_120000.png"));
        f.add(new File("20230101_120000.jpg"));
        f.add(new File("20240101_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = false;

        // Act
        s.addYearFilter("2024");
        s.getYears();
        s.addYearFilter("2024");
        var actual = s.isYearsUpdated();

        // Assert
        assertEquals(expected, actual);
    }
}
