package tart.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.components.filter.Mask;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelGetDaysTests {

    @Test
    public void noValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of();

        // Act
        s.scan("test");
        var actual = s.getDays();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("01", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getDays();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void threeUniqueValues() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        f.add(new FileWrapper86("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("01", true, false),
                new Mask("03", true, false),
                new Mask("05", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getDays();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneUniqueValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240101_130000.png"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = List.of(
                new Mask("01", true, false)
        );

        // Act
        s.scan("test");
        var actual = s.getDays();

        // Assert
        assertIterableEquals(expected, actual);
    }
}
