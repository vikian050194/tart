package tart.core.matcher;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InlineFileMatcherTests {

    @Test
    public void simplePatternSuccess() {
        // Arrange
        var ifm = new InlineFileMatcher("foo.*");
        var testFile = new File("foo.json");
        var expected = true;

        // Act
        var actual = ifm.isNameMatch(testFile);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void simplePatternFail() {
        // Arrange
        var ifm = new InlineFileMatcher("bar");
        var testFile = new File("foo.json");
        var expected = false;

        // Act
        var actual = ifm.isNameMatch(testFile);

        // Assert
        assertEquals(actual, expected);
    }
}
