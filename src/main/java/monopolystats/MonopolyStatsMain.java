package monopolystats;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MonopolyStatsMain implements KeyListener {

    private static final Position[] positions = {
            /*0 */new Position("Go", Color.gray, 1),
            /*1 */new Position("Mediterranean", Color.getHSBColor(0.77222f, 1, 0.7f), 0),
            /*2 */new Position("Community Chest", Color.gray, 0),
            /*3 */new Position("Baltic", Color.getHSBColor(0.77222f, 1, 0.7f), 0),
            /*4 */new Position("Income Tax", Color.gray, 0),
            /*5 */new Position("Reading Railroad", Color.black, 0),
            /*6 */new Position("Oriental", Color.getHSBColor(0.52777f, 1, 1), 0),
            /*7 */new Position("Chance", Color.gray, 0),
            /*8 */new Position("Vermont", Color.getHSBColor(0.52777f, 1, 1), 0),
            /*9 */new Position("Connecticut", Color.getHSBColor(0.52777f, 1, 1), 0),
            /*10*/new Position("Jail", Color.gray, 0),
            /*11*/new Position("St. Charlies Place", Color.magenta, 0),
            /*12*/new Position("Electric Company", Color.black, 0),
            /*13*/new Position("States", Color.magenta, 0),
            /*14*/new Position("Virginia", Color.magenta, 0),
            /*15*/new Position("Pennsylvania Railroad", Color.black, 0),
            /*16*/new Position("St. James Place", Color.ORANGE, 0),
            /*17*/new Position("Community Chest", Color.gray, 0),
            /*18*/new Position("Tennessee", Color.ORANGE, 0),
            /*19*/new Position("New York", Color.ORANGE, 0),
            /*20*/new Position("Free Parking", Color.GRAY, 0),
            /*21*/new Position("Kentucky", Color.red, 0),
            /*22*/new Position("Chance", Color.gray, 0),
            /*23*/new Position("Indiana", Color.red, 0),
            /*24*/new Position("Illinois", Color.red, 0),
            /*25*/new Position("B&O Railroad", Color.black, 0),
            /*26*/new Position("Atlantic", Color.getHSBColor(0.16666f, 1, 1), 0),
            /*27*/new Position("Vermont", Color.getHSBColor(0.166666f, 1, 1), 0),
            /*28*/new Position("Water Works", Color.black, 0),
            /*29*/new Position("Marvin Gardens", Color.getHSBColor(0.16666f, 1, 1), 0),
            /*30*/new Position("Go To Jail", Color.gray, 0),
            /*31*/new Position("Pacific", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            /*32*/new Position("North Carolina", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            /*33*/new Position("Community Chest", Color.gray, 0),
            /*34*/new Position("Pennsylvania", Color.getHSBColor(0.35833f, 1, 0.4f), 0),
            /*35*/new Position("Short Line Railroad", Color.black, 0),
            /*36*/new Position("Chance", Color.gray, 0),
            /*37*/new Position("Park Place", Color.BLUE, 0),
            /*38*/new Position("Luxury Tax", Color.gray, 0),
            /*39*/new Position("Boardwalk", Color.BLUE, 0),
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
                int currentIdx = (die1 + die2 + idx) % 40;
                positions[currentIdx].addAverageTimesLanded(probabilityAtIdx * 1.0 / 36);

                if (currentIdx == 30) { // Go To Jail
                    positions[10].setBufferedProbability(positions[10].getBufferedProbability() + probabilityAtIdx * (1.0 / 36));
                } else if (currentIdx == 2 || currentIdx == 17 || currentIdx == 33) { // Community Chest
                    positions[0].addBufferedProbability(probabilityAtIdx * (1.0 / 17) * (1.0 / 36)); // Go
                    positions[10].addBufferedProbability(probabilityAtIdx * (1.0 / 17) * (1.0 / 36)); // Jail*/
                    positions[0].addAverageTimesLanded(probabilityAtIdx * (1.0 / 17) * (1.0 / 36));
                    positions[10].addAverageTimesLanded(probabilityAtIdx * (1.0 / 17) * (1.0 / 36));
                    positions[currentIdx].addBufferedProbability((1.0 / 36) * (15.0 / 17) * probabilityAtIdx);
                } else if (currentIdx == 7 || currentIdx == 22 || currentIdx == 36) {
                    positions[0].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Go
                    positions[0].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[24].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Illinois
                    positions[24].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[11].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // St. Charlies Place
                    positions[11].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[10].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // St. Charlies Place
                    positions[10].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[5].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Reading Railroad
                    positions[5].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[39].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Boardwalk
                    positions[39].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[(currentIdx + 37) % 40].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Go back 3 Spaces
                    positions[(currentIdx + 37) % 40].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[(currentIdx < 12 || 28 < currentIdx) ? 12 : 28].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Nearest Utility
                    positions[(currentIdx < 12 || 28 < currentIdx) ? 12 : 28].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));

                    int closestRailroad;
                    if (currentIdx == 7) {
                        closestRailroad = 15;
                    } else if (currentIdx == 22) {
                        closestRailroad = 25;
                    } else {
                        closestRailroad = 5;
                    }
                    positions[closestRailroad].addBufferedProbability(probabilityAtIdx * (1.0 / 15) * (1.0 / 36)); // Nearest Railroad//*/
                    positions[closestRailroad].addAverageTimesLanded(probabilityAtIdx * (1.0 / 15) * (1.0 / 36));
                    positions[currentIdx].addBufferedProbability((1.0 / 36) * (6.0 / 15) * probabilityAtIdx);
                } else if (die1 == die2) { // Doubles
                    if (doubleCount < 2) {
                        updateFuturePositions(currentIdx, (probabilityAtIdx * 1.0 / 36), doubleCount + 1);
                    } else {
                        positions[10].setBufferedProbability(positions[10].getBufferedProbability() + probabilityAtIdx * (1.0 / 36));
                    }
                } else {
                    positions[(currentIdx)].setBufferedProbability(positions[(currentIdx)].getBufferedProbability() + (1.0 / 36 * probabilityAtIdx));
                }
            }
        }
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
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            nextRoll();
            visualizer.displayAverage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
