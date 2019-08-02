package polynomialregression;

import java.math.BigDecimal;

public class ParametricEquation {

    private PolynomialRegression x;
    private PolynomialRegression y;

    public ParametricEquation(PolynomialRegression x, PolynomialRegression y) {
        this.x = x;
        this.y = y;
    }

    double getXValueAtT(int t) {
        return x.getYValue(t);
    }

    double getYValueAtT(int t) {
        return y.getYValue(t);
    }
}
