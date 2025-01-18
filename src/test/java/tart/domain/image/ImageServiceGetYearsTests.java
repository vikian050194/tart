package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceGetYearsTests {

    @Test
    public void noValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = List.of();

        // Act
        is.scan("test");
        var actual = is.getYears();

        // Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void oneValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = List.of(
                new Mask("2024", true, false)
        );

        // Act
        is.scan("test");
        var actual = is.getYears();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void threeUniqueValues() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20220301_120000.jpg"));
        f.add(new FileWrapper86("20230501_120000.jpeg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = List.of(
                new Mask("2022", true, false),
                new Mask("2023", true, false),
                new Mask("2024", true, false)
        );

        // Act
        is.scan("test");
        var actual = is.getYears();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void oneUniqueValue() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240201_120000.png"));
        f.add(new FileWrapper86("20240201_130000.png"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expected = List.of(
                new Mask("2024", true, false)
        );

        // Act
        is.scan("test");
        var actual = is.getYears();

        // Assert
        assertEquals(expected, actual);
    }
}
