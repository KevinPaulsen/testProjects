package monopolystats;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MonopolyStatsMain implements KeyListener {

    private static final Position[] positions = {
            new Position("Go", Color.gray, 1),
            new Position("Mediterranean", Color.getHSBColor(0.77222f, 1, 0.7f), 0),
            new Position("Community Chest", Color.gray, 0),
            new Position("Baltic", Color.getHSBColor(0.77222f, 1, 0.7f), 0),
            new Position("Income Tax", Color.gray, 0),
            new Position("Reading Rail Road", Color.black, 0),
            new Position("Oriental", Color.getHSBColor(0.52777f, 1, 1), 0),
            new Position("Chance", Color.gray, 0),
            new Position("Vermont", Color.getHSBColor(0.52777f, 1, 1), 0),
            new Position("Connecticut", Color.getHSBColor(0.52777f, 1, 1), 0),
            new Position("Jail", Color.gray, 0),
            new Position("St. Charlies Place", Color.magenta, 0),
            new Position("Electric Company", Color.black, 0),
            new Position("States", Color.magenta, 0),
            new Position("Virginia", Color.magenta, 0),
            new Position("Pennsylvania Railroad", Color.black, 0),
            new Position("St. James Place", Color.ORANGE, 0),
            new Position("Community Chest", Color.gray, 0),
            new Position("Tennessee", Color.ORANGE, 0),
            new Position("New York", Color.ORANGE, 0),
            new Position("Free Parking", Color.GRAY, 0),
            new Position("Kentucky", Color.red, 0),
            new Position("Chance", Color.gray, 0),
            new Position("Indiana", Color.red, 0),
            new Position("Illinois", Color.red, 0),
            new Position("B&O Railroad", Color.black, 0),
            new Position("Atlantic", Color.getHSBColor(0.16666f, 1, 1), 0),
            new Position("Vermont", Color.getHSBColor(0.166666f, 1, 1), 0),
            new Position("Water Works", Color.black, 0),
            new Position("Marvin Gardens", Color.getHSBColor(0.16666f, 1, 1), 0),
            new Position("Go To Jail", Color.gray, 0),
            new Position("Pacific", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            new Position("North Carolina", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            new Position("Community Chest", Color.gray, 0),
            new Position("Pennsylvania", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            new Position("Short Line Railroad", Color.black, 0),
            new Position("Chance", Color.gray, 0),
            new Position("Park Place", Color.BLUE, 0),
            new Position("Luxury Tax", Color.gray, 0),
            new Position("Boardwalk", Color.BLUE, 0),
    };
    private static final double[] rollProbability = {
            0,
            0,
            1.0/36,
            2.0/36,
            3.0/36,
            4.0/36,
            5.0/36,
            6.0/36,
            5.0/36,
            4.0/36,
            3.0/36,
            2.0/36,
            1.0/36,
    };
    private static final MonopolyVisualizer visualizer = new MonopolyVisualizer(positions);

    public MonopolyStatsMain() {
        visualizer.addKeyListener(this);
    }

    public static void main(String[] args) {
        new MonopolyStatsMain();
    }

    private static void nextRoll() {
        for (int idx = 0; idx < positions.length; idx++) {
            updateFuturePositions(idx, positions[idx].getProbability(), 0);
        }
        for (Position position : positions) {
            position.setProbability(position.getBufferedProbability());
            position.setBufferedProbability(0);
        }
    }

    private static void updateFuturePositions(int idx, double probabilityAtIdx, int doubleCount) {


        for (int die1 = 1; die1 <= 6; die1++) {
            for (int die2 = 1; die2 <= 6; die2++) {

               if (die1 == die2) { // Doubles
                   if (doubleCount < 2) {
                       updateFuturePositions(die1 + die2 + idx, (probabilityAtIdx * 1.0 / 36), doubleCount + 1);
                   } else {
                       positions[10].setBufferedProbability(positions[10].getBufferedProbability() + probabilityAtIdx * (1.0 / 36));
                   }
               } else if (die1 + die2 + idx == 30) { // Go To Jail
                   positions[10].setBufferedProbability(positions[10].getBufferedProbability() + probabilityAtIdx * (1.0 / 36));
               } else {
                   positions[(idx + die1 + die2) % 40].setBufferedProbability(positions[(idx + die1 + die2) % 40].getBufferedProbability() + (1.0 / 36 * probabilityAtIdx));
               }
            }
        }
        /*



        Position currentPosition = positions[idx % 40];
        for (int roll1 = 1; roll1 <= 6; roll1++) {
            for (int roll2 = 1; roll2 <= 6; roll2++) {
                int roll = roll1 + roll2;

                if (roll1 == roll2) { // Landed on idx + roll but is double
                    doubleCount++;
                    if (doubleCount == 3) {
                        positions[10].setBufferedProbability(positions[10].getBufferedProbability() + 1.0/46656);
                        doubleCount--;
                    } else {
                        updateFuturePositions(idx + roll, doubleCount);
                    }
                } else {
                    positions[(idx + roll) % 40].setBufferedProbability(positions[(idx + roll) % 40].getBufferedProbability() +
                            (currentPosition.getProbability() * rollProbability[roll]));
                }
            }
        }//*/
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            nextRoll();
            visualizer.displayProbability();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            double sum = 0;
            for (Position position : positions) {
                sum += position.getProbability();
            }
            System.out.println(sum);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
