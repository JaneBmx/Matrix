package entity;

import exception.ServiceException;
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
    private CountDownLatch countDownLatch;
    private int threadId;
    private int diagElem;

    public MatrixThread(CountDownLatch countDownLatch, int diagElem) {
        this.countDownLatch = countDownLatch;
        threadId = COUNTER.getAndIncrement();
        this.diagElem = diagElem;
        start();
    }

    public void run() {
        try {
            SERVICE.writeElement(MATRIX, diagElem, threadId);
            SERVICE.fillRandomElement(MATRIX, diagElem, threadId);
            int sum = SERVICE.getSum(MATRIX, diagElem);
            String res = "thread: " + threadId + "; sum: " + sum + ";\n";
            WRITER.writeInFile(res, null);
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
