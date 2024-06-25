package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FilterGetYearMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"20\\d{2}"};

        // Act
        var actual = f.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"2024"};

        // Act
        f.addYearFilter("2024");
        var actual = f.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"2012", "2023", "2031"};

        // Act
        f.addYearFilter("2012");
        f.addYearFilter("2023");
        f.addYearFilter("2031");
        var actual = f.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"2012"};

        // Act
        f.addYearFilter("2012");
        f.addYearFilter("2012");
        var actual = f.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void backToDefaultValue() {
        // Arrange
        var f = new Filter();
        var expected = new String[]{"20\\d{2}"};

        // Act
        f.addYearFilter("2024");
        f.removeYearFilter("2024");
        var actual = f.getYearMasks();

        // Assert
        assertArrayEquals(expected, actual);
    }
}
