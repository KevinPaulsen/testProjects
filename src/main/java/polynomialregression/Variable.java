package polynomialregression;

public class Variable {

    private String compareString;

    public Variable(String variable, int degree) {
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
