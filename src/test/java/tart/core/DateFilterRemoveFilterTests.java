package tart.core;

import tart.domain.image.Filter;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterRemoveFilterTests {

    @Test
    public void customMask() {
        // Arrange
        var f = new Filter("default");
        var testMask = "12";
        var expected = true;

        // Act
        f.add(testMask);
        var actual = f.remove(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskOneFilterIsRemaining() {
        // Arrange
        var f = new Filter("default");
        var keepTestMask = "01";
        var testMask = "12";
        var expected = true;

        // Act
        f.add(keepTestMask);
        f.add(testMask);
        var actual = f.remove(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customNewMask() {
        // Arrange
        var f = new Filter("default");
        var testMask = "12";
        var expected = false;

        // Act
        var actual = f.remove(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
