package polynomialregression2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matrix {

    private final BigDecimal[][] matrix;
    private final int scale;
    public final int m;
    public final int n;

    public Matrix(int m, int n, int scale) {
        this.scale = scale;
        this.m = m;
        this.n = n;
        matrix = new BigDecimal[m][n];
    }

    public void setValue(int m, int n, BigDecimal value) {
        matrix[m][n] = value;
    }

    public BigDecimal getValue(int m, int n) {
        return matrix[m][n];
    }

    public void reduceToReducedRowEchelonForm() {
        final BigDecimal one = new BigDecimal(1).setScale(scale, RoundingMode.HALF_UP);
        final BigDecimal negativeOne = new BigDecimal(-1).setScale(scale, RoundingMode.HALF_UP);
        for (int row = 0; row < m; row++) {
            // Make Leading 1 at MxM
            divideRow(row, matrix[row][row]);
            // Add row*leading*-1 to each row
            for (int subRow = 0; subRow < m; subRow++) {
                if (subRow == row) {
                    continue;
                }
                addScaledRow(subRow, row, negativeOne.multiply(matrix[subRow][row]));
            }
        }
    }

    private void addScaledRow(int targetRow, int addedRow, BigDecimal scale) {
        if (0 <= targetRow && targetRow < m && 0 <= addedRow && addedRow < m) {
            for (int col = 0; col < n; col++) {
                matrix[targetRow][col] = matrix[targetRow][col].add(matrix[addedRow][col].multiply(scale));
            }
        }
    }

    private void divideRow(int targetRow, BigDecimal value) {
        for (int col = 0; col < n; col++) {
            matrix[targetRow][col] = matrix[targetRow][col].divide(value, RoundingMode.HALF_UP);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringMatrix = new StringBuilder();
        for (BigDecimal[] row : matrix) {
            for (BigDecimal value : row) {
                stringMatrix.append(value.doubleValue()).append(" ");
            }
            stringMatrix.append("\n");
        }
        return stringMatrix.toString();
    }
}
