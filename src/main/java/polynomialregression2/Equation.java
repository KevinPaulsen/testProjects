package polynomialregression2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Equation {

    /**
     * Array holding each coefficient of the equation where the
     * X value is equal to x^idx.
     */
    protected final BigDecimal[] terms;
    protected final int scale = 200;
    protected final int degree;

    public Equation(int degree) {
        this.degree = degree;
        if (degree >= 0) {
            terms = new BigDecimal[degree + 1];
        } else {
            terms = new BigDecimal[0];
        }
    }

    public double evaluateAt(double x) {
        BigDecimal value = new BigDecimal(0);
        final BigDecimal xValue = new BigDecimal(x);
        for (int idx = 0; idx < terms.length; idx++) {
            value = value.add(terms[idx].multiply(xValue.pow(idx)));
        }
        return value.doubleValue();
    }

    public BigDecimal[] getTerms() {
        return terms;
    }

    public int getScale() {
        return scale;
    }
}
