package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FilterGetDayMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"\\d{2}"};

        // Act
        var actual = f.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"12"};

        // Act
        f.addDayFilter("12");
        var actual = f.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"12", "23", "31"};

        // Act
        f.addDayFilter("12");
        f.addDayFilter("23");
        f.addDayFilter("31");
        var actual = f.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"12"};

        // Act
        f.addDayFilter("12");
        f.addDayFilter("12");
        var actual = f.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void backToDefaultValue() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"\\d{2}"};

        // Act
        f.addDayFilter("12");
        f.removeDayFilter("12");
        var actual = f.getDayMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
