package polynomialregression;

import java.util.ArrayList;

public class PolynomialTest {

    public static void main(String[] args) {

        int n = 11;
        ArrayList<ArrayList<Fraction>> lists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Fraction> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new Fraction("" + Math.round(Math.random() * 5 + 1),
                        "" + 1));
            }
            lists.add(row);
        }//*/

        /*Fraction[] row1 = {
                new Fraction("5", "1"),
                new Fraction("2", "1"),
                new Fraction("2", "1"),
                new Fraction("5", "1"),
                new Fraction("3", "1"),
                new Fraction("4", "1"),
                new Fraction("4", "1"),
        };
        Fraction[] row2 = {
                new Fraction("1", "1"),
                new Fraction("3", "1"),
                new Fraction("5", "1"),
                new Fraction("4", "1"),
                new Fraction("1", "1"),
                new Fraction("3", "1"),
                new Fraction("2", "1"),
        };
        Fraction[] row3 = {
                new Fraction("4", "1"),
                new Fraction("5", "1"),
                new Fraction("4", "1"),
                new Fraction("4", "1"),
                new Fraction("3", "1"),
                new Fraction("5", "1"),
                new Fraction("2", "1"),
        };
        Fraction[] row4 = {
                new Fraction("2", "1"),
                new Fraction("6", "1"),
                new Fraction("6", "1"),
                new Fraction("5", "1"),
                new Fraction("3", "1"),
                new Fraction("5", "1"),
                new Fraction("2", "1"),
        };
        Fraction[] row5 = {
                new Fraction("4", "1"),
                new Fraction("4", "1"),
                new Fraction("3", "1"),
                new Fraction("2", "1"),
                new Fraction("4", "1"),
                new Fraction("2", "1"),
                new Fraction("1", "1"),
        };
        Fraction[] row6 = {
                new Fraction("4", "1"),
                new Fraction("1", "1"),
                new Fraction("6", "1"),
                new Fraction("4", "1"),
                new Fraction("3", "1"),
                new Fraction("5", "1"),
                new Fraction("1", "1"),
        };
        Fraction[] row7 = {
                new Fraction("1", "1"),
                new Fraction("4", "1"),
                new Fraction("4", "1"),
                new Fraction("2", "1"),
                new Fraction("2", "1"),
                new Fraction("1", "1"),
                new Fraction("4", "1"),
        };

        Fraction[][] brockenList = {row1, row2, row3, row4, row5, row6, row7};//*/

        Matrix testMatrix = new Matrix(lists);
        //testMatrix.printMatrix();
        //System.out.println("\n\n");
        //testMatrix.getInverse().printMatrix();
        //testMatrix.printMatrix();
        //System.out.println("-----------------------------------------------------------------------------------------------\n");
        testMatrix.getInverse2().printMatrix();
        //testMatrix.getMultiply(testMatrix.getInverse2()).printMatrix();

        /*ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            points.add(new Point(i, (int) Math.pow(i, 6)));
        }
        PolynomialRegression regression = new PolynomialRegression(points, 15);
        regression.regressPoints();//*/
    }

}
