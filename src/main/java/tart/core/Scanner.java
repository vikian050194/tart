package tart.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import tart.core.fs.FileSystemManager;
import tart.core.matcher.AllFileMatcher;
import tart.core.matcher.InlineFileMatcher;

public class Scanner {

    private boolean ready;

    private final FileSystemManager fsManager;
    private final ArrayList<File> filFiles = new ArrayList<>();

    private boolean isYearsUpdated = false;
    private boolean isMonthsUpdated = false;
    private boolean isDaysUpdated = false;

    private int index = 0;

    private boolean isAllYears = true;
    private boolean isAllMonths = true;
    private boolean isAllDays = true;

    private final ArrayList<String> allYears = new ArrayList<>();
    private final ArrayList<String> allMonths = new ArrayList<>();
    private final ArrayList<String> allDays = new ArrayList<>();

    private final ArrayList<String> yearMasks = new ArrayList<>();
    private final ArrayList<String> monthMasks = new ArrayList<>();
    private final ArrayList<String> dayMasks = new ArrayList<>();

    public Scanner(FileSystemManager fsm) {
        fsManager = fsm;
    }

    private void filter() {
        filFiles.clear();

        for (String yearMask : getYearMasks()) {
            for (String monthMask : getMonthMasks()) {
                for (String dayMask : getDayMasks()) {
                    var stringPattern = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
                    var matcher = new InlineFileMatcher(stringPattern);
                    var result = fsManager.getFiles().stream().filter((f) -> matcher.isMatch(f)).filter((f) -> !filFiles.contains(f));
                    filFiles.addAll(result.toList());
                }
            }
        }

        filFiles.sort((a, b) -> a.getName().compareTo(b.getName()));

//        updatePossibleValues(skipFilter);
    }

    private void updatePossibleValues(Filters skipFilter) {
        var prevYears = new String[allYears.size()];
        allYears.toArray(prevYears);
        var prevMonths = new String[allMonths.size()];
        allMonths.toArray(prevMonths);
        var prevDays = new String[allDays.size()];
        allDays.toArray(prevDays);

        ArrayList<String> newYears = new ArrayList<>();
        ArrayList<String> newMonths = new ArrayList<>();
        ArrayList<String> newDays = new ArrayList<>();

        for (File f : filFiles) {
            var year = f.getName().substring(0, 4);
            var month = f.getName().substring(4, 6);
            var day = f.getName().substring(6, 8);
//
//            if (!year.matches(yearMask)) {
//                continue;
//            }
//
//            if (!month.matches(monthMask)) {
//                continue;
//            }
//
//            if (!day.matches(dayMask)) {
//                continue;
//            }

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
            allYears.clear();
            allYears.addAll(newYears);
            allYears.sort((a, b) -> a.compareTo(b));
            var years = new String[allYears.size()];
            allYears.toArray(years);
            isYearsUpdated = isYearsUpdated || !Arrays.equals(prevYears, years);
        }

        if (skipFilter != Filters.MONTH) {
            allMonths.clear();
            allMonths.addAll(newMonths);
            allMonths.sort((a, b) -> a.compareTo(b));
            var months = new String[allMonths.size()];
            allMonths.toArray(months);
            isMonthsUpdated = isMonthsUpdated || !Arrays.equals(prevMonths, months);
        }

        if (skipFilter != Filters.DAY) {
            allDays.clear();
            allDays.addAll(newDays);
            allDays.sort((a, b) -> a.compareTo(b));
            var days = new String[allDays.size()];
            allDays.toArray(days);
            isDaysUpdated = isDaysUpdated || !Arrays.equals(prevDays, days);
        }
    }

    /**
     *
     * @param dir directories tree traverse start
     */
    public void scan(String dir) {
        var rootDir = new File(dir);

        ready = fsManager.inspect(rootDir, new AllFileMatcher());

        if (ready) {
            filter();
            updatePossibleValues(Filters.NONE);
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

    public void addYearFilter(String mask) {
        if (mask.equals("ALL")) {
            isAllYears = true;
            filter();
            return;
        }

        if (yearMasks.contains(mask)) {
            return;
        }

        isAllYears = false;
        yearMasks.add(mask);

        filter();
    }

    public void removeYearFilter(String mask) {
        yearMasks.remove(mask);

        if (yearMasks.isEmpty()) {
            isAllYears = true;
        }

        filter();
    }

    public void addMonthFilter(String mask) {
        if (mask.equals("ALL")) {
            isAllMonths = true;
            filter();
            return;
        }

        if (monthMasks.contains(mask)) {
            return;
        }

        isAllMonths = false;
        monthMasks.add(mask);

        filter();
    }

    public void removeMonthFilter(String mask) {
        monthMasks.remove(mask);

        if (monthMasks.isEmpty()) {
            isAllMonths = true;
        }

        filter();
    }

    public void addDayFilter(String mask) {
        if (mask.equals("ALL")) {
            isAllDays = true;
            filter();
            return;
        }

        if (dayMasks.contains(mask)) {
            return;
        }

        isAllDays = false;
        dayMasks.add(mask);

        filter();
    }

    public void removeDayFilter(String mask) {
        dayMasks.remove(mask);

        if (dayMasks.isEmpty()) {
            isAllDays = true;
        }

        filter();
    }

    public String[] getYears() {
        yearsReviewed();

        var years = new String[allYears.size()];
        allYears.toArray(years);

        return years;
    }

    public String[] getMonths() {
        monthsReviewed();

        var months = new String[allMonths.size()];
        allMonths.toArray(months);

        return months;
    }

    public String[] getDays() {
        daysReviewed();

        var days = new String[allDays.size()];
        allDays.toArray(days);

        return days;
    }

    public String[] getYearMasks() {
        if (isAllYears) {
            // TODO extract string
            return new String[]{"20\\d{2}"};
        }

        var years = new String[yearMasks.size()];
        yearMasks.toArray(years);

        return years;
    }

    public String[] getMonthMasks() {
        if (isAllMonths) {
            // TODO extract string
            return new String[]{"\\d{2}"};
        }

        var months = new String[monthMasks.size()];
        monthMasks.toArray(months);

        return months;
    }

    public String[] getDayMasks() {
        if (isAllDays) {
            // TODO extract string
            return new String[]{"\\d{2}"};
        }

        var days = new String[dayMasks.size()];
        dayMasks.toArray(days);

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
