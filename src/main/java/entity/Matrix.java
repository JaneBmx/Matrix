package entity;

import static validator.MatrixValidator.*;

public class Matrix {
    private static final int DEFAULT_MATRIX_SIZE = 5;
    private int[][] intMatrix;
    private boolean[][] boolMatrix;

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
            intMatrix = new int[size][size];
        } else {
            intMatrix = new int[DEFAULT_MATRIX_SIZE][DEFAULT_MATRIX_SIZE];
        }
        refreshBoolMatrix();
    }

    public void refreshBoolMatrix(){
        boolMatrix = new boolean[intMatrix.length][intMatrix.length];
    }

    public int[][] getIntMatrix() {
        return intMatrix;
    }

    public boolean[][] getBoolMatrix() {
        return boolMatrix;
    }

    public void setIntMatrix(int[][] intMatrix) {
        this.intMatrix = intMatrix;
    }

    public void setBoolMatrix(boolean[][] boolMatrix) {
        this.boolMatrix = boolMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intMatrix.length; i++) {
            for (int k = 0; k < intMatrix[i].length; k++) {
                sb.append(intMatrix[i][k]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
