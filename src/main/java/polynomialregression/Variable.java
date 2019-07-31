package polynomialregression;

public class Variable {

    private String variable;
    private int degree;
    private String compareString;

    public Variable(String variable, int degree) {
        this.variable = variable;
        this.degree = degree;
        compareString = variable + degree;
    }

    public String getCompareString() {
        return compareString;
    }

    @Override
    public boolean equals(Object obj) {
        return (compareString.equals(((Variable) obj).getCompareString()));
    }

    @Override
    public int hashCode() {
        return compareString.hashCode();
    }
}
