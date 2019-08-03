package polynomialregression;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolynomialTest implements KeyListener {

    private ArrayList<Point> mapPoints = new ArrayList<>();
    private ArrayList<Point> xPoints = new ArrayList<>();
    private ArrayList<Point> yPoints = new ArrayList<>();
    private double scale = 0.53;
    private Graph graph = new Graph(1600, 950, -600, -390, scale);
    private int degree = 30;
    private PolynomialRegression xRegression;
    private PolynomialRegression yRegression;

    private static final boolean DRAW_Y_VALUES = false;
    private static final boolean DRAW_X_VALUES = false;
    private static final boolean DRAW_RACING_LINE = true;




    public static void main(String[] args) {

        new PolynomialTest();

    }

    private PolynomialTest() {

        /*for (int c = 0; c < 17; c++) {
            mapPoints.add(new Point(c, 1));
        }

        PolynomialRegression regression = new PolynomialRegression(mapPoints, 16);
        regression.regressPoints();//*/

        graph.addKeyListener(this);


        try {
            Scanner scanner = new Scanner(new File("list2.txt"));
            scanner.next();
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                mapPoints.add(new Point(x, y));
            }
            for (int i = 0; i < mapPoints.size(); i += 5) {
                xPoints.add(new Point(i, mapPoints.get(i).x));
                yPoints.add(new Point(i, mapPoints.get(i).y));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        /*for (int idx = 0; idx < xPoints.size(); idx++) {
            System.out.println((idx) + " " + xPoints.get(idx).y);
        }//*/

        drawThings();


        //*/
    }

    private void drawThings() {

        graph.dispose();
        graph.initialize();
        graph.drawGridLines();

        xRegression = new PolynomialRegression(xPoints, degree);
        yRegression = new PolynomialRegression(yPoints, degree);
        if (DRAW_X_VALUES) {
            drawPoints(xPoints, graph);
            approximateLine(xRegression, graph, xPoints.get(0).x, xPoints.get(xPoints.size() - 1).x, (1.0 / scale), Color.CYAN);
        }

        if (DRAW_Y_VALUES) {
            drawPoints(yPoints, graph);
            approximateLine(yRegression, graph, yPoints.get(0).x, yPoints.get(yPoints.size() - 1).x, (1.0 / scale), Color.blue);
        }

        if (DRAW_RACING_LINE) {
            drawPoints(mapPoints, graph);
            drawParametricEquation(new ParametricEquation(xRegression, yRegression), xPoints.get(xPoints.size() - 1).x - 1, graph);
        }//*/
    }

    private void approximateLine(PolynomialRegression regression, Graph graph, int start, int end, double step, Color color) {

        for (double x = start; x <= end; x += step) {
            double yValue = (regression.getYValue(x));
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
