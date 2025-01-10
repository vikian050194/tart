package tart.core.matcher;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InvertedFileMatcherTests {

    @Test
    public void simplePatternFail() {
        // Arrange
        var innerFileMather = new InlineFileMatcher("foo.*");
        var invertedFileMatcher = new InvertedFileMatcher(innerFileMather);
        var testFile = new File("foo.json");
        var expected = false;

        // Act
        var actual = invertedFileMatcher.isNameMatch(testFile);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void simplePatternSuccess() {
        // Arrange
        var innerFileMather = new InlineFileMatcher("bar");
        var invertedFileMatcher = new InvertedFileMatcher(innerFileMather);
        var testFile = new File("foo.json");
        var expected = true;

        // Act
        var actual = invertedFileMatcher.isNameMatch(testFile);

        // Assert
        assertEquals(actual, expected);
    }
}
