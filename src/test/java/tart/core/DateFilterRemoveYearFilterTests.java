package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterRemoveYearFilterTests {

    @Test
    public void allMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "ALL";
        var expected = true;

        // Act
        var actual = f.removeYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void allMaskUnexpected() {
        // Arrange
        var f = new DateFilter();
        var initialMask = "2024";
        var testMask = "ALL";
        var expected = false;

        // Act
        f.addYearFilter(initialMask);
        var actual = f.removeYearFilter(testMask);

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
        f.addYearFilter(testMask);
        var actual = f.removeYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskOneFilterIsRemaining() {
        // Arrange
        var f = new DateFilter();
        var keepTestMask = "2012";
        var testMask = "2024";
        var expected = true;

        // Act
        f.addYearFilter(keepTestMask);
        f.addYearFilter(testMask);
        var actual = f.removeYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customNewMask() {
        // Arrange
        var f = new DateFilter();
        var testMask = "2024";
        var expected = false;

        // Act
        var actual = f.removeYearFilter(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
