package polynomialregression;

import java.util.*;

public class Matrix {

    private Fraction[][] matrix;
    private int numColumns;
    private int numRows;
    private static Map<Matrix, Fraction> determinantMap = new HashMap<>();

    public Matrix(ArrayList<ArrayList<Fraction>> twoDemArray) {
        numRows = twoDemArray.size();
        numColumns = twoDemArray.get(0).size();
        matrix = new Fraction[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            Fraction[] rowList = new Fraction[numColumns];
            for (int col = 0; col < numColumns; col++) {
                rowList[col] = new Fraction(twoDemArray.get(row).get(col));
            }
            matrix[row] = rowList;
        }
    }

    private Matrix(Matrix matrix) {
        this.numColumns = matrix.getNumColumns();
        this.numRows = matrix.getNumRows();
        this.matrix = new Fraction[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            Fraction[] rowList = new Fraction[numColumns];
            for (int col = 0; col < numColumns; col++) {
                rowList[col] = new Fraction(matrix.getMatrix()[row][col]);
            }
            this.matrix[row] = rowList;
        }
    }

    private Matrix(int size) {
        numColumns = size;
        numRows = size;
        matrix = new Fraction[size][size];
    }

    public Matrix(Fraction[][] matrix) {
        this.matrix = matrix;
        numColumns = matrix.length;
        numRows = matrix[0].length;
    }

    public void printMatrix() {
        for (Fraction[] row : matrix) {
            for (Fraction value : row) {
                System.out.printf("%14.5f ", value.approximate(5));
            }
            System.out.println("\n");
        }
    }

    public Matrix getMultiply(Matrix matrix) {
        if (this.numColumns != matrix.getNumRows()) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        Fraction[][] newMatrix = new Fraction[matrix.getNumRows()][matrix.getNumColumns()];

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

    private void makeColumnZero(int column, Fraction[][] matrix, Fraction[][] augmentedMatrix) {

        for (int row = 0; row < matrix.length; row++) {
            if (row == column) {
                continue;
            }
            Fraction scalar = matrix[row][column];
            Fraction[] scaledMatrixRowValues = multiplyEach(getCopyRow(matrix[column]), scalar);
            Fraction[] scaledAugmentedMatrixRowValues = multiplyEach(getCopyRow(augmentedMatrix[column]), scalar);
            subtract(matrix[row], scaledMatrixRowValues);
            subtract(augmentedMatrix[row], scaledAugmentedMatrixRowValues);
        }
    }

    private static Matrix getIdentityMatrix(int size) {
        Fraction[][] matrixValues = new Fraction[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (row == column) {
                    matrixValues[row][column] = new Fraction("1", "1");
                } else {
                    matrixValues[row][column] = new Fraction("0", "1");
                }
            }
        }
        return new Matrix(matrixValues);
    }

    private void subtract(Fraction[] matrixRow, Fraction[] subtractMatrix) {
        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRow[idx] = matrixRow[idx].getSubtract(subtractMatrix[idx]);
        }
    }

    private void makeLeadingCoefficientOne(Fraction[][] matrixRow, Fraction[][] augmentedMatrixRow, int row) {
        Fraction leadingReciprocal = matrixRow[row][row].getReciprocal();

        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRow[row][idx] = matrixRow[row][idx].getMultiply(leadingReciprocal);
            augmentedMatrixRow[row][idx] = augmentedMatrixRow[row][idx].getMultiply(leadingReciprocal);
        }
    }

    private Fraction[] getCopyRow(Fraction[] matrixRow) {
        Fraction[] matrixRowCopy = new Fraction[matrixRow.length];
        for (int idx = 0; idx < matrixRow.length; idx++) {
            matrixRowCopy[idx] = new Fraction(matrixRow[idx]);
        }
        return matrixRowCopy;
    }

    private Fraction[] multiplyEach(Fraction[] matrixRow, Fraction scalar) {
        for (Fraction value : matrixRow) {
            value.multiply(scalar);
        }
        return matrixRow;
    }

    Fraction getDeterminant(Matrix matrix) {
        if (matrix.getNumRows() != matrix.getNumColumns()) {
            throw new RuntimeException("Matrix is not square.");
        } else if (matrix.getNumRows() < 2) {
            throw new RuntimeException("Matrix of size" + matrix.getNumRows() + " is too small");
        }

        Fraction determinant;
        if (matrix.getNumRows() == 2) {
            Fraction aTimesD = matrix.getValue(0, 0).getMultiply(matrix.getValue(1, 1));
            Fraction bTimesC = matrix.getValue(1, 0).getMultiply(matrix.getValue(0, 1));
            determinant = aTimesD.getSubtract(bTimesC);
            determinantMap.put(matrix, determinant);
        } else {
            determinant = new Fraction("0", "1");
            for (int column = 0; column < matrix.getNumRows(); column++) {

                Matrix subMatrix = getSubMatrix(matrix, 0, column);
                Fraction subDeterminant = determinantMap.get(subMatrix);
                if (subDeterminant == null) {
                    subDeterminant = Objects.requireNonNull(getDeterminant(subMatrix));
                }
                determinant.add(matrix.getValue(0, column)
                        .getMultiply(subDeterminant)
                                .getMultiply(new Fraction("" + Math.pow(-1, column), "" + 1)));
                determinantMap.put(subMatrix, subDeterminant);
            }
        }
        return determinant;
    }

    private static Matrix getSubMatrix(Matrix matrix, int row, int column) {
        Fraction[][] subMatrix = new Fraction[matrix.getNumRows() - 1][matrix.getNumRows() - 1];
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
        return adjugate(matrixOfCofactors).getMultiply(getDeterminant(this).getReciprocal());
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
                matrix.setValue(matrix.getValue(row, column)
                        .getMultiply(new Fraction("" + Math.pow(-1, row + column), "" + 1)),
                        row, column);
            }
        }
        return matrix;
    }

    private Matrix adjugate(Matrix matrix) {
        Fraction[][] matrixCopy = new Fraction[getNumRows()][getNumColumns()];

        for (int row = 0; row < matrix.getNumRows(); row++) {
            for (int column = 0; column < matrix.getNumRows(); column++) {
                matrixCopy[row][column] = matrix.getValue(column, row);
            }
        }

        return new Matrix(matrixCopy);
    }

    private Fraction[] getRow(int row) {
        return matrix[row];
    }

    private void setRow(int row, Fraction[] matrixRow) {
        matrix[row] = matrixRow;
    }

    private void setValue(Fraction value, int row, int column) {
        matrix[row][column] = value;
    }

    private Matrix getMultiply(Fraction scalar) {
        Fraction[][] multipliedMatrix = new Fraction[getNumRows()][getNumColumns()];
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumColumns(); column++) {
                multipliedMatrix[row][column] = getValue(row, column).getMultiply(scalar);
            }
        }
        return new Matrix(multipliedMatrix);
    }

    private static Vector getVirticleVector(int columnNum, Matrix matrix) {
        Fraction[] dimensions = new Fraction[matrix.getNumRows()];
        for (int row = 0; row < matrix.getNumRows(); row++) {
            dimensions[row] = matrix.getValue(row, columnNum);
        }
        return new Vector(dimensions);
    }

    private static Vector getHorizontalVector(int rowNum, Matrix matrix) {
        return new Vector(matrix.getMatrix()[rowNum]);
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

    public Fraction[][] getMatrix() {
        return matrix;
    }

    public Fraction getValue(int row, int column) {
        return matrix[row][column];
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumRows() {
        return numRows;
    }
}
