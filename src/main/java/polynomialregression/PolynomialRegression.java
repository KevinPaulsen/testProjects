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
    private ArrayList<Equation> equations;
    private ArrayList<Fraction> coefficients;
    private Matrix valueMatrix;
    private Matrix resultMatrix;
    private Matrix coefficientMatrix;

    public PolynomialRegression(ArrayList<Point> dataPoints, int degree) {
        this.dataPoints = dataPoints;
        this.degree = degree;
        mapSums = new HashMap<>();
    }

    public void
    regressPoints() {
        init();
        /*
        System.out.println("Value Matrix:");
        valueMatrix.printMatrix();

        System.out.println("\n\nInverse Matrix:");
        Matrix matrix = valueMatrix.getInverse();
        matrix.printMatrix();

        System.out.println("\n\nMultiply Matrix:");
        valueMatrix.getMultiply(matrix).printMatrix();//*/

        valueMatrix.printMatrix();
        System.out.println();
        resultMatrix.printMatrix();

        System.out.println("\n-----------------------------------------------\n");

        valueMatrix.getInverse2().printMatrix();

        System.out.println("\n-----------------------------------------------\n");

        (valueMatrix.getInverse2().getMultiply(resultMatrix)).printMatrix();
        //resultMatrix.getInverse2();

        System.out.println("\n-----------------------------------------------\n");

        //coefficientMatrix = valueMatrix.getInverse2().getMultiply(resultMatrix);

        //coefficientMatrix.printMatrix();
    }

    public void printString() {
        for (Equation equation : equations) {
            for (Fraction fraction : equation.getTerms()) {
                System.out.printf("%8.2f ", fraction.approximate(6).doubleValue());
            }
            System.out.printf(" = %8.2f", equation.getResult().approximate(6).doubleValue());
            System.out.println("\n");
        }
        System.out.println();
        for (Fraction coefficient : coefficients) {
            System.out.printf("%8.2f ", coefficient.approximate(2).doubleValue());
        }
        System.out.println();
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

    private static ArrayList<Equation> formatEquations(ArrayList<Equation> equations) {
        ArrayList<Equation> finalEquations = new ArrayList<>();

        int column = equations.get(0).size() - equations.size();

        for (Equation equation : equations) {
            equation.divide(new Fraction(equation.getTerm(column)));
        }

        Equation firstCopy = new Equation(new Equation(equations.get(0)));
        for (int idx = 1; idx < equations.size(); idx++) {
            equations.get(idx).subtract(firstCopy);
        }
        finalEquations.add(equations.remove(0));

        if (equations.size() > 0) {
            finalEquations.addAll(formatEquations(equations));
        }

        return finalEquations;
    }

    public static ArrayList<Fraction> calculateCoefficients(ArrayList<Equation> equations) {
        ArrayList<Fraction> coefficients = new ArrayList<>();
        //coefficients.add(equations.get(equations.size() - 1).getTerm(equations.size() - 1));

        for (int row = equations.size() - 1; row >= 0; row--) {
            coefficients.add(0, isolateCoefficient(equations.get(row), coefficients));
        }

        return coefficients;
    }

    private static Fraction isolateCoefficient(Equation equation, ArrayList<Fraction> coefficients) {
        Fraction coefficient = new Fraction(equation.getResult());

        int numSolvedTerms = equation.size() - coefficients.size();
        for (int variable = numSolvedTerms; variable < equation.size(); variable++) {
            coefficient.subtract(equation.getTerm(variable).getMultiply(coefficients.get(variable - numSolvedTerms)));
        }

        return coefficient;
    }
}
