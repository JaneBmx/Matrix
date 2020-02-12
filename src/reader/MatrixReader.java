package reader;

import exception.ReadException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MatrixReader implements Reader {
    private static class Holder {
        private static final reader.Reader instance = new MatrixReader();
    }

    public static Reader getInstance(){
        return Holder.instance;
    }

    @Override
    public String read(String path) throws ReadException {
        StringBuffer text = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        }catch (FileNotFoundException e){
            throw new ReadException("File not found.");
            //TODO logger warn
        }catch (IOException e){
            throw new ReadException("some msg");
            //TODO Logger warn
        }
        return text.toString().trim();
    }
}
