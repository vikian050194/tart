package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerGetDayMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"\\d{2}"};

        // Act
        var actual = s.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"12"};

        // Act
        s.addDayFilter("12");
        var actual = s.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"12", "23", "31"};

        // Act
        s.addDayFilter("12");
        s.addDayFilter("23");
        s.addDayFilter("31");
        var actual = s.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"12"};

        // Act
        s.addDayFilter("12");
        s.addDayFilter("12");
        var actual = s.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void backToDefaultValue() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"\\d{2}"};

        // Act
        s.addDayFilter("12");
        s.removeDayFilter("12");
        var actual = s.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
