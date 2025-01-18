package tart.core;

import tart.domain.image.Filter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFilterGetMasksTests {

    @Test
    public void noMasksDefaultValue() {
        // Arrange
        var f = new Filter("default");
        var expected = List.of("default");

        // Act
        var actual = f.get();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void oneMask() {
        // Arrange
        var f = new Filter("default");
        var expected = List.of("12");

        // Act
        f.add("12");
        var actual = f.get();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void threeMasks() {
        // Arrange
        var f = new Filter("default");
        var expected = List.of("12", "23", "31");

        // Act
        f.add("12");
        f.add("23");
        f.add("31");
        var actual = f.get();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void oneUniqueMask() {
        // Arrange
        var f = new Filter("default");
        var expected = List.of("12");

        // Act
        f.add("12");
        f.add("12");
        var actual = f.get();

        // Assert
        assertEquals(expected, actual);
    }

}
