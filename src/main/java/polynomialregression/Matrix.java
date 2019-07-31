package polynomialregression;

import java.math.BigDecimal;
import java.util.*;

public class Matrix {

    private BigDecimal[][] matrix;
    private int numColumns;
    private int numRows;
    private static Map<Matrix, BigDecimal> determinantMap = new HashMap<>();
    private static int scale = 100;

    public Matrix(ArrayList<ArrayList<BigDecimal>> twoDemArray) {
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

    private Matrix(int size) {
        numColumns = size;
        numRows = size;
        matrix = new BigDecimal[size][size];
    }

    public Matrix(BigDecimal[][] matrix) {
        this.matrix = matrix;
        numColumns = matrix.length;
        numRows = matrix[0].length;
    }

    public void printMatrix() {
        for (BigDecimal[] row : matrix) {
            for (BigDecimal value : row) {
                System.out.printf("%18.5f ", value.doubleValue());
            }
            System.out.println("\n");
        }
    }

    public Matrix getMultiply(Matrix matrix) {
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

    public Matrix getInverse2() {
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

    BigDecimal getDeterminant(Matrix matrix) {
        if (matrix.getNumRows() != matrix.getNumColumns()) {
            throw new RuntimeException("Matrix is not square.");
        } else if (matrix.getNumRows() < 2) {
            throw new RuntimeException("Matrix of size" + matrix.getNumRows() + " is too small");
        }

        BigDecimal determinant;
        if (matrix.getNumRows() == 2) {
            BigDecimal aTimesD = matrix.getValue(0, 0).multiply(matrix.getValue(1, 1));
            BigDecimal bTimesC = matrix.getValue(1, 0).multiply(matrix.getValue(0, 1));
            determinant = aTimesD.subtract(bTimesC);
            determinantMap.put(matrix, determinant);
        } else {
            determinant = new BigDecimal("0");
            for (int column = 0; column < matrix.getNumRows(); column++) {

                Matrix subMatrix = getSubMatrix(matrix, 0, column);
                BigDecimal subDeterminant = determinantMap.get(subMatrix);
                if (subDeterminant == null) {
                    subDeterminant = Objects.requireNonNull(getDeterminant(subMatrix));
                }
                determinant.add(matrix.getValue(0, column)
                        .multiply(subDeterminant)
                                .multiply(new BigDecimal("" + Math.pow(-1, column))));
                determinantMap.put(subMatrix, subDeterminant);
            }
        }
        return determinant;
    }

    private static Matrix getSubMatrix(Matrix matrix, int row, int column) {
        BigDecimal[][] subMatrix = new BigDecimal[matrix.getNumRows() - 1][matrix.getNumRows() - 1];
        int rowOffset = 0;
        for (int r = 0; r < matrix.getNumRows(); r++) {
            if (r == row) {
                rowOffset = 1;
                continue;
            }
            int columnOffset = 0;
            for (int c = 0; c < matrix.getNumRows(); c++) {
                if (c == column) {
                    columnOffset = 1;
                    continue;
                }
                subMatrix[r - rowOffset][c - columnOffset] = matrix.getValue(r, c);
            }
        }
        return new Matrix(subMatrix);
    }

    public Matrix getInverse() {
        Matrix matrixOfMinors = calcMatrixOfMinors();
        Matrix matrixOfCofactors = calcMatrixOfCofactors(matrixOfMinors);
        return adjugate(matrixOfCofactors).getMultiply(new BigDecimal("1").divide(getDeterminant(this),
                scale,
                BigDecimal.ROUND_HALF_UP));
    }

    private Matrix calcMatrixOfMinors() {
        Matrix matrixOfMinors = new Matrix(numColumns);
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                matrixOfMinors.setValue(getDeterminant(getSubMatrix(this, row, column)), row, column);
            }
        }
        return matrixOfMinors;
    }

    private Matrix calcMatrixOfCofactors(Matrix matrix) {
        for (int row = 0; row < matrix.getNumRows(); row++) {
            for (int column = 0; column < matrix.getNumRows(); column++) {
                matrix.setValue(matrix.getValue(row, column).multiply(
                        new BigDecimal("" + Math.pow(-1, row + column))),
                        row,
                        column);
            }
        }
        return matrix;
    }

    private Matrix adjugate(Matrix matrix) {
        BigDecimal[][] matrixCopy = new BigDecimal[getNumRows()][getNumColumns()];

        for (int row = 0; row < matrix.getNumRows(); row++) {
            for (int column = 0; column < matrix.getNumRows(); column++) {
                matrixCopy[row][column] = matrix.getValue(column, row);
            }
        }

        return new Matrix(matrixCopy);
    }

    private BigDecimal[] getRow(int row) {
        return matrix[row];
    }

    private void setRow(int row, BigDecimal[] matrixRow) {
        matrix[row] = matrixRow;
    }

    private void setValue(BigDecimal value, int row, int column) {
        matrix[row][column] = value;
    }

    private Matrix getMultiply(BigDecimal scalar) {
        BigDecimal[][] multipliedMatrix = new BigDecimal[getNumRows()][getNumColumns()];
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumColumns(); column++) {
                multipliedMatrix[row][column] = getValue(row, column).multiply(scalar);
            }
        }
        return new Matrix(multipliedMatrix);
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

    public BigDecimal[][] getMatrix() {
        return matrix;
    }

    public BigDecimal getValue(int row, int column) {
        return matrix[row][column];
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumRows() {
        return numRows;
    }
}
