package tart.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerGetYearsTests {

    @Test
    public void noValue() {
        // Arrange
        var f = new ArrayList<File>();
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = List.of();

        // Act
        var actual = s.getYears();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneValue() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = List.of(new DateFilterItemValue("2024", true, false));

        // Act
        var actual = s.getYears();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void threeUniqueValues() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20220301_120000.jpg"));
        f.add(new File("20230501_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new Scanner(fsm);
        s.scan("test");
        var expected = List.of(
                new DateFilterItemValue("2022", true, false),
                new DateFilterItemValue("2023", true, false),
                new DateFilterItemValue("2024", true, false)
        );

        // Act
        var actual = s.getYears();

        // Assert
        assertEquals(expected, actual);
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
        var expected = List.of(new DateFilterItemValue("2024", true, false));

        // Act
        var actual = s.getYears();

        // Assert
        assertEquals(expected, actual);
    }
}
