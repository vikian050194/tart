package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.core.fs.TestFileSystemManager;

public class ScannerGetYearMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"20\\d{2}"};

        // Act
        var actual = s.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"2024"};

        // Act
        s.addYearFilter("2024");
        var actual = s.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"2012", "2023", "2031"};

        // Act
        s.addYearFilter("2012");
        s.addYearFilter("2023");
        s.addYearFilter("2031");
        var actual = s.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"2012"};

        // Act
        s.addYearFilter("2012");
        s.addYearFilter("2012");
        var actual = s.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void backToDefaultValue() {
        // Arrange
        var fsm = new TestFileSystemManager();
        var s = new Scanner(fsm);
        var expected = new String[]{"20\\d{2}"};

        // Act
        s.addYearFilter("2024");
        s.removeYearFilter("2024");
        var actual = s.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
