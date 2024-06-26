package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterGetMonthMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var f = new DateFilter();
        var expected = new String[]{"\\d{2}"};

        // Act
        var actual = f.getMonthMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var f = new DateFilter();
        var expected = new String[]{"12"};

        // Act
        f.addMonthFilter("12");
        var actual = f.getMonthMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var f = new DateFilter();
        var expected = new String[]{"12", "23", "31"};

        // Act
        f.addMonthFilter("12");
        f.addMonthFilter("23");
        f.addMonthFilter("31");
        var actual = f.getMonthMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var f = new DateFilter();
        var expected = new String[]{"12"};

        // Act
        f.addMonthFilter("12");
        f.addMonthFilter("12");
        var actual = f.getMonthMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void backToDefaultValue() {
        // Arrange
        var f = new DateFilter();
        var expected = new String[]{"\\d{2}"};

        // Act
        f.addMonthFilter("12");
        f.removeMonthFilter("12");
        var actual = f.getMonthMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
