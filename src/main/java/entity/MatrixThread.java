package entity;

import exception.ServiceException;
import org.apache.log4j.Logger;
import service.MatrixService;
import writer.TextWriter;
import writer.Writer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixThread extends Thread {
    private static final MatrixService SERVICE = MatrixService.getInstance();
    private static final Matrix MATRIX = Matrix.getInstance();
    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private static final Writer WRITER = TextWriter.getInstance();
    private static final Logger LOGGER = Logger.getLogger(MatrixThread.class);
    private CountDownLatch countDownLatch;
    private int threadId;
    private int diagElem;

    public MatrixThread(CountDownLatch countDownLatch, int diagElem) {
        this.countDownLatch = countDownLatch;
        threadId = COUNTER.getAndIncrement();
        this.diagElem = diagElem;
        start();
    }

    @Override
    public void run() {
        try {
            SERVICE.fillDiagonalElement(MATRIX, diagElem, threadId);
            SERVICE.fillRandomElement(MATRIX, diagElem, threadId);
            int sum = SERVICE.getSum(MATRIX, diagElem);
            WRITER.writeInFile("thread: " + threadId + "; sum: " + sum + ";\n", null);
        } catch (ServiceException e) {
            LOGGER.warn("Thread has been interrupted", e);
        } finally {
            countDownLatch.countDown();
        }
    }
}
