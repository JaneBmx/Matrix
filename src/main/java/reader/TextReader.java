package reader;

import exception.ReadException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextReader implements Reader {
    private static final String DEFAULT_PATH = "src/main/resources/Source.txt";

    private static class Holder {
        private static final reader.Reader instance = new TextReader();
    }

    public static Reader getInstance() {
        return Holder.instance;
    }

    @Override
    public String read(String path) {
        path = path==null?DEFAULT_PATH:path;
        StringBuffer text = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("some msg");
        }
        return text.toString().trim();
    }
}
