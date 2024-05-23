package tart.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.InlineFileMatcher;

public class Scanner {

    private File root;

    private final ArrayList<File> files = new ArrayList<>();

    private final ArrayList<String> ys = new ArrayList<>();
    private final ArrayList<String> ms = new ArrayList<>();
    private final ArrayList<String> ds = new ArrayList<>();
    private boolean isYearsUpdated = false;
    private boolean isMonthsUpdated = false;
    private boolean isDaysUpdated = false;

    private int index = 0;

    private String yearMask = "20\\d{2}";
    private String monthMask = "\\d{2}";
    private String dayMask = "\\d{2}";

    public Scanner() {

    }

    private List<File> listFiles(File dir, FileMatcher fileMather) {
        var result = new ArrayList<File>();
        var queue = new LinkedList<File>();
        queue.add(dir);

        while (!queue.isEmpty()) {
            var currentDir = queue.poll();
            var dirs = Stream.of(currentDir.listFiles())
                    .filter(file -> file.isDirectory())
                    .collect(Collectors.toList());
            queue.addAll(dirs);
            var currentDirFiles = Stream.of(currentDir.listFiles())
                    .filter(file -> !file.isDirectory() && fileMather.isMatch(file))
                    .collect(Collectors.toList());
            result.addAll(currentDirFiles);
        }

        return result;
    }

    private List<File> sortFiles(List<File> source) {
        var result = new ArrayList<File>(source.size());
        result.addAll(source);
        result.sort((a, b) -> a.getName().compareTo(b.getName()));
        return result;
    }

    private void filter(Filters skipFilter) {
        files.clear();
        var stringPattern = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
        var unsortedFiles = listFiles(root, new InlineFileMatcher(stringPattern));
        var sortedFiles = sortFiles(unsortedFiles);
        files.addAll(sortedFiles);

        updatePossibleValues(skipFilter);
    }

    private void updatePossibleValues(Filters skipFilter) {
        var prevYears = new String[ys.size()];
        ys.toArray(prevYears);
        var prevMonths = new String[ms.size()];
        ms.toArray(prevMonths);
        var prevDays = new String[ds.size()];
        ds.toArray(prevDays);

        ArrayList<String> newYears = new ArrayList<>();
        ArrayList<String> newMonths = new ArrayList<>();
        ArrayList<String> newDays = new ArrayList<>();

        for (File f : files) {
            var year = f.getName().substring(0, 4);
            var month = f.getName().substring(4, 6);
            var day = f.getName().substring(6, 8);

            if (!year.matches(yearMask)) {
                continue;
            }

            if (!month.matches(monthMask)) {
                continue;
            }

            if (!day.matches(dayMask)) {
                continue;
            }

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
            ys.clear();
            ys.addAll(newYears);
            ys.sort((a, b) -> a.compareTo(b));
            var years = new String[ys.size()];
            ys.toArray(years);
            isYearsUpdated = isYearsUpdated || !Arrays.equals(prevYears, years);
        }

        if (skipFilter != Filters.MONTH) {
            ms.clear();
            ms.addAll(newMonths);
            ms.sort((a, b) -> a.compareTo(b));
            var months = new String[ms.size()];
            ms.toArray(months);
            isMonthsUpdated = isMonthsUpdated || !Arrays.equals(prevMonths, months);
        }

        if (skipFilter != Filters.DAY) {
            ds.clear();
            ds.addAll(newDays);
            ds.sort((a, b) -> a.compareTo(b));
            var days = new String[ds.size()];
            ds.toArray(days);
            isDaysUpdated = isDaysUpdated || !Arrays.equals(prevDays, days);
        }
    }

    /**
     *
     * @param dir directories tree traverse start
     */
    public void scan(String dir) {
        var rootDir = new File(dir);

        if (!rootDir.exists()) {
            System.out.printf("%s is not found%n", dir);
            return;
        }

        if (rootDir.isFile()) {
            System.out.printf("%s is not directory%n", dir);
            return;
        }

        root = rootDir;

        filter(Filters.NONE);

//        updatePossibleValues();
//        yearsUpdated();
//        unsortedFiles = listFiles(rootDir, new AllFileMatcher());
//        sortedFiles = sortFiles(unsortedFiles);
//        files.addAll(sortedFiles);
    }

    public boolean isReady() {
        return root != null;
    }

    public void gotoPreviousFile() {
        index--;

        if (index < 0) {
            index = files.size() - 1;
        }
    }

    public void gotoNextFile() {
        index++;

        if (index >= files.size()) {
            index = 0;
        }
    }

    public File getFile() {
        return files.get(index);
    }

    public File getRoot() {
        return root;
    }

    public int getFilesCount() {
        return files.size();
    }

    public void setYearFilter(String mask) {
        if (mask.equals(yearMask)) {
            return;
        }

        if (mask.equals("ALL") && yearMask.equals("20\\d{2}")) {
            return;
        }

        if (mask.equals("ALL")) {
            yearMask = "20\\d{2}";
        } else {
            yearMask = mask;
        }

        filter(Filters.YEAR);
    }

    public void setMonthFilter(String mask) {
        if (mask.equals(monthMask)) {
            return;
        }

        if (mask.equals("ALL") && monthMask.equals("\\d{2}")) {
            return;
        }

        if (mask.equals("ALL")) {
            monthMask = "\\d{2}";
        } else {
            monthMask = mask;
        }

        filter(Filters.MONTH);
    }

    public void setDayFilter(String mask) {
        if (mask.equals(dayMask)) {
            return;
        }

        if (mask.equals("ALL") && dayMask.equals("\\d{2}")) {
            return;
        }

        if (mask.equals("ALL")) {
            dayMask = "\\d{2}";
        } else {
            dayMask = mask;

        }

        filter(Filters.DAY);
    }

    public String[] getYears() {
        yearsReviewed();

        var years = new String[ys.size()];
        ys.toArray(years);

        return years;
    }

    public String[] getMonths() {
        monthsReviewed();

        var months = new String[ms.size()];
        ms.toArray(months);

        return months;
    }

    public String[] getDays() {
        daysReviewed();

        var days = new String[ds.size()];
        ds.toArray(days);

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

    private void yearsUpdated() {
        isYearsUpdated = true;
    }

    private void monthsUpdated() {
        isMonthsUpdated = true;
    }

    private void daysUpdated() {
        isDaysUpdated = true;
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
