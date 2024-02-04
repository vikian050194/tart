package tart.core.matcher;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InlineFileMatcherTests {

    @Test
    public void simplePattern() {
        // Arrange
        var ifm = new InlineFileMatcher("foo.*");
        var testFile = new File("foo.json");
        var expected = true;

        // Act
        var actual = ifm.isMatch(testFile);

        // Assert
        assertEquals(actual, expected);
    }

}
