package main;

import entity.Matrix;
import entity.MatrixThread;
import exception.ServiceException;
import org.apache.log4j.Logger;
import parser.MatrixInfoParser;
import parser.Parser;
import reader.Reader;
import reader.TextReader;
import writer.TextWriter;
import writer.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final Writer WRITER = TextWriter.getInstance();
    private static final Reader READER = TextReader.getInstance();
    private static final Parser PARSER = MatrixInfoParser.getInstance();
    private static int time = 2;
    private static final ArrayList<Integer> permits = new ArrayList<>();

    public static void main(String[] args) {
        int[] params = PARSER.parse(READER.read(null));

        Matrix matrix = Matrix.getInstance();
        matrix.fillMatrix(params[0]);
        int matrixSize = 5;
        initArrayList(matrix.getIntMatrix().length);

        int iterations = params[0] * params[1];

        CountDownLatch cdl = null;
        for (int i = 0; i < iterations; i++) {
            cdl = new CountDownLatch(matrixSize);
            for (int k = 0; k < matrixSize; k++) {
                try {
                    new MatrixThread(cdl, getNumber());
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }

            try {
                cdl.await(time, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warn("Thread has been interrupted", e);
            }
            WRITER.writeInFile("\n" + Matrix.getInstance().toString() + "\n", null);
            matrix.refreshBoolMatrix();
            initArrayList(5);
        }
    }

    private static void initArrayList(int size) {
        for (int i = 0; i < size; i++) {
            permits.add(i);
        }
    }

    private static int getNumber() throws ServiceException {
        if (!permits.isEmpty()) {
            Collections.shuffle(permits);
            int temp = permits.get(0);
            permits.remove(0);
            return temp;
        }
        LOGGER.warn("No permits");
        throw new ServiceException("No permits.");
    }
}
