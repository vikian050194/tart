package tart.domain.file;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DirectoryDescriptionTests {

    @Test
    public void getName() {
        // Arrange
        var expectedName = "baz";
        var dirs = List.of("foo", "bar", "baz");
        var d = new DirectoryDescription(dirs);

        // Act
        var actualName = d.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getAncestors() {
        // Arrange
        var expectedAncestors = List.of("foo", "bar");
        var dirs = List.of("foo", "bar", "baz");
        var d = new DirectoryDescription(dirs);

        // Act
        var actualAncestors = d.getAncestors();

        // Assert
        assertEquals(expectedAncestors, actualAncestors);
    }
}
