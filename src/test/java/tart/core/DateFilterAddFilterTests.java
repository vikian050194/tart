package tart.core;

import tart.app.components.filter.FilterModel;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterAddFilterTests {

    @Test
    public void customMask() {
        // Arrange
        var f = new FilterModel("default");
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
        var f = new FilterModel("default");
        var testMask = "12";
        var expected = false;

        // Act
        f.add(testMask);
        var actual = f.add(testMask);

        // Assert
        assertEquals(expected, actual);
    }
}
