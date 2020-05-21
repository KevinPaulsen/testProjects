package polynomialregression2;

import polynomialregression.Variable;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

public class Regression extends Equation {

    private final ArrayList<Point> points;
    private final HashMap<Variable, BigDecimal> mapSums = new HashMap<>();
    private final Matrix coefficientMatrix;

    public Regression(int degree, ArrayList<Point> points) {
        super(degree);
        this.points = points;
        coefficientMatrix = new Matrix(degree + 1, degree + 2, scale);

        regressPoints();
    }

    private void regressPoints() {
        init();
        coefficientMatrix.reduceToReducedRowEchelonForm();
        for (int row = 0; row < coefficientMatrix.m; row++) {
            terms[row] = coefficientMatrix.getValue(row, coefficientMatrix.n - 1);
        }
    }

    private void init() {
        for (int power = 0; power <= 2 * degree; power++) {
            calculatePowSum(power);
        }

        for (int col = 0; col <= degree + 1; col++) {
            for (int row = 0; row < degree + 1; row++) {
                if (col < degree + 1) {
                    coefficientMatrix.setValue(row, col, mapSums.get(new Variable("x", row + col)));
                } else {
                    coefficientMatrix.setValue(row, col, mapSums.get(new Variable("xy", row)));
                }

            }
        }
    }

    private void calculatePowSum(int power) {
        BigDecimal xSum = new BigDecimal("0").setScale(scale, RoundingMode.HALF_UP);
        BigDecimal ySum = new BigDecimal("0").setScale(scale, RoundingMode.HALF_UP);
        boolean calcYValues = power <= degree;

        for (Point point : points) {
            BigDecimal powX1 = new BigDecimal(Integer.toString(point.x)).pow(power).setScale(scale, RoundingMode.HALF_UP);
            xSum = xSum.add(powX1);
            if (calcYValues) {
                ySum = ySum.add(new BigDecimal(Integer.toString(point.y)).multiply(powX1).setScale(scale, RoundingMode.HALF_UP));
            }
        }
        mapSums.put(new Variable("x", power), xSum);
        if (calcYValues) {
            mapSums.put(new Variable("xy", power), ySum);
        }
    }
}
