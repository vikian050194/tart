package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceGoToFileTests {

    @Test
    public void nextFileNoFile() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        File expected = null;

        // Act
        is.scan("test");
        is.gotoNextFile();
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void nextFile() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = f.get(1);

        // Act
        is.scan("test");
        is.gotoNextFile();
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFileNoFile() {
        // Arrange
        var ir = new TestImageRepository();
        var is = new ImageService(ir);
        File expected = null;

        // Act
        is.scan("test");
        is.gotoPreviousFile();
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFile() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = f.get(1);

        // Act
        is.scan("test");
        is.gotoPreviousFile();
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void previousFileNonInitial() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = f.get(0);

        // Act
        is.scan("test");
        is.gotoNextFile();
        is.gotoPreviousFile();
        var actual = is.getFile();

        // Assert
        assertEquals(expected, actual);
    }

}
