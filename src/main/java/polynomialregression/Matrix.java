package polynomialregression;

import java.math.BigDecimal;
import java.util.*;

public class Matrix {

    private BigDecimal[][] matrix;
    private int numColumns;
    private int numRows;
    private static int scale = 100;

    Matrix(ArrayList<ArrayList<BigDecimal>> twoDemArray) {
        numRows = twoDemArray.size();
        numColumns = twoDemArray.get(0).size();
        matrix = new BigDecimal[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            BigDecimal[] rowList = new BigDecimal[numColumns];
            for (int col = 0; col < numColumns; col++) {
                rowList[col] = decimalCopy(twoDemArray.get(row).get(col));
            }
            matrix[row] = rowList;
        }
    }

    private Matrix(Matrix matrix) {
        this.numColumns = matrix.getNumColumns();
        this.numRows = matrix.getNumRows();
        this.matrix = new BigDecimal[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            BigDecimal[] rowList = new BigDecimal[numColumns];
            for (int col = 0; col < numColumns; col++) {
                rowList[col] = decimalCopy(matrix.getMatrix()[row][col]);
            }
            this.matrix[row] = rowList;
        }
    }

    private Matrix(BigDecimal[][] matrix) {
        this.matrix = matrix;
        numColumns = matrix.length;
        numRows = matrix[0].length;
    }

    void printMatrix() {
        for (BigDecimal[] row : matrix) {
            for (BigDecimal value : row) {
                System.out.printf("%18.5f ", value.doubleValue());
            }
            System.out.println("\n");
        }
    }

    Matrix getMultiply(Matrix matrix) {
        if (this.numColumns != matrix.getNumRows()) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        BigDecimal[][] newMatrix = new BigDecimal[matrix.getNumRows()][matrix.getNumColumns()];

        for (int row = 0; row < numRows; row++) {
            Vector rowVector = Matrix.getHorizontalVector(row, this);
            for (int column = 0; column < matrix.getNumColumns(); column++) {
                Vector columnVector = Matrix.getVirticleVector(column, matrix);
                newMatrix[row][column] = rowVector.getDotProduct(columnVector);
            }
        }

        return new Matrix(newMatrix);
    }

    Matrix getInverse2() {
        if (numRows != numColumns) {
            throw new RuntimeException("Matrix is not square.");
        }

        Matrix augmentedMatrix = getIdentityMatrix(numRows);
        Matrix matrixCopy = new Matrix(this);

        for (int row = 0; row < numRows; row++) {
            makeLeadingCoefficientOne(matrixCopy.getMatrix(), augmentedMatrix.getMatrix(), row);
            makeColumnZero(row, matrixCopy.getMatrix(), augmentedMatrix.getMatrix());
        }

        return augmentedMatrix;
    }

    private void makeColumnZero(int column, BigDecimal[][] matrix, BigDecimal[][] augmentedMatrix) {

        for (int row = 0; row < matrix.length; row++) {
            if (row == column) {
                continue;
            }
            BigDecimal scalar = matrix[row][column];
            BigDecimal[] scaledMatrixRowValues = multiplyEach(getCopyRow(matrix[column]), scalar);
            BigDecimal[] scaledAugmentedMatrixRowValues = multiplyEach(getCopyRow(augmentedMatrix[column]), scalar);
            subtract(matrix[row], scaledMatrixRowValues);
            subtract(augmentedMatrix[row], scaledAugmentedMatrixRowValues);
        }
    }

    private static Matrix getIdentityMatrix(int size) {
        BigDecimal[][] matrixValues = new BigDecimal[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (row == column) {
                    matrixValues[row][column] = new BigDecimal("1");
                } else {
                    matrixValues[row][column] = new BigDecimal("0");
                }
            }
        }
        return new Matrix(matrixValues);
    }

    private void subtract(BigDecimal[] matrixRow, BigDecimal[] subtractMatrix) {
        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRow[idx] = matrixRow[idx].subtract(subtractMatrix[idx]);
        }
    }

    private void makeLeadingCoefficientOne(BigDecimal[][] matrixRow, BigDecimal[][] augmentedMatrixRow, int row) {
        BigDecimal leadingReciprocal = matrixRow[row][row];

        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRow[row][idx] = matrixRow[row][idx].divide(leadingReciprocal, scale, BigDecimal.ROUND_HALF_UP);
            augmentedMatrixRow[row][idx] = augmentedMatrixRow[row][idx].divide(leadingReciprocal, scale, BigDecimal.ROUND_HALF_UP);
        }
    }

    private BigDecimal[] getCopyRow(BigDecimal[] matrixRow) {
        BigDecimal[] matrixRowCopy = new BigDecimal[matrixRow.length];
        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRowCopy[idx] = decimalCopy(matrixRow[idx]);
        }
        return matrixRowCopy;
    }

    private BigDecimal[] multiplyEach(BigDecimal[] matrixRow, BigDecimal scalar) {
        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRow[idx] = matrixRow[idx].multiply(scalar);
        }
        return matrixRow;
    }

    private static Vector getVirticleVector(int columnNum, Matrix matrix) {
        BigDecimal[] dimensions = new BigDecimal[matrix.getNumRows()];
        for (int row = 0; row < matrix.getNumRows(); row++) {
            dimensions[row] = matrix.getValue(row, columnNum);
        }
        return new Vector(dimensions);
    }

    private static Vector getHorizontalVector(int rowNum, Matrix matrix) {
        return new Vector(matrix.getMatrix()[rowNum]);
    }

    private static BigDecimal decimalCopy(BigDecimal original) {
        return original.multiply(new BigDecimal("1"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return Arrays.deepEquals(matrix, matrix1.getMatrix());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    private BigDecimal[][] getMatrix() {
        return matrix;
    }

    private BigDecimal getValue(int row, int column) {
        return matrix[row][column];
    }

    private int getNumColumns() {
        return numColumns;
    }

    private int getNumRows() {
        return numRows;
    }
}
