package tart.data.image;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.data.file.LocalFileRepository;
import tart.domain.file.DirectoryDescription;
import tart.domain.file.FileRepository;

public class LocalFileRepositoryTests {

    @Test
    public void getDirectoriesAtHome() {
        // Arrange
        FileRepository fr = new LocalFileRepository();
        var expectedSize = 37;

        // Act
        var actualSize = fr.getDirectories().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getDirectoriesAtTarget() {
        // Arrange
        var target = new DirectoryDescription(List.of("home", "kirill", "git"));
        FileRepository fr = new LocalFileRepository();
        var expectedSize = 37;

        // Act
        var actualSize = fr.getDirectories(target).size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }
}
