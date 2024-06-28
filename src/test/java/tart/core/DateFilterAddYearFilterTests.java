package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterAddYearFilterTests {

    @Test
    public void allMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "ALL";
        var expected = true;

        // Act
        var actual = f.addYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "2024";
        var expected = true;

        // Act
        var actual = f.addYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskDuplication() {
        // Arrange
        var f = new DateFilter();
        var testMask = "2024";
        var expected = false;

        // Act
        f.addYearFilter(testMask);
        var actual = f.addYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
