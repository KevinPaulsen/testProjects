package polynomialregression;

import java.math.BigDecimal;

public class Vector {

    private BigDecimal[] dimensions;

    public Vector(BigDecimal[] dimensions) {
        this.dimensions = dimensions;
    }

    public BigDecimal getDotProduct(Vector vector) {
        if (dimensions.length != vector.getSize()) {
            throw new RuntimeException("Vectors are not of same size");
        }
        BigDecimal dotProduct = new BigDecimal("0");

        for (int idx = 0; idx < dimensions.length; idx++) {
            dotProduct = dotProduct.add(this.dimensions[idx].multiply(vector.getDimensions()[idx]));
        }
        return dotProduct;
    }

    public BigDecimal[] getDimensions() {
        return dimensions;
    }

    public int getSize() {
        return dimensions.length;
    }
}
