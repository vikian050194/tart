package tart.core.wrapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWrapper86 extends FileWrapper {

    public FileWrapper86(String p) {
        super(p);
    }

    public FileWrapper86(File f) {
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
        fileName = fileName.substring(0, 15);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(fileName, formatter);

        return dateTime;
    }

    @Override
    public FileWrapper cloneWith(File value) {
        return new FileWrapper86(value);
    }
}
