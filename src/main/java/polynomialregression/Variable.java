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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return compareString.equals(variable.getCompareString());
    }

    @Override
    public int hashCode() {
        return compareString.hashCode();
    }
}
