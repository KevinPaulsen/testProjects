package polynomialregression2;

import polynomialregression.Graph;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainTest {

    private final double xScale = 0.5;
    private final double yScale = 0.5;
    private final String filePath = "list.txt";
    private final ArrayList<Point> xPoints = new ArrayList<>();
    private final ArrayList<Point> yPoints = new ArrayList<>();
    private final ArrayList<Point> points = new ArrayList<>();
    private final Graph graph = new Graph(1600, 950, -700, -400, xScale, yScale);

    public static void main(String[] args) {
        new MainTest();
    }

    public MainTest() {

        initializePoints();

        Equation xRegression = new Regression(70, xPoints);
        Equation yRegression = new Regression(70, yPoints);
        drawScreen();
        approximateLine(xRegression, xPoints.get(0).x, xPoints.get(xPoints.size() - 1).x, (1.0 / xScale), Color.CYAN);
        approximateLine(yRegression, xPoints.get(0).x, xPoints.get(xPoints.size() - 1).x, (1.0 / xScale), Color.CYAN);

        drawParametricEquation(xRegression, yRegression);
    }

    private void drawScreen() {
        graph.dispose();
        graph.initialize();
        graph.drawGridLines();

        drawPoints(xPoints);
        drawPoints(yPoints);
        drawPoints(points);
    }

    private void drawParametricEquation(Equation xEquation, Equation yEquation) {
        for (int t = 0; t < xPoints.size(); t++) {
            graph.drawPoint(
                    xEquation.evaluateAt(t),
                    yEquation.evaluateAt(t),
                    3,
                    Color.MAGENTA
            );
        }
    }

    private void approximateLine(Equation equation, int start, int end, double step, Color color) {
        for (double x = start; x <= end; x += step) {
            double yValue = (equation.evaluateAt(x));
            graph.drawPoint(x, yValue, 2, color);
        }
    }

    private void drawPoints(ArrayList<Point> points) {
        for (Point point : points) {
            graph.drawPoint(point.x, point.y, 3, Color.BLACK);
        }
    }

    /**
     * Copies entries from text file, into xPoints, and yPoints
     */
    private void initializePoints() {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.next();
            int count = 0;
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                xPoints.add(new Point(count, x));
                yPoints.add(new Point(count, y));
                points.add(new Point(x, y));
                count++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
