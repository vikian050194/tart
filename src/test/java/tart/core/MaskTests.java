package tart.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.domain.file.Mask;

public class MaskTests {

    @Test
    public void equalsTheSame() {
        // Arrange
        var m = new Mask("test");
        var expected = true;

        // Act
        var actual = m.equals(m);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsIsNotInstance() {
        // Arrange
        var m = new Mask("test");
        var o = new Object();
        var expected = false;

        // Act
        var actual = m.equals(o);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsDiffText() {
        // Arrange
        var m = new Mask("test");
        var d = new Mask("diff");
        var expected = false;

        // Act
        var actual = m.equals(d);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsDiffEnabled() {
        // Arrange
        var m = new Mask("test", true, true);
        var d = new Mask("test", false, true);
        var expected = false;

        // Act
        var actual = m.equals(d);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsDiffSelected() {
        // Arrange
        var m = new Mask("test", true, true);
        var d = new Mask("test", true, false);
        var expected = false;

        // Act
        var actual = m.equals(d);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsCorrect() {
        // Arrange
        var m = new Mask("test", true, false);
        var d = new Mask("test", true, false);
        var expected = true;

        // Act
        var actual = m.equals(d);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void hashCodeEnabled() {
        // Arrange
        var m = new Mask("test", true, false);
        var expected = 749511038;

        // Act
        var actual = m.hashCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void hashCodeSelected() {
        // Arrange
        var m = new Mask("test", false, true);
        var expected = 749510968;

        // Act
        var actual = m.hashCode();

        // Assert
        assertEquals(expected, actual);
    }
}
