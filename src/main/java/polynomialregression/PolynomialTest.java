package polynomialregression;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolynomialTest {

    ArrayList<Point> mapPoints = new ArrayList<>();
    ArrayList<Point> xPoints = new ArrayList<>();
    ArrayList<Point> yPoints = new ArrayList<>();



    private double scale = 0.27;

    public static void main(String[] args) {

        new PolynomialTest();

    }

    private PolynomialTest() {

        /*for (int c = 0; c < 17; c++) {
            mapPoints.add(new Point(c, 1));
        }

        PolynomialRegression regression = new PolynomialRegression(mapPoints, 16);
        regression.regressPoints();//*/


        try {
            Scanner scanner = new Scanner(new File("list.txt"));
            scanner.next();
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                mapPoints.add(new Point(x, y));
            }
            for (int i = 0; i < mapPoints.size(); i += 1) {
                xPoints.add(new Point(i, mapPoints.get(i).x));
                yPoints.add(new Point(i, mapPoints.get(i).y));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        PolynomialRegression xRegression = new PolynomialRegression(xPoints, 14);
        xRegression.regressPoints();//
        //PolynomialRegression yRegression = new PolynomialRegression(yPoints, 15);
        //yRegression.regressPoints();//

        Graph graph = new Graph(800, 800, -300, -300, scale);
        graph.drawGridLines();

        drawPoints(xPoints, graph);
        //drawPoints(yPoints, graph);
        approximateLine(xRegression, graph, xPoints.get(0).x, xPoints.get(xPoints.size() - 1).x, (1.0 / scale));
        //approximateLine(yRegression, graph, yPoints.get(0).x, yPoints.get(yPoints.size() - 1).x, (1.0 / scale));

        //drawPoints(mapPoints, graph);
        //drawParametricEquation(new ParametricEquation(xRegression, yRegression), xPoints.get(xPoints.size() - 1).x - 1, graph);
        //*/
    }

    private void approximateLine(PolynomialRegression regression, Graph graph, int start, int end, double step) {

        for (double x = start; x <= end; x += step) {
            double yValue = (regression.getYValue(x));
            graph.drawPoint(x, yValue, 2, Color.CYAN);
        }

    }

    private void drawParametricEquation(ParametricEquation parametricEquation, int endTValue, Graph graph) {

        for (int t = 0; t < endTValue; t++) {
            graph.drawPoint(
                    parametricEquation.getXValueAtT(t),
                    parametricEquation.getYValueAtT(t),
                    3,
                    Color.MAGENTA
            );
        }

    }

    private void drawPoints(ArrayList<Point> points, Graph graph) {
        for (Point point : points) {
            graph.drawPoint(point.x, point.y, 3, Color.BLACK);
        }
    }

    private ArrayList<Point> getHardCodedValues() {
        ArrayList<Point> hardCodedValues = new ArrayList<Point>();

        hardCodedValues.add(new Point(0, -2));
        hardCodedValues.add(new Point(1, -1));
        hardCodedValues.add(new Point(2, -1));
        hardCodedValues.add(new Point(3, 0));
        hardCodedValues.add(new Point(4, -3));
        hardCodedValues.add(new Point(5, -2));
        hardCodedValues.add(new Point(6, 1));
        hardCodedValues.add(new Point(7, 4));
        hardCodedValues.add(new Point(8, 5));
        hardCodedValues.add(new Point(9, 4));
        hardCodedValues.add(new Point(10, 6));
        hardCodedValues.add(new Point(11, 4));
        hardCodedValues.add(new Point(12, 3));
        hardCodedValues.add(new Point(13, 1));
        hardCodedValues.add(new Point(14, -2));
        hardCodedValues.add(new Point(15, 0));
        hardCodedValues.add(new Point(16, 1));
        hardCodedValues.add(new Point(17, 9));
        hardCodedValues.add(new Point(18, 7));
        hardCodedValues.add(new Point(19, 4));

        return hardCodedValues;
    }

}
