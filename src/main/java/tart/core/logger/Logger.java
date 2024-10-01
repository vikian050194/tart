package tart.core.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import javax.swing.filechooser.FileSystemView;

public class Logger {

    private static java.util.logging.Logger logger;

    private static void initLogger() {
        logger = java.util.logging.Logger.getGlobal();

        FileHandler fh;

        var view = FileSystemView.getFileSystemView();
        var file = view.getHomeDirectory();
        var desktopPath = file.getPath();

        try {
            // TODO magic string - file name
            fh = new FileHandler(String.format("%s/%s", desktopPath, "tart.log"), true);
            logger.addHandler(fh);

            logger.addHandler(new ConsoleHandler() {
                {
                    setOutputStream(System.out);
                }
            });

            // TODO add structured log
            var formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // TODO add logging level configurable option
            logger.setLevel(Level.ALL);
        } catch (SecurityException | IOException e) {
            logger.severe(e.toString());
        }
    }

    public static java.util.logging.Logger getLogger() {
        if (logger == null) {
            initLogger();
        }

        return logger;
    }
}
