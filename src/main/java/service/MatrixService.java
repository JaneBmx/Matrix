package service;

import entity.Matrix;
import exception.ServiceException;

import java.util.Random;

import static validator.MatrixValidation.*;

public class MatrixService {
    private static final Random RANDOM = new Random();

    private static class Holder {
        private static final MatrixService INSTANCE = new MatrixService();
    }

    public static MatrixService getInstance() {
        return Holder.INSTANCE;
    }

    public void fillDiagonalElement(Matrix matrix, int index, int element) throws ServiceException {
        if (isValidMatrix(matrix) && isValidElement(matrix, index)) {
            matrix.getIntMatrix()[index][index] = element;
        } else {
            throw new ServiceException("Invalid data.");
        }
    }

    public void fillRandomElement(Matrix matrix, int index, int element) throws ServiceException {
        if (isValidMatrix(matrix)) {
            int randomIndex;
            boolean checkingIndex;

            int index1;
            int index2;

            do {
                randomIndex = randomizeIndex(index, matrix.getIntMatrix().length);

                if (RANDOM.nextBoolean()) {
                    checkingIndex = matrix.getBoolMatrix()[randomIndex][index];
                    index1 = randomIndex;
                    index2 = index;
                } else {
                    checkingIndex = matrix.getBoolMatrix()[index][randomIndex];
                    index1 = index;
                    index2 = randomIndex;
                }
            } while (checkingIndex);
            matrix.getBoolMatrix()[index1][index2] = true;
            matrix.getIntMatrix()[index1][index2] = element;
        } else {
            throw new ServiceException("Invalid data.");
        }
    }

    public int getSum(Matrix matrix, int diagonalIndex) throws ServiceException {
        if (isValidElement(matrix, diagonalIndex)) {
            int sum = 0;
            for (int i = 0; i < matrix.getIntMatrix().length; i++) {
                sum += matrix.getIntMatrix()[i][diagonalIndex];
                sum += matrix.getIntMatrix()[diagonalIndex][i];
            }
            return sum - matrix.getIntMatrix()[diagonalIndex][diagonalIndex];
        }
        throw new ServiceException("Illegal size of matrix" + matrix.getIntMatrix().length + ", and index " + diagonalIndex);
    }

    public int randomizeIndex(int n, int size) {
        int randomNumber = n;
        while (randomNumber == n) {
            randomNumber = RANDOM.nextInt(size);
        }
        return randomNumber;
    }
}
