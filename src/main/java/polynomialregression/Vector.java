package polynomialregression;

public class Vector {

    private Fraction[] dimensions;

    public Vector(Fraction[] dimensions) {
        this.dimensions = dimensions;
    }

    public Fraction getDotProduct(Vector vector) {
        if (dimensions.length != vector.getSize()) {
            throw new RuntimeException("Vectors are not of same size");
        }
        Fraction dotProduct = new Fraction("0", "1");

        for (int idx = 0; idx < dimensions.length; idx++) {
            dotProduct.add(this.dimensions[idx].getMultiply(vector.getDimensions()[idx]));
        }
        return dotProduct;
    }

    public Fraction[] getDimensions() {
        return dimensions;
    }

    public int getSize() {
        return dimensions.length;
    }
}
