package polynomialregression;

import javax.swing.*;
import java.awt.*;

public class Graph extends JFrame {

    private int xAxis;
    private int yAxis;
    private double scale;
    private int windowWidth, windowHeight;

    Graph(int windowWidth, int windowHeight, int xOffset, int yOffset, double scale) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        this.xAxis = windowWidth / 2 + xOffset;
        this.yAxis = windowHeight / 2 - yOffset;
        this.scale = scale;

        initialize();
    }

    @Override
    public void paint(Graphics g) {
    }

    void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight + 22);
        setResizable(false);
        setVisible(true);
        setFocusable(true);
        setIgnoreRepaint(true);
    }

    void drawPoint(double x, double y, int size, Color color) {
        Graphics g = getGraphics();
        g.setColor(color);
        g.fillRect((int) Math.round (x * scale) + xAxis, yAxis - (int) Math.round(y * scale), size, size);
    }

    void drawGridLines() {
        getGraphics().drawLine(xAxis, 0, xAxis, windowHeight + 22);
        getGraphics().drawLine(0, yAxis, windowWidth, yAxis);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    void setColor(Color color) {
        getGraphics().setColor(color);
    }
}

