package polynomialregression;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Equation {

    /**
     * The array that holds each coefficient ordered from x^0
     * up to x^n.
     */
    protected BigDecimal[] terms;

    Equation(int numTerms) {
        terms = new BigDecimal[numTerms];
    }

    double getYValue(double x) {
        BigDecimal yValue = new BigDecimal("0").setScale(Matrix.scale, RoundingMode.HALF_UP);

        for (int idx = 0; idx < terms.length; idx++) {
            yValue = yValue.add(terms[idx].multiply(new BigDecimal(Double.toString(x)).setScale(Matrix.scale, RoundingMode.HALF_UP).pow(idx)));
        }

        return yValue.doubleValue();
    }
}
