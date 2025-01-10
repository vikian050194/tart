package tart.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.components.filter.Mask;
import tart.app.core.wrapper.FileWrapper;
import tart.app.core.wrapper.FileWrapper86;
import tart.core.fs.TestFileSystemManager;

public class AppModelRemoveMonthFilterTests {

    @Test
    public void updated() {
        // Arrange
        var f = new ArrayList<FileWrapper>();
        f.add(new FileWrapper86("20240101_120000.png"));
        f.add(new FileWrapper86("20240201_120000.jpg"));
        f.add(new FileWrapper86("20240301_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
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
        s.scan("test");
        s.addMonthFilter("02");
        s.addMonthFilter("01");
        s.removeMonthFilter("02");
        var actualDays = s.getDays();
        var actualMonths = s.getMonths();
        var actualYears = s.getYears();

        // Assert
        assertEquals(expectedDays, actualDays);
        assertEquals(expectedMonths, actualMonths);
        assertEquals(expectedYears, actualYears);
    }
}
