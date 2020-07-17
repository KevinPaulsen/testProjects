package monopolystats;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;
import java.math.MathContext;

public class MonopolyVisualizer extends JFrame {
    private final Position[] positions;
    private double COlUMN_HEIGHT_MULTIPLIER;
    private int move = 0;

    private static final int WIDTH = 1900;
    private static final int HEIGHT = 900;
    private static final int COLUMN_WIDTH = 25;
    private static final int MAX_COLUMN_HEIGHT = 700;
    private static final int LABEL_X_OFFSET = 15;
    private static final int LABEL_Y_OFFSET = 120;
    private static final int FONT_SIZE = 12;
    private static final int MOVE_LABEL_X_OFFSET = 100;
    private static final int MOVE_LABEL_Y_OFFSET = 50;
    private static final int ROUNDING_PRECISION = 3;

    public MonopolyVisualizer(Position[] position, PositionStat[] positionStats) {
        this.positions = position;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);

        init(positionStats);
    }

    private void init(PositionStat[] positionStats) {
        drawBlankScreen(positionStats);
        displayProbability(positionStats, 0);
    }

    private void drawBlankScreen(PositionStat[] positionStats) {
        Graphics2D graphics2D = (Graphics2D) this.getGraphics();
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.PI / 4);
        graphics2D.setFont(new Font(null, Font.PLAIN, FONT_SIZE).deriveFont(affineTransform));
        for (int idx = 0; idx < positionStats.length; idx++) {
            graphics2D.setColor(positionStats[idx].getPosition().getColor());
            graphics2D.drawString(positionStats[idx].getPosition().getName(),idx * (WIDTH / positions.length) + LABEL_X_OFFSET, HEIGHT - LABEL_Y_OFFSET + 20);
        }
        graphics2D.setColor(Color.black);
        graphics2D.drawLine(0, HEIGHT - LABEL_Y_OFFSET, WIDTH, HEIGHT - LABEL_Y_OFFSET);
        graphics2D.dispose();
        getGraphics().drawString("Move: " + move, WIDTH - MOVE_LABEL_X_OFFSET, MOVE_LABEL_Y_OFFSET);
    }

    private double getMaxProbability(PositionStat[] positionStats) {
        double maxProb = positionStats[0].getProbabilityOnPosition();
        for (PositionStat positionStat : positionStats) {
            maxProb = Math.max(maxProb, positionStat.getProbabilityOnPosition());
        }
        return maxProb;
    }

    private double getMaxAverage(PositionStat[] positionStats) {
        double maxProb = positionStats[0].getAverageTimesLanded();
        for (PositionStat positionStat : positionStats) {
            maxProb = Math.max(maxProb, positionStat.getAverageTimesLanded());
        }
        return maxProb;
    }

    public void displayProbability(PositionStat[] positionStat, int move) {
        this.move = move;
        clearScreen(positionStat);
        COlUMN_HEIGHT_MULTIPLIER = MAX_COLUMN_HEIGHT / getMaxProbability(positionStat);

        for (int idx = 0; idx < positionStat.length; idx++) {
            if (positionStat[idx].getProbabilityOnPosition() != 0) {
                int height = HEIGHT - LABEL_Y_OFFSET - ((int) (COlUMN_HEIGHT_MULTIPLIER * positionStat[idx].getProbabilityOnPosition()));
                drawColumn(positionStat, idx, height, positionStat[idx].getProbabilityOnPosition());
            }
        }
    }


    public void displayAverage(PositionStat[] positionStat, int move) {
        this.move = move;
        clearScreen(positionStat);
        COlUMN_HEIGHT_MULTIPLIER = MAX_COLUMN_HEIGHT / getMaxAverage(positionStat);

        for (int idx = 0; idx < positionStat.length; idx++) {
            if (positionStat[idx].getAverageTimesLanded() != 0) {
                int height = HEIGHT - LABEL_Y_OFFSET - ((int) (COlUMN_HEIGHT_MULTIPLIER * positionStat[idx].getAverageTimesLanded()));
                drawColumn(positionStat, idx, height, positionStat[idx].getAverageTimesLanded());
            }
        }
    }

    private void drawColumn(PositionStat[] positionStat, int idx, int height, double value) {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        int center = idx * (WIDTH / positions.length) + LABEL_X_OFFSET;
        graphics2D.setColor(positionStat[idx].getPosition().getColor());
        graphics2D.fillRect(center - (COLUMN_WIDTH / 2), height, COLUMN_WIDTH, HEIGHT - height - LABEL_Y_OFFSET);

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(-Math.PI / 4);
        graphics2D.setFont(new Font(null, Font.PLAIN, FONT_SIZE).deriveFont(affineTransform));
        graphics2D.drawString(new BigDecimal(value).round(new MathContext(ROUNDING_PRECISION)).toPlainString(), center, height);
        graphics2D.dispose();
    }

    private void clearScreen(PositionStat[] positionStats) {
        this.getGraphics().clearRect(0, 0, WIDTH, HEIGHT);
        drawBlankScreen(positionStats);
    }
}