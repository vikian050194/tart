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

    private void filter() {
        files.clear();
        var stringPattern = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
        var unsortedFiles = listFiles(root, new InlineFileMatcher(stringPattern));
        var sortedFiles = sortFiles(unsortedFiles);
        files.addAll(sortedFiles);

        updatePossibleValues();
    }

    private void updatePossibleValues() {
        var prevYears = new String[ys.size()];
        ys.toArray(prevYears);
        var prevMonths = new String[ms.size()];
        ms.toArray(prevMonths);
        var prevDays = new String[ds.size()];
        ds.toArray(prevDays);

        ys.clear();
        ms.clear();
        ds.clear();

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

            if (!ys.contains(year)) {
                ys.add(year);
            }

            if (!ms.contains(month)) {
                ms.add(month);
            }

            if (!ds.contains(day)) {
                ds.add(day);
            }
        }

        ys.sort((a, b) -> a.compareTo(b));
        ms.sort((a, b) -> a.compareTo(b));
        ds.sort((a, b) -> a.compareTo(b));

        var years = new String[ys.size()];
        ys.toArray(years);
        var months = new String[ms.size()];
        ms.toArray(months);
        var days = new String[ds.size()];
        ds.toArray(days);

        isYearsUpdated = isYearsUpdated || !Arrays.equals(prevYears, years);
        isMonthsUpdated = isMonthsUpdated || !Arrays.equals(prevMonths, months);
        isDaysUpdated = isDaysUpdated || !Arrays.equals(prevDays, days);
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

        filter();

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

        filter();
    }

    public void setMonthFilter(String mask) {
        if (mask.equals(monthMask) || mask.equals("ALL")) {
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

        filter();
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

        filter();
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
