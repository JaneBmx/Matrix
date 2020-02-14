package validator;

import entity.Matrix;

public class MatrixValidation {
    private static final int MIN_MATRIX_SIZE = 1;

    public static boolean isValidMatrix(Matrix matrix) {
        return matrix != null && matrix.getMatrix().length > MIN_MATRIX_SIZE;
    }

    public static boolean isValidElement(Matrix matrix, int index) {
        if (isValidMatrix(matrix)) {
            return index <= matrix.getMatrix().length;
        }
        return false;
    }

    public static boolean isValidMatrixSize(int size) {
        return size > MIN_MATRIX_SIZE;
    }
}
