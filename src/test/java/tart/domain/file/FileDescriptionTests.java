package tart.domain.file;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FileDescriptionTests {

    @Test
    public void getName() {
        // Arrange
        var expectedName = "baz.json";
        var name = "baz.json";
        var dirs = List.of("foo", "bar");
        NodeDescription d = new FileDescription(name, dirs);

        // Act
        var actualName = d.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getDirs() {
        // Arrange
        var expectedDirs = List.of("foo", "bar");
        var name = "baz.json";
        var dirs = List.of("foo", "bar");
        NodeDescription d = new FileDescription(name, dirs);

        // Act
        var actualDirs = d.getDirs();

        // Assert
        assertEquals(expectedDirs, actualDirs);
    }

    @Test
    public void getFullName() {
        // Arrange
        var expectedFullName = List.of("foo", "bar", "baz.json");
        var name = "baz.json";
        var dirs = List.of("foo", "bar");
        NodeDescription d = new FileDescription(name, dirs);

        // Act
        var actualFullName = d.getFullName();

        // Assert
        assertEquals(expectedFullName, actualFullName);
    }
}
