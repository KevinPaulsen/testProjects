package polynomialregression;

import java.math.BigDecimal;

public class ParametricEquation {

    private Equation x;
    private Equation y;

    public ParametricEquation(Equation x, Equation y) {
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
