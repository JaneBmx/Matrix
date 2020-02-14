package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter implements Writer {
    private static final String DEFAULT_FILE = "Matrix.txt";

    private TextWriter() {
    }

    private static class Holder {
        private final static Writer INSTANCE = new TextWriter();
    }

    public static Writer getInstance() {
        return Holder.INSTANCE;
    }

    public void writeInFile(String text, String path) {
        path = path == null ? DEFAULT_FILE : path;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            //log
            e.printStackTrace();
        }
    }

    public void writeInConsole(String message) {
        System.out.println(message);
    }
}
