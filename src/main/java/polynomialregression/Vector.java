package polynomialregression;

import java.math.BigDecimal;

class Vector {

    private BigDecimal[] dimensions;

    Vector(BigDecimal[] dimensions) {
        this.dimensions = dimensions;
    }

    BigDecimal getDotProduct(Vector vector) {
        if (dimensions.length != vector.getSize()) {
            throw new RuntimeException("Vectors are not of same size");
        }
        BigDecimal dotProduct = new BigDecimal("0");

        for (int idx = 0; idx < dimensions.length; idx++) {
            dotProduct = dotProduct.add(this.dimensions[idx].multiply(vector.getDimensions()[idx]));
        }
        return dotProduct;
    }

    private BigDecimal[] getDimensions() {
        return dimensions;
    }

    private int getSize() {
        return dimensions.length;
    }
}
