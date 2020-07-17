package monopolystats;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class MonopolyVisualizer extends JFrame {
    private static final int width = 1900;
    private static final int height = 900;
    private static final int COLUMN_WIDTH = 20;
    private static final int COlUMN_MAX_HEIGHT = 100;
    private final Position[] positions;

    public MonopolyVisualizer(Position[] positions) {
        this.positions = positions;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(false);
        setVisible(true);

        init();
    }

    private void init() {
        Graphics2D graphics2D = (Graphics2D) this.getGraphics();
        graphics2D.drawLine(0, 780, 1900, 780);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.PI / 4);
        graphics2D.setFont(new Font(null, Font.PLAIN, 12).deriveFont(affineTransform));
        for (int idx = 0; idx < positions.length; idx++) {
            graphics2D.setColor(positions[idx].getColor());
            graphics2D.drawString(positions[idx].getName(),idx * 45 + 30, 800);
        }
        graphics2D.dispose();
    }

    public void displayProbability() {
        Graphics g = getGraphics();
        g.clearRect(0, 0, 2000, 1000);
        init();

        for (int idx = 0; idx < positions.length; idx++) {
            if (positions[idx].getProbability() != 0) {
                int center = idx * 45 + 30;
                int height = ((int) (COlUMN_MAX_HEIGHT * positions[idx].getProbability()));
                g.setColor(positions[idx].getColor());
                g.fillRect(center - (COLUMN_WIDTH / 2), 780 - height, COLUMN_WIDTH, height);
            }
        }
    }

    public void displayAverage() {
        Graphics g = getGraphics();
        g.clearRect(0, 0, 2000, 1000);
        init();

        for (int idx = 0; idx < positions.length; idx++) {
            if (positions[idx].getAverageTimesLanded() != 0) {
                int center = idx * 45 + 30;
                int height = ((int) (COlUMN_MAX_HEIGHT * positions[idx].getAverageTimesLanded()));
                g.setColor(positions[idx].getColor());
                g.fillRect(center - (COLUMN_WIDTH / 2), 780 - height, COLUMN_WIDTH, height);
            }
        }
    }
}