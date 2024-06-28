package tart.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tart.core.fs.FileSystemManager;
import tart.core.matcher.InlineFileMatcher;
import tart.core.matcher.type.JpgFileMatcher;

public class Scanner {

    private boolean ready;

    private final FileSystemManager fsManager;
    private final List<File> filFiles = new ArrayList<>();

    private boolean isYearsUpdated = false;
    private boolean isMonthsUpdated = false;
    private boolean isDaysUpdated = false;

    private int index = 0;

    private final List<String> possibleYears = new ArrayList<>();
    private final List<String> possibleMonths = new ArrayList<>();
    private final List<String> possibleDays = new ArrayList<>();

    private final List<String> availableYears = new ArrayList<>();
    private final List<String> availableMonths = new ArrayList<>();
    private final List<String> availableDays = new ArrayList<>();

    public final DateFilter filter = new DateFilter();

    public Scanner(FileSystemManager fsm) {
        fsManager = fsm;
    }

    public void filter() {
        filFiles.clear();

        for (String yearMask : filter.getYearMasks()) {
            for (String monthMask : filter.getMonthMasks()) {
                for (String dayMask : filter.getDayMasks()) {
                    var stringPattern = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
                    var matcher = new InlineFileMatcher(stringPattern);
                    var result = fsManager.getFiles().stream()
                            .filter((f) -> matcher.isMatch(f))
                            .filter((f) -> !filFiles.contains(f));
                    filFiles.addAll(result.toList());
                }
            }
        }

        filFiles.sort((a, b) -> a.getName().compareTo(b.getName()));

//        updatePossibleValues(skipFilter);
    }

    private void updateAvailableValues(Filters skipFilter) {
        var prevYears = new String[availableYears.size()];
        availableYears.toArray(prevYears);
        var prevMonths = new String[availableMonths.size()];
        availableMonths.toArray(prevMonths);
        var prevDays = new String[availableDays.size()];
        availableDays.toArray(prevDays);

        ArrayList<String> newYears = new ArrayList<>();
        ArrayList<String> newMonths = new ArrayList<>();
        ArrayList<String> newDays = new ArrayList<>();

        for (File f : filFiles) {
            var year = f.getName().substring(0, 4);
            var month = f.getName().substring(4, 6);
            var day = f.getName().substring(6, 8);

            if (!newYears.contains(year)) {
                newYears.add(year);
            }

            if (!newMonths.contains(month)) {
                newMonths.add(month);
            }

            if (!newDays.contains(day)) {
                newDays.add(day);
            }
        }

        if (skipFilter != Filters.YEAR) {
            availableYears.clear();
            availableYears.addAll(newYears);
            availableYears.sort((a, b) -> a.compareTo(b));
            var years = new String[availableYears.size()];
            availableYears.toArray(years);
            isYearsUpdated = isYearsUpdated || !Arrays.equals(prevYears, years);
        }

        if (skipFilter != Filters.MONTH) {
            availableMonths.clear();
            availableMonths.addAll(newMonths);
            availableMonths.sort((a, b) -> a.compareTo(b));
            var months = new String[availableMonths.size()];
            availableMonths.toArray(months);
            isMonthsUpdated = isMonthsUpdated || !Arrays.equals(prevMonths, months);
        }

        if (skipFilter != Filters.DAY) {
            availableDays.clear();
            availableDays.addAll(newDays);
            availableDays.sort((a, b) -> a.compareTo(b));
            var days = new String[availableDays.size()];
            availableDays.toArray(days);
            isDaysUpdated = isDaysUpdated || !Arrays.equals(prevDays, days);
        }
    }

    private void updatePossibleValues() {
        ArrayList<String> newYears = new ArrayList<>();
        ArrayList<String> newMonths = new ArrayList<>();
        ArrayList<String> newDays = new ArrayList<>();

        for (File f : fsManager.getFiles()) {
            var year = f.getName().substring(0, 4);
            var month = f.getName().substring(4, 6);
            var day = f.getName().substring(6, 8);

            if (!newYears.contains(year)) {
                newYears.add(year);
            }

            if (!newMonths.contains(month)) {
                newMonths.add(month);
            }

            if (!newDays.contains(day)) {
                newDays.add(day);
            }
        }

        possibleYears.clear();
        possibleYears.addAll(newYears);
        possibleYears.sort((a, b) -> a.compareTo(b));
        var years = new String[possibleYears.size()];
        possibleYears.toArray(years);

        possibleMonths.clear();
        possibleMonths.addAll(newMonths);
        possibleMonths.sort((a, b) -> a.compareTo(b));
        var months = new String[possibleMonths.size()];
        possibleMonths.toArray(months);

        possibleDays.clear();
        possibleDays.addAll(newDays);
        possibleDays.sort((a, b) -> a.compareTo(b));
        var days = new String[possibleDays.size()];
        possibleDays.toArray(days);
    }

    /**
     *
     * @param dir directories tree traverse start
     */
    public void scan(String dir) {
        var rootDir = new File(dir);

        ready = fsManager.inspect(rootDir, new JpgFileMatcher());

        if (ready) {
            filter();
            updatePossibleValues();
            updateAvailableValues(Filters.NONE);
        }
    }

    public boolean isReady() {
        return ready;
    }

    public void gotoPreviousFile() {
        index--;

        if (index < 0) {
            index = filFiles.size() - 1;
        }
    }

    public void gotoNextFile() {
        index++;

        if (index >= filFiles.size()) {
            index = 0;
        }
    }

    public File getFile() {
        return filFiles.get(index);
    }

    public File getRoot() {
        return fsManager.getRoot();
    }

    public int getFilesCount() {
        return filFiles.size();
    }

    public String[] getPossibleYears() {
        yearsReviewed();

        var years = new String[possibleYears.size()];
        possibleYears.toArray(years);

        return years;
    }

    public String[] getPossibleMonths() {
        monthsReviewed();

        var months = new String[possibleMonths.size()];
        possibleMonths.toArray(months);

        return months;
    }

    public String[] getPossibleDays() {
        daysReviewed();

        var days = new String[possibleDays.size()];
        possibleDays.toArray(days);

        return days;
    }

    public boolean isYearsUpdated() {
        return isYearsUpdated;
    }

    public boolean isMonthsUpdated() {
        return isMonthsUpdated;
    }

    public boolean isDaysUpdated() {
        return isDaysUpdated;
    }

    private void yearsReviewed() {
        isYearsUpdated = false;
    }

    private void monthsReviewed() {
        isMonthsUpdated = false;
    }

    private void daysReviewed() {
        isDaysUpdated = false;
    }
}
