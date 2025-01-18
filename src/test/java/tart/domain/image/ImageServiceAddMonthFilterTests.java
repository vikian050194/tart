package tart.domain.image;

import tart.data.image.TestImageRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.domain.image.ImageService;

public class ImageServiceAddMonthFilterTests {

    @Test
    public void updated() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240201_120000.jpg"));
        f.add(new FileWrapper86("20240301_120000.jpeg"));
        var ir = new TestImageRepository(f);
        var is = new ImageService(ir);
        var expectedDays = List.of(
                new Mask("01", true, false)
        );
        var expectedMonths = List.of(
                new Mask("01", true, true),
                new Mask("02", false, false),
                new Mask("03", false, false)
        );
        var expectedYears = List.of(
                new Mask("2024", true, false)
        );

        // Act
        is.scan("test");
        is.addMonthFilter("01");
        var actualDays = is.getDays();
        var actualMonths = is.getMonths();
        var actualYears = is.getYears();

        // Assert
        assertEquals(expectedDays, actualDays);
        assertEquals(expectedMonths, actualMonths);
        assertEquals(expectedYears, actualYears);
    }
}
