package service;

import entity.Matrix;
import exception.ServiceException;
import exception.WriteException;
import writer.MatrixWriter;
import writer.Writer;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class MatrixThreads implements Runnable {
    MatrixWriter writer = new MatrixWriter();
    Matrix matrix;
    Semaphore sem;
    int num;

    public MatrixThreads(Matrix matrix, Semaphore sem) {
        this.matrix = matrix;
        this.sem = sem;
    }

    public void run() {
    }


    private void fillMatrix() throws ServiceException {
        matrix.getMatrix()[num][num] = Integer.parseInt(Thread.currentThread().getName());
        try {
            writer.write(matrix);
        } catch (WriteException e) {
            throw new ServiceException("some msg");
        }
    }
}

class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Semaphore sem = new Semaphore(n);
        Matrix mat = new Matrix(n);

        new Thread(new MatrixThreads(mat,sem));
    }

}
