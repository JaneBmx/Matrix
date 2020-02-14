package entity;

import static validator.MatrixValidation.*;

public class Matrix {
    private static final int DEFAULT_MATRIX_SIZE = 5;
    private int[][] matrix;

    private Matrix() {
    }

    private static class Holder {
        private static final Matrix INSTANCE = new Matrix();
    }

    public static Matrix getInstance() {
        return Holder.INSTANCE;
    }

    public void fillMatrix(int size) {
        if (isValidMatrixSize(size)) {
            matrix = new int[size][size];
        } else {
            matrix = new int[DEFAULT_MATRIX_SIZE][DEFAULT_MATRIX_SIZE];
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                sb.append(matrix[i][k]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
