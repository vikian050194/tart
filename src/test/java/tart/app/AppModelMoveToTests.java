package tart.app;

import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelMoveToTests {

    @Test
    public void updateMiddle() {
        // Arrange
        var testTarget = new File("foo");
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.jpg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expected = new FileWrapper86("foo/20240101_120000.jpg");

        // Act
        s.scan("test");
        s.moveTo(testTarget);
        var actual = s.getFile();

        // Assert
        assertEquals(expected.getFile(), actual.getFile());
    }

}
