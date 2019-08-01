package polynomialregression;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolynomialRegression {

    private int degree;
    private List<Point> dataPoints;
    private Map<Variable, BigDecimal> mapSums;
    private Matrix valueMatrix;
    private Matrix resultMatrix;
    private Matrix coefficientMatrix;

    public PolynomialRegression(ArrayList<Point> dataPoints, int degree) {
        if (dataPoints.size() < degree) {
            throw new RuntimeException("Not enough points to have a polynomial of degree: " + dataPoints.size());
        }
        this.dataPoints = dataPoints;
        this.degree = degree;
        mapSums = new HashMap<>();
    }

    public void
    regressPoints() {
        init();

        coefficientMatrix = valueMatrix.getInverse2().getMultiply(resultMatrix);
    }

    private void init() {
        // Calculate x/xy sums
        for (int power = 0; power <= 2 * degree; power++) {
            calculatePowSum(power);
        }

        initValues();
    }

    private void initValues() {
        ArrayList<ArrayList<BigDecimal>> valueMatrix = new ArrayList<>();
        ArrayList<ArrayList<BigDecimal>> resultMatrix = new ArrayList<>();
        for (int row = 0; row <= degree; row++) {
            ArrayList<BigDecimal> rowValues = new ArrayList<>();
            for (int column = 0; column <= degree; column++) {
                rowValues.add(new BigDecimal(mapSums.get(new Variable("x", row + column)).toString()));
            }
            ArrayList<BigDecimal> result = new ArrayList<>();
            result.add(new BigDecimal(mapSums.get(new Variable("xy", row)).toString()));
            resultMatrix.add(result);
            valueMatrix.add(rowValues);
        }
        this.valueMatrix = new Matrix(valueMatrix);
        this.resultMatrix = new Matrix(resultMatrix);
    }

    private void calculatePowSum(int power) {
        BigDecimal xSum = new BigDecimal(0);
        BigDecimal ySum = new BigDecimal(0);
        boolean calcYValues = power <= degree;

        for (Point point : dataPoints) {
            BigDecimal powX1 = new BigDecimal(point.x).pow(power);
            xSum = xSum.add(powX1);
            if (calcYValues) {
                ySum = ySum.add(new BigDecimal(point.y).multiply(powX1));
            }
        }
        mapSums.put(new Variable("x", power), xSum);
        if (calcYValues) {
            mapSums.put(new Variable("xy", power), ySum);
        }
    }
}
