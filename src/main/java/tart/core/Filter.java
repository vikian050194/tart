package tart.core;

import java.util.ArrayList;

public class Filter {

    private boolean isAllYears = true;
    private boolean isAllMonths = true;
    private boolean isAllDays = true;

    private final ArrayList<String> yearMasks = new ArrayList<>();
    private final ArrayList<String> monthMasks = new ArrayList<>();
    private final ArrayList<String> dayMasks = new ArrayList<>();

    public boolean addYearFilter(String mask) {
        if (mask.equals("ALL")) {
            isAllYears = true;
//            filter();
            // TODO add unit tests
            return true;
        }

        if (yearMasks.contains(mask)) {
            return false;
        }

        isAllYears = false;
        yearMasks.add(mask);

//        filter();
        return true;
    }

    public boolean addMonthFilter(String mask) {
        // TODO refactor this logic
        // extract it to upper level
        // add addAllMonthFilter method
        if (mask.equals("ALL")) {
            isAllMonths = true;
//            filter();
            return true;
        }

        if (monthMasks.contains(mask)) {
            return false;
        }

        isAllMonths = false;
        monthMasks.add(mask);

        return true;
//        filter();
    }

    public boolean addDayFilter(String mask) {
        if (mask.equals("ALL")) {
            isAllDays = true;
//            filter();
            return true;
        }

        if (dayMasks.contains(mask)) {
            return false;
        }

        isAllDays = false;
        dayMasks.add(mask);

        return false;
//        filter();
    }

    public boolean removeYearFilter(String mask) {
        yearMasks.remove(mask);

        if (yearMasks.isEmpty()) {
            isAllYears = true;
        }

        return true;
//        filter();
    }

    public boolean removeMonthFilter(String mask) {
        monthMasks.remove(mask);

        if (monthMasks.isEmpty()) {
            isAllMonths = true;
        }

        return true;
//        filter();
    }

    public boolean removeDayFilter(String mask) {
        dayMasks.remove(mask);

        if (dayMasks.isEmpty()) {
            isAllDays = true;
        }

        return false;
//        filter();
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

}
