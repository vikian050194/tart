package tart.app;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.event.*;
import tart.app.core.wrapper.FileWrapper;
import tart.core.fs.FileSystemManager;
import tart.core.logger.Logger;
import tart.core.matcher.InlineFileMatcher;
import tart.core.matcher.data.FileMatcher42;
import tart.core.matcher.data.FileMatcher86;
import tart.core.matcher.type.JpgFileMatcher;

public class AppModel {

    private static final String YEAR_MASK = "20\\d{2}";
    private static final String MONTH_MASK = "\\d{2}";
    private static final String DAY_MASK = "\\d{2}";
    private static final String DIR_MASK = ".*";

    protected ChangeEvent changeEvent = null;
    protected EventListenerList listenerList = new EventListenerList();

    private int index;
    private int size;

    private boolean ready;

    private final FileSystemManager fsManager;
    private final List<FileWrapper> filFiles = new ArrayList<>();

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
    private final FilterModel dirFilter = new FilterModel(DIR_MASK);

    public AppModel(FileSystemManager fsm) {
        fsManager = fsm;
    }

    public void filter() {
        // TODO is it possible to remove this boilerplate array creating?
        var prevFil = List.copyOf(filFiles);
        filFiles.clear();

        var dirMask = dirFilter.get().get(0);

        if (!dirFilter.isEmpty()) {
//            dirMask = String.format("%s/.*$", dirMask); // soft mode
            dirMask = String.format("%s/[^/]*\\..*$", dirMask); // strict mode
        }

        var matcher = new InlineFileMatcher(dirMask);

        for (String yearMask : yearsFilter.get()) {
            for (String monthMask : monthsFilter.get()) {
                for (String dayMask : daysFilter.get()) {
                    var datePatternSource = String.format("%s%s%s.*", yearMask, monthMask, dayMask);
                    var pattern = Pattern.compile(datePatternSource, Pattern.CASE_INSENSITIVE);

                    var result = fsManager.getFiles().stream()
                            .filter((f) -> matcher.isAbsoluteMatch(f.getFile()) && pattern.matcher(f.getTimestamp().format(DateTimeFormatter.BASIC_ISO_DATE)).matches());
                    filFiles.addAll(result.toList());
                }
            }
        }

        filFiles.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        var newFil = filFiles;
        var updated = !prevFil.equals(newFil);

        if (updated) {
            index = 0;
        }

        updateAvailableValues();

        size = filFiles.size();

        fireStateChanged();
    }

