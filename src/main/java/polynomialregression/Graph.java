package polynomialregression;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Graph extends JFrame {

    private int xOffset;
    private int yOffset;
    private int scale;
    private int windowWidth, windowHeight;

    Graph(int windowWidth, int windowHeight, int xOffset, int yOffset, int scale) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        this.xOffset = windowWidth / 2 + xOffset;
        this.yOffset = windowHeight / 2 + yOffset;
        this.scale = scale;

        initialize();
    }

    @Override
    public void paint(Graphics g) {
    }

    private void initialize() {
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
        g.fillRect((int) Math.round (x * scale) + xOffset, (int) Math.round(y * scale) + yOffset + 22, size, size);
    }

    void drawGridLines() {
        getGraphics().drawLine(xOffset, 0, xOffset, windowHeight + 22);
        getGraphics().drawLine(0, yOffset, windowWidth, yOffset);
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

