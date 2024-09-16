package tart.app;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.*;
import tart.app.components.filter.FilterModel;
import tart.app.components.filter.Mask;
import tart.core.fs.FileSystemManager;
import tart.core.matcher.InlineFileMatcher;
import tart.core.matcher.data.FileMatcher86All;
import tart.core.matcher.type.JpgFileMatcher;

public class AppModel {

    private static final String YEAR_MASK = "20\\d{2}";
    private static final String MONTH_MASK = "\\d{2}";
    private static final String DAY_MASK = "\\d{2}";

    protected ChangeEvent changeEvent = null;
    protected EventListenerList listenerList = new EventListenerList();

    private int index;
    private int size;

    private boolean ready;

    private final FileSystemManager fsManager;
    private final List<File> filFiles = new ArrayList<>();

    private boolean isYearsUpdated = false;
    private boolean isMonthsUpdated = false;
    private boolean isDaysUpdated = false;

    private final List<String> possibleYears = new ArrayList<>();
    private final List<String> possibleMonths = new ArrayList<>();
    private final List<String> possibleDays = new ArrayList<>();

    private final List<String> availableYears = new ArrayList<>();
    private final List<String> availableMonths = new ArrayList<>();
    private final List<String> availableDays = new ArrayList<>();

    private final FilterModel yearsFilter = new FilterModel(YEAR_MASK);
    private final FilterModel monthsFilter = new FilterModel(MONTH_MASK);
    private final FilterModel daysFilter = new FilterModel(DAY_MASK);

    public AppModel(FileSystemManager fsm) {
        fsManager = fsm;
    }

    public void filter() {
        // TODO is it possible to remove this boilerplate array creating?
        var prevFil = new File[filFiles.size()];
        filFiles.toArray(prevFil);
        filFiles.clear();

        for (String yearMask : yearsFilter.get()) {
            for (String monthMask : monthsFilter.get()) {
                for (String dayMask : daysFilter.get()) {
                    var stringPattern = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
                    var matcher = new InlineFileMatcher(stringPattern);

                    var result = fsManager.getFiles().stream()
                            .filter((f) -> matcher.isMatch(f));
                    filFiles.addAll(result.toList());
                }
            }
        }

        filFiles.sort((a, b) -> a.getName().compareTo(b.getName()));

        // TODO is it possible to remove this boilerplate array creating?
        var newFil = new File[filFiles.size()];
        filFiles.toArray(newFil);
        var updated = !Arrays.equals(prevFil, newFil);

        if (updated) {
            index = 0;
        }

        updateAvailableValues();

        size = filFiles.size();

        fireStateChanged();
    }

    private void updateAvailableValues() {
        var prevYears = new String[availableYears.size()];
        availableYears.toArray(prevYears);
        var prevMonths = new String[availableMonths.size()];
        availableMonths.toArray(prevMonths);
        var prevDays = new String[availableDays.size()];
        availableDays.toArray(prevDays);

        List<String> newYears = new ArrayList<>();
        List<String> newMonths = new ArrayList<>();
        List<String> newDays = new ArrayList<>();

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

        // TODO extract similar code to method
        availableYears.clear();
        availableYears.addAll(newYears);
        availableYears.sort((a, b) -> a.compareTo(b));
        var years = new String[availableYears.size()];
        availableYears.toArray(years);
        isYearsUpdated = isYearsUpdated || !Arrays.equals(prevYears, years);

        availableMonths.clear();
        availableMonths.addAll(newMonths);
        availableMonths.sort((a, b) -> a.compareTo(b));
        var months = new String[availableMonths.size()];
        availableMonths.toArray(months);
        isMonthsUpdated = isMonthsUpdated || !Arrays.equals(prevMonths, months);

        availableDays.clear();
        availableDays.addAll(newDays);
        availableDays.sort((a, b) -> a.compareTo(b));
        var days = new String[availableDays.size()];
        availableDays.toArray(days);
        isDaysUpdated = isDaysUpdated || !Arrays.equals(prevDays, days);
    }

    // TODO extract common with updateAvailableValues code
    private void updatePossibleValues() {
        List<String> newYears = new ArrayList<>();
        List<String> newMonths = new ArrayList<>();
        List<String> newDays = new ArrayList<>();

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

        // TODO extract similar code to method
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

    private void reset() {
        // TODO reset fsManager?

        ready = false;

        index = 0;

        filFiles.clear();

        isYearsUpdated = false;
        isMonthsUpdated = false;
        isDaysUpdated = false;

        possibleYears.clear();
        possibleMonths.clear();
        possibleDays.clear();

        availableYears.clear();
        availableMonths.clear();
        availableDays.clear();

        yearsFilter.clear();
        monthsFilter.clear();
        daysFilter.clear();
    }

    /**
     *
     * @param dir directories tree traverse start
     */
    public void scan(String dir) {
        reset();

        var rootDir = new File(dir);

        ready = fsManager.inspect(rootDir, new FileMatcher86All(new JpgFileMatcher()));

        if (ready) {
            updatePossibleValues();

            filter();
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

        fireStateChanged();
    }

    public void gotoNextFile() {
        index++;

        if (index >= filFiles.size()) {
            index = 0;
        }

        fireStateChanged();
    }

    public void deleteFile() {
        // TODO handle "last image is deleted" case
        var targetFile = getFile();
        filFiles.remove(targetFile);
        fsManager.delete(targetFile);

        size = filFiles.size();

        fireStateChanged();
    }

    public File getFile() {
        if (filFiles.isEmpty()) {
            return null;
        }

        return filFiles.get(index);
    }

    public Image getImage() {
        var file = getFile();

        try {
            var image = ImageIO.read(file);
            return image;
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void setFile(File newFile) {
        if (filFiles.isEmpty()) {
            return;
        }

        filFiles.set(index, newFile);
    }

    public File getRoot() {
        return fsManager.getRoot();
    }

    public int getFilesCount() {
        return filFiles.size();
    }

    public int getFileIndex() {
        return index;
    }

    public List<Mask> getYears() {
        return possibleYears.stream()
                .map((y) -> new Mask(y, availableYears.isEmpty() || availableYears.contains(y), yearsFilter.contains(y)))
                .toList();
    }

    public List<Mask> getMonths() {
        return possibleMonths.stream()
                .map((y) -> new Mask(y, availableMonths.isEmpty() || availableMonths.contains(y), monthsFilter.contains(y)))
                .toList();
    }

    public List<Mask> getDays() {
        return possibleDays.stream()
                .map((y) -> new Mask(y, availableDays.isEmpty() || availableDays.contains(y), daysFilter.contains(y)))
                .toList();
    }

    public void addYearFilter(String mask) {
        if (yearsFilter.add(mask)) {
            filter();
        }
    }

    public void addMonthFilter(String mask) {
        if (monthsFilter.add(mask)) {
            filter();
        }
    }

    public void addDayFilter(String mask) {
        if (daysFilter.add(mask)) {
            filter();
        }
    }

    public void removeYearFilter(String mask) {
        if (yearsFilter.remove(mask)) {
            filter();
        }
    }

    public void removeMonthFilter(String mask) {
        if (monthsFilter.remove(mask)) {
            filter();
        }
    }

    public void removeDayFilter(String mask) {
        if (daysFilter.remove(mask)) {
            filter();
        }
    }

    public void moveTo(File target) {
        // TODO add unit tests
        var file = getFile();
        var newFile = fsManager.moveTo(file, target);
        setFile(newFile);

        fireStateChanged();
    }

    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    protected void fireStateChanged() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }
}
