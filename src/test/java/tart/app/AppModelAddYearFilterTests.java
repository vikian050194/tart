package tart.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tart.app.components.filter.Mask;
import tart.core.fs.TestFileSystemManager;

public class AppModelAddYearFilterTests {

    @Test
    public void updated() {
        // Arrange
        var f = new ArrayList<File>();
        f.add(new File("20220101_120000.png"));
        f.add(new File("20230103_120000.jpg"));
        f.add(new File("20240105_120000.jpeg"));
        var fsm = new TestFileSystemManager(f);
        var s = new AppModel(fsm);
        var expectedDays = List.of(
                new Mask("01", false, false),
                new Mask("03", false, false),
                new Mask("05", true, false)
        );
        var expectedMonths = List.of(
                new Mask("01", true, false)
        );
        var expectedYears = List.of(
                new Mask("2022", false, false),
                new Mask("2023", false, false),
                new Mask("2024", true, true)
        );

        // Act
        s.scan("test");
        s.addYearFilter("2024");
        var actualDays = s.getDays();
        var actualMonths = s.getMonths();
        var actualYears = s.getYears();

        // Assert
        assertEquals(expectedDays, actualDays);
        assertEquals(expectedMonths, actualMonths);
        assertEquals(expectedYears, actualYears);
    }
}
