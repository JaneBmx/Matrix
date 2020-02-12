package writer;

import entity.Matrix;
import exception.WriteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MatrixWriter implements Writer {
    private static final String DEFAULT_FILE = "Matrix.txt";

    public static void write(Matrix matrix) throws WriteException {
        int[][] matrixForWrite = matrix.getMatrix();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DEFAULT_FILE), true));
            for (int i = 0; i < matrixForWrite.length; i++) {
                for (int j = 0; j < matrixForWrite.length; j++) {
                    writer.write(String.valueOf(matrixForWrite[i][j]));
                    writer.write(" ");
                }
                writer.write("\r\n");
            }
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            throw new WriteException("sm msg");
            //TODO logger warn
        }
    }
}
