package polynomialregression;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Equation {

    private ArrayList<Fraction> terms;
    private Fraction result;

    public Equation(ArrayList<Fraction> function, Fraction result) {
        this.terms = function;
        this.result = result;
    }

    public Equation(Equation equation) {
        this.terms = new ArrayList<>(equation.getTerms());
        result = new Fraction(equation.result);
    }

    public Equation() {
        terms = new ArrayList<>();
        result = new Fraction(new BigDecimal(1), new BigDecimal(1));
    }

    public void addTerm(Fraction term) {
        terms.add(term);
    }

    public void addResult(Fraction result) {
        this.result = result;
    }

    public void subtract(Equation equation) {
        if (this.size() != equation.size()) {
            System.out.println("Equations not same size");
            return;
        }
        for (int idx = 0; idx < equation.size(); idx++) {
            getTerm(idx).subtract(equation.getTerm(idx));
        }
        result.subtract(equation.getResult());
    }

    public void divide(Fraction divisor) {
        for (Fraction fraction : terms) {
            fraction.divide(divisor);
        }
        result.divide(divisor);
    }

    public ArrayList<Fraction> getTerms() {
        return terms;
    }

    public Fraction getResult() {
        return result;
    }

    public Fraction getTerm(int index) {
        return terms.get(index);
    }

    public int size() {
        return terms.size();
    }
}
