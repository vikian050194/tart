package tart.core.wrapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWrapper42 extends FileWrapper {

    public FileWrapper42(String p) {
        super(p);
    }

    public FileWrapper42(File f) {
        super(f);
    }

    @Override
    public String getDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getTime() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LocalDateTime getTimestamp() {
        var fileName = file.getName().toLowerCase();
        fileName = fileName.substring(0, 19);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime dateTime = LocalDateTime.parse(fileName, formatter);

        return dateTime;
    }

    @Override
    public FileWrapper cloneWith(File value) {
        return new FileWrapper42(value);
    }
}
