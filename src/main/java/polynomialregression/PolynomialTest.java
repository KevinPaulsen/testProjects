package polynomialregression;

import java.awt.*;
import java.util.ArrayList;

public class PolynomialTest {

    private int scale = 20;

    public static void main(String[] args) {

        new PolynomialTest();

    }

    private PolynomialTest() {
        ArrayList<Point> points = new ArrayList<>();

        points = getHardCodedValues();

        PolynomialRegression regression = new PolynomialRegression(points, 6);
        regression.regressPoints();//*/

        Graph graph = new Graph(500, 500, -200, 0, scale);
        graph.drawGridLines();
        drawPoints(points, graph);
        approximateLine(regression, graph, points.get(0).x, points.get(points.size() - 1).x * 10, (1.0 / scale));
        //graph.drawPoint(xOffset, yOffset, 10, Color.MAGENTA);
    }

    private void approximateLine(PolynomialRegression regression, Graph graph, int start, int end, double step) {

        for (double x = start; x <= end; x += step) {
            double yValue = (regression.getYValue(x));
            graph.drawPoint(x, yValue, 2, Color.CYAN);
        }

    }

    private void drawPoints(ArrayList<Point> points, Graph graph) {
        for (Point point : points) {
            graph.drawPoint(point.x, point.y, 5, Color.BLACK);
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
