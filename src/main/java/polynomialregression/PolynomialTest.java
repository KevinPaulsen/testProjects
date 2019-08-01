package polynomialregression;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PolynomialTest {

    public static void main(String[] args) {

        /*int n = 50;
        ArrayList<ArrayList<BigDecimal>> lists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<BigDecimal> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new BigDecimal("" + Math.round(Math.random() * 5 + 1)));
            }
            lists.add(row);
        }//

        Matrix testMatrix = new Matrix(lists);
        //testMatrix.printMatrix();
        //System.out.println("\n\n");
        //testMatrix.getInverse().printMatrix();
        testMatrix.printMatrix();
        System.out.println("-----------------------------------------------------------------------------------------------\n");
        testMatrix.getInverse2().printMatrix();
        //testMatrix.getMultiply(testMatrix.getInverse2()).printMatrix();//*/

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            points.add(new Point(i, (int) Math.pow(i, 2)));
        }
        PolynomialRegression regression = new PolynomialRegression(points, 30);
        regression.regressPoints();//*/
    }

}
