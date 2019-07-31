package polynomialregression;

import java.math.BigDecimal;
import java.util.Objects;

public class Fraction {

    private static final BigDecimal zeroDecimal = new BigDecimal("0");
    private static final BigDecimal oneDecimal = new BigDecimal("1");

    private BigDecimal numerator;
    private BigDecimal denominator;

    private static final int NUMBER_OF_GCD_CHECK = 10;

    public Fraction(String numerator, String denominator) {
        this.numerator = new BigDecimal(numerator);
        this.denominator = new BigDecimal(denominator);
    }

    public Fraction(BigDecimal numerator, BigDecimal denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(Fraction fraction) {
        numerator = fraction.getNumerator().multiply(new BigDecimal("1"));
        denominator = fraction.getDenominator().multiply(new BigDecimal("1"));
    }

    public void scale(BigDecimal decimal) {
        numerator = numerator.multiply(decimal);
        denominator = denominator.multiply(decimal);
    }

    public void multiply(BigDecimal decimal) {
        numerator = numerator.multiply(decimal);
        checkForOneOrZero();
        reduceFraction();
    }

    public void multiply(Fraction fraction) {
        numerator = numerator.multiply(fraction.getNumerator());
        denominator = denominator.multiply(fraction.getDenominator());
        checkForOneOrZero();
        reduceFraction();
    }

    public Fraction getMultiply(Fraction fraction) {
        Fraction product = new Fraction(numerator.multiply(fraction.getNumerator()).multiply(new BigDecimal("1")),
                denominator.multiply(fraction.getDenominator()).multiply(new BigDecimal("1")));
        product.checkForOneOrZero();
        return product;
    }

    public void divide(Fraction fraction) {
        numerator = numerator.multiply(fraction.getDenominator());
        denominator = denominator.multiply(fraction.getNumerator());
        checkForOneOrZero();
        reduceFraction();
    }

    public Fraction getDivide(Fraction fraction) {
        return new Fraction(fraction.getDenominator().multiply(new BigDecimal("1")),
                fraction.getNumerator().multiply(new BigDecimal("1")));
    }

    public void add(Fraction fraction) {
        if (denominator.hashCode() == fraction.getDenominator().hashCode()) {
            numerator = numerator.add(fraction.getNumerator());
        } else {
            add(makeCommonDenominators(fraction));
            return;
        }
        checkForOneOrZero();
        reduceFraction();
    }

    private void checkForOneOrZero() {
        if (numerator.hashCode() == zeroDecimal.hashCode()) {
            denominator = new BigDecimal("1");
        } else if (numerator.equals(denominator)) {
            numerator = new BigDecimal("1");
            denominator = new BigDecimal("1");
        }
    }

    public void subtract(Fraction fraction) {
        if (fraction.getNumerator().hashCode() == zeroDecimal.hashCode()) {
            return;
        }
        Fraction fractionCopy = new Fraction(fraction);
        fractionCopy.multiply(new BigDecimal("-1"));
        add(fractionCopy);
    }

    public Fraction getSubtract(Fraction fraction) {
        subtract(fraction);
        return this;
    }

    private Fraction makeCommonDenominators(Fraction fraction1, Fraction fraction2) {
        Fraction fraction1Copy = new Fraction(fraction1);
        Fraction fraction2Copy = new Fraction(fraction2);
        fraction1Copy.scale(fraction2Copy.getDenominator());

        return new Fraction(fraction1Copy);
    }

    private Fraction makeCommonDenominators(Fraction fraction) {
        BigDecimal tempDenominator = denominator.multiply(oneDecimal);
        scale(fraction.getDenominator());
        fraction.scale(tempDenominator);
        return fraction;
    }

    private void reduceFraction() {
        for (int count = 2; count < NUMBER_OF_GCD_CHECK; count++) {
            if ((numerator.remainder(new BigDecimal(count)).hashCode() == zeroDecimal.hashCode()) &&
                    denominator.remainder(new BigDecimal(count)).hashCode() == zeroDecimal.hashCode()) {
                numerator = numerator.divide(new BigDecimal(count), 0, BigDecimal.ROUND_HALF_UP);
                denominator = denominator.divide(new BigDecimal(count), 0, BigDecimal.ROUND_HALF_UP);
            }
        }//*/
    }

    public Fraction getReciprocal() {
        if (numerator.hashCode() == zeroDecimal.hashCode()) {
            return new Fraction("0", "1");
        }
        return new Fraction(denominator, numerator);
    }

    public BigDecimal approximate(int scale) {
        if (numerator.hashCode() == zeroDecimal.hashCode()) {
            return new BigDecimal("0");
        }
        return numerator.divide(denominator, scale, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getNumerator() {
        return numerator;
    }

    public BigDecimal getDenominator() {
        return denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return getNumerator().equals(fraction.getNumerator()) &&
                getDenominator().equals(fraction.getDenominator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumerator(), getDenominator());
    }
}
