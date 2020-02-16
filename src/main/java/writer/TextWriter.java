package writer;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter implements Writer {
    private static final Logger LOGGER = Logger.getLogger(TextWriter.class);
    private static final String DEFAULT_FILE = "Matrix.txt";

    private TextWriter() {
    }

    private static class Holder {
        private static final Writer INSTANCE = new TextWriter();
    }

    public static Writer getInstance() {
        return Holder.INSTANCE;
    }

    public void writeInFile(String text, String path) {
        path = path == null ? DEFAULT_FILE : path;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(text);
        } catch (IOException e) {
            LOGGER.warn("Info hasn't been wrote.");
        }
    }
}
