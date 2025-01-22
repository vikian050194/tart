package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.domain.file.Filter;

public class DateFilterAddFilterTests {

    @Test
    public void customMask() {
        // Arrange
        var f = new Filter("default");
        var testMask = "12";
        var expected = true;

        // Act
        var actual = f.add(testMask);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void customMaskDuplication() {
        // Arrange
        var f = new Filter("default");
        var testMask = "12";
        var expected = false;

        // Act
        f.add(testMask);
        var actual = f.add(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
