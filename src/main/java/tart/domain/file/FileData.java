package tart.domain.file;

public class FileData {

    private final byte[] data;

    public FileData(byte[] d) {
        data = d;
    }

    public byte[] getData() {
        return data;
    }
}
