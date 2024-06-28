package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterRemoveDayFilterTests {

    @Test
    public void allMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "ALL";
        var expected = true;

        // Act
        var actual = f.removeDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void allMaskUnexpected() {
        // Arrange
        var f = new DateFilter();
        var initialMask = "12";
        var testMask = "ALL";
        var expected = false;

        // Act
        f.addDayFilter(initialMask);
        var actual = f.removeDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "12";
        var expected = true;

        // Act
        f.addDayFilter(testMask);
        var actual = f.removeDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskOneFilterIsRemaining() {
        // Arrange
        var f = new DateFilter();
        var keepTestMask = "01";
        var testMask = "12";
        var expected = true;

        // Act
        f.addDayFilter(keepTestMask);
        f.addDayFilter(testMask);
        var actual = f.removeDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customNewMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "12";
        var expected = false;

        // Act
        var actual = f.removeDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
