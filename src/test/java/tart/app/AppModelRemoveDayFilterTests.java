package tart.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.components.filter.Mask;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelRemoveDayFilterTests {

    @Test
    public void updated() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240103_120000.jpg"));
        f.add(new FileWrapper86("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expectedDays = List.of(
                new Mask("01", false, false),
                new Mask("03", true, true),
                new Mask("05", false, false)
        );
        var expectedMonths = List.of(
                new Mask("01", true, false)
        );
        var expectedYears = List.of(
                new Mask("2024", true, false)
        );

        // Act
        s.scan("test");
        s.addDayFilter("01");
        s.addDayFilter("03");
        s.removeDayFilter("01");
        var actualDays = s.getDays();
        var actualMonths = s.getMonths();
        var actualYears = s.getYears();

        // Assert
        assertEquals(expectedDays, actualDays);
        assertEquals(expectedMonths, actualMonths);
        assertEquals(expectedYears, actualYears);
    }
}
