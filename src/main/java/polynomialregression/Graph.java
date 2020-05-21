package polynomialregression;

import javax.swing.*;
import java.awt.*;

public class Graph extends JFrame {

    private final int xAxis;
    private final int yAxis;
    private final double xScale;
    private final double yScale;
    private final int windowWidth;
    private final int windowHeight;

    public Graph(int windowWidth, int windowHeight, int xOffset, int yOffset, double xScale, double yScale) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        this.xAxis = windowWidth / 2 + xOffset;
        this.yAxis = windowHeight / 2 - yOffset;
        this.xScale = xScale;
        this.yScale = yScale;

        initialize();
    }

    @Override
    public void paint(Graphics g) {}

    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight + 22);
        setResizable(false);
        setVisible(true);
        setFocusable(true);
        setIgnoreRepaint(true);
    }

    public void drawPoint(double x, double y, int size, Color color) {
        Graphics g = getGraphics();
        g.setColor(color);
        g.fillRect((int) Math.round(x * xScale) + xAxis, yAxis - (int) Math.round(y * yScale), size, size);
    }

    public void drawGridLines() {
        getGraphics().drawLine(xAxis, 0, xAxis, windowHeight + 22);
        getGraphics().drawLine(0, yAxis, windowWidth, yAxis);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
}

