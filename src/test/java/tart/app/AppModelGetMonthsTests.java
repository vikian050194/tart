package tart.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.components.filter.Mask;
import tart.core.fs.TestFileSystemManager;

public class AppModelGetMonthsTests {

    @Test
    public void noValue() {
        // Arrange
        var f = new ArrayList<File>();
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of();

        // Act
        s.scan("test");
        var actual = s.getMonths();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneValue() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("01", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getMonths();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void threeUniqueValues() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240101_120000.png"));
        f.add(new File("20240301_120000.jpg"));
        f.add(new File("20240501_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("01", true, false),
                new Mask("03", true, false),
                new Mask("05", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getMonths();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneUniqueValue() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20240201_120000.png"));
        f.add(new File("20240201_130000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("02", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getMonths();

        // Assert
        assertIterableEquals(expected, actual);
    }
}