    private void updateAvailableValues() {
        var prevYears = List.copyOf(availableYears);
        var prevMonths = List.copyOf(availableMonths);
        var prevDays = List.copyOf(availableDays);

        List<String> newYears = new ArrayList<>();
        List<String> newMonths = new ArrayList<>();
        List<String> newDays = new ArrayList<>();

        for (FileWrapper f : filFiles) {
            var year = String.valueOf(f.getYear());
            var month = String.format("%02d", f.getMonth());
            var day = String.format("%02d", f.getDay());

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
        isYearsUpdated = isYearsUpdated || !prevYears.equals(newYears);

        availableMonths.clear();
        availableMonths.addAll(newMonths);
        availableMonths.sort((a, b) -> a.compareTo(b));
        isMonthsUpdated = isMonthsUpdated || !prevMonths.equals(newMonths);

        availableDays.clear();
        availableDays.addAll(newDays);
        availableDays.sort((a, b) -> a.compareTo(b));
        isDaysUpdated = isDaysUpdated || !prevDays.equals(newDays);

        // TODO update dir filter
    }

    // TODO extract common with updateAvailableValues code
    private void updatePossibleValues() {
        List<String> newYears = new ArrayList<>();
        List<String> newMonths = new ArrayList<>();
        List<String> newDays = new ArrayList<>();

        for (FileWrapper f : fsManager.getFiles()) {
            var year = String.valueOf(f.getYear());
            var month = String.format("%02d", f.getMonth());
            var day = String.format("%02d", f.getDay());

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

        possibleMonths.clear();
        possibleMonths.addAll(newMonths);
        possibleMonths.sort((a, b) -> a.compareTo(b));

        possibleDays.clear();
        possibleDays.addAll(newDays);
        possibleDays.sort((a, b) -> a.compareTo(b));

        // TODO update dir filter
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
        dirFilter.clear();
    }

    /**
     *
     * @param dir directories tree traverse start
     */
    public void scan(String dir) {
        reset();

        var rootDir = new File(dir);
        // TODO support few file matchers
        var matchers = List.of(
                new FileMatcher86(new JpgFileMatcher()),
                new FileMatcher42(new JpgFileMatcher())
        );
        ready = fsManager.inspect(rootDir, matchers);

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

    public FileWrapper getFile() {
        if (filFiles.isEmpty()) {
            return null;
        }

        return filFiles.get(index);
    }

    public Image getImage() {
        var file = getFile();

        try {
            var image = ImageIO.read(file.getFile());
            return image;
        } catch (IOException ex) {
            Logger.getLogger().finest(ex.toString());
        }

        return null;
    }

    private void setFile(File newFile) {
        if (filFiles.isEmpty()) {
            return;
        }

        // TODO refactoring is needed - set-get-clone looks awkward
        filFiles.set(index, filFiles.get(index).cloneWith(newFile));
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

    public List<Mask> getDirs() {
        var delimiter = "/";

        var values = new ArrayList<Mask>();
        List<String> currentMaskChunks;

        if (dirFilter.isEmpty()) {
            currentMaskChunks = List.of();
        } else {
            var currentMask = dirFilter.get().get(0);
            // TODO is it needed to filter values !p.isEmpty()?
            currentMaskChunks = Arrays.stream(currentMask.split(delimiter)).filter(p -> !p.isEmpty()).toList();
        }

        var file = getFile();
        // TODO get delimenter value for current OS
        var pathChunks = file.getFile().getAbsolutePath()
                .substring(getRoot().getParentFile().getAbsolutePath().length())
                .split(delimiter);
        var depthToParent = getRoot().getParentFile().getAbsolutePath()
                .split(delimiter).length - 1;
        // TODO is it needed to filter values !p.isEmpty()?
        var valuableChunks = Arrays.stream(pathChunks).filter(p -> !p.isEmpty() || p.equals(file.getFile().getName())).toList();

        var prefix = getRoot().getParentFile().getAbsolutePath();

        var lastSelected = false;

        for (int i = 0; i < valuableChunks.size() - 1; i++) {
            var text = valuableChunks.get(i);
            var value = new StringBuilder(valuableChunks.size() * 2);
            value.append(prefix);
            value.append(delimiter);

            var selected = false;

            if (currentMaskChunks.size() > i + depthToParent) {
                selected = text.equals(currentMaskChunks.get(i + depthToParent));
            }

            // TODO fix dir filters re-rendering after file moving
            // selected directory is disablem instead of selected
            if (lastSelected && !selected) {
                for (int j = 0; j < i; j++) {
                    values.get(j).enabled = false;
                }
            }

            if (i == valuableChunks.size() - 2 && selected) {
                for (int j = 0; j < i; j++) {
                    values.get(j).enabled = false;
                }
            }

            lastSelected = selected;

            for (int j = 0; j <= i; j++) {
                String pathChunk = valuableChunks.get(j);
                value.append(pathChunk);

                if (j < i) {
                    value.append(delimiter);
                }
            }

            var newMask = new Mask(value.toString(), text, true, selected);
            values.add(newMask);
        }

        return values;
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

    public void addDirFilter(String mask) {
        // TODO remove this hack
        dirFilter.clear();

        if (dirFilter.add(mask)) {
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

    public void removeDirFilter(String mask) {
        if (dirFilter.remove(mask)) {
            filter();
        }
    }

    public void moveTo(File target) {
        // TODO add unit tests
        var file = getFile();
        var newFile = fsManager.moveTo(file.getFile(), target);
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
