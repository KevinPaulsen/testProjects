package polynomialregression;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolynomialTest implements KeyListener {

    private final ArrayList<Point> mapPoints = new ArrayList<>();
    private final ArrayList<Point> xPoints = new ArrayList<>();
    private final ArrayList<Point> yPoints = new ArrayList<>();
    private final double xScale = 0.35;
    private final double yScale = 0.35;

    private final Graph graph = new Graph(1600, 950, -700, -200, xScale, yScale);
    private int degree = 50;

    private static final boolean DRAW_Y_VALUES = false;
    private static final boolean DRAW_X_VALUES = true;
    private static final boolean DRAW_RACING_LINE = false;




    public static void main(String[] args) {
        new PolynomialTest();
    }

    private PolynomialTest() {

        graph.addKeyListener(this);

        try {
            Scanner scanner = new Scanner(new File("list2.txt"));
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

        drawThings();
    }

    private void drawThings() {

        graph.dispose();
        graph.initialize();
        graph.drawGridLines();

        PolynomialRegression xRegression = new PolynomialRegression(xPoints, degree);
        PolynomialRegression yRegression = new PolynomialRegression(yPoints, degree);

        if (DRAW_X_VALUES) {
            drawPoints(xPoints, graph);
            approximateLine(xRegression, graph, xPoints.get(0).x, xPoints.get(xPoints.size() - 1).x, (1.0 / xScale), Color.CYAN);
        }

        if (DRAW_Y_VALUES) {
            drawPoints(yPoints, graph);
            approximateLine(yRegression, graph, yPoints.get(0).x, yPoints.get(yPoints.size() - 1).x, (1.0 / yScale), Color.blue);
        }

        if (DRAW_RACING_LINE) {
            drawPoints(mapPoints, graph);
            drawParametricEquation(new ParametricEquation(xRegression, yRegression), xPoints.get(xPoints.size() - 1).x - 1, graph);
        }//*/
    }

    private void approximateLine(PolynomialEquation polynomialEquation, Graph graph, int start, int end, double step, Color color) {
        for (double x = start; x <= end; x += step) {
            double yValue = (polynomialEquation.getYValue(x));
            graph.drawPoint(x, yValue, 2, color);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            degree++;
            drawThings();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            degree--;
            drawThings();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
