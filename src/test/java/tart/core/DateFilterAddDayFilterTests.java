package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterAddDayFilterTests {

    @Test
    public void allMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "ALL";
        var expected = true;

        // Act
        var actual = f.addDayFilter(testMask);

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
        var actual = f.addDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskDuplication() {
        // Arrange
        var f = new DateFilter();
        var testMask = "12";
        var expected = false;

        // Act
        f.addDayFilter(testMask);
        var actual = f.addDayFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
