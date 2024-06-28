package tart.core;

import java.util.ArrayList;

public class DateFilter {

    private static final String YEAR_MASK = "20\\d{2}";
    private static final String MONTH_MASK = "\\d{2}";
    private static final String DAY_MASK = "\\d{2}";

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

    // TODO fix strange naming - filter or mask?
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

        return true;
//        filter();
    }

    public boolean removeYearFilter(String mask) {
        if (mask.equals("ALL")) {
            if (isAllYears) {
                isAllYears = false;
                return true;
            }
//            filter();
            return false;
        }

        var result = yearMasks.remove(mask);

        if (yearMasks.isEmpty()) {
            // TODO should we setup isAllYears if collection is empty?
            isAllYears = true;
        }

        return result;
//        filter();
    }

    public boolean removeMonthFilter(String mask) {
        if (mask.equals("ALL")) {
            if (isAllMonths) {
                isAllMonths = false;
                return true;
            }
//            filter();
            return false;
        }

        var result = monthMasks.remove(mask);

        if (monthMasks.isEmpty()) {
            // TODO should we setup isAllMonths if collection is empty?
            isAllMonths = true;
        }

        return result;
//        filter();
    }

    public boolean removeDayFilter(String mask) {
        if (mask.equals("ALL")) {
            if (isAllDays) {
                isAllDays = false;
                return true;
            }
//            filter();
            return false;
        }

        var result = dayMasks.remove(mask);

        if (dayMasks.isEmpty()) {
            // TODO should we setup isAllDays if collection is empty?
            isAllDays = true;
        }

        return result;
//        filter();
    }

    public String[] getYearMasks() {
        if (isAllYears) {
            return new String[]{YEAR_MASK};
        }

        var years = new String[yearMasks.size()];
        yearMasks.toArray(years);

        return years;
    }

    public String[] getMonthMasks() {
        if (isAllMonths) {
            return new String[]{MONTH_MASK};
        }

        var months = new String[monthMasks.size()];
        monthMasks.toArray(months);

        return months;
    }

    public String[] getDayMasks() {
        if (isAllDays) {
            return new String[]{DAY_MASK};
        }

        var days = new String[dayMasks.size()];
        dayMasks.toArray(days);

        return days;
    }

}
