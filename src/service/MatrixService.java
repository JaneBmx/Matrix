package service;

import entity.Matrix;
import exception.ServiceException;

import java.util.Random;

import static validator.MatrixValidation.*;

public class MatrixService {
    private final static Random RANDOM = new Random();

    private static class Holder {
        private static final MatrixService INSTANCE = new MatrixService();
    }

    public static MatrixService getInstance() {
        return Holder.INSTANCE;
    }

    public void writeElement(Matrix matrix, int index, int element) throws ServiceException {
        if (isValidMatrix(matrix) && isValidElement(matrix, index)) {
            matrix.getMatrix()[index][index] = element;
        } else {
            throw new ServiceException("Invalid data.");
        }
    }

    public void fillRandomElement(Matrix matrix, int index, int element) throws ServiceException {
        if (isValidMatrix(matrix)) {
            int randomIndex = getRandomIndex(index, matrix.getMatrix().length);
            boolean isRow = RANDOM.nextBoolean();

            if (isRow) {
                matrix.getMatrix()[index][randomIndex] = element;
            } else {
                matrix.getMatrix()[randomIndex][index] = element;
            }
        } else {
            throw new ServiceException("Invalid data.");
        }
    }

    public int getSum(Matrix matrix, int diagonalIndex) throws ServiceException {
        if (isValidElement(matrix, diagonalIndex)) {
            int sum = 0;
            for (int i = 0; i < matrix.getMatrix().length; i++) {
                sum += matrix.getMatrix()[i][diagonalIndex];
                sum += matrix.getMatrix()[diagonalIndex][i];
            }
            return sum - matrix.getMatrix()[diagonalIndex][diagonalIndex];
        }
        throw new ServiceException("Illegal size of matrix" + matrix.getMatrix().length + ", and index " + diagonalIndex);
    }

    public int getRandomIndex(int n, int size) {
        int randomNumber = n;
        while (randomNumber == n) {
            //randomNumber = (int) (Math.random() * size - 1);
            randomNumber = RANDOM.nextInt(size);
        }
        return randomNumber;
    }
}
