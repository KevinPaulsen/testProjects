package monopolystats;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MonopolyStatsMain implements KeyListener {

    private static final Position[] positions = {
            /*0 */new Position("Go", Color.gray),
            /*1 */new Position("Mediterranean", Color.getHSBColor(0.77222f, 1, 0.7f)),
            /*2 */new Position("Community Chest", Color.gray),
            /*3 */new Position("Baltic", Color.getHSBColor(0.77222f, 1, 0.7f)),
            /*4 */new Position("Income Tax", Color.gray),
            /*5 */new Position("Reading Railroad", Color.black),
            /*6 */new Position("Oriental", Color.getHSBColor(0.52777f, 1, 1)),
            /*7 */new Position("Chance", Color.gray),
            /*8 */new Position("Vermont", Color.getHSBColor(0.52777f, 1, 1)),
            /*9 */new Position("Connecticut", Color.getHSBColor(0.52777f, 1, 1)),
            /*10*/new Position("Jail", Color.gray),
            /*11*/new Position("St. Charlies Place", Color.magenta),
            /*12*/new Position("Electric Company", Color.black),
            /*13*/new Position("States", Color.magenta),
            /*14*/new Position("Virginia", Color.magenta),
            /*15*/new Position("Pennsylvania Railroad", Color.black),
            /*16*/new Position("St. James Place", Color.ORANGE),
            /*17*/new Position("Community Chest", Color.gray),
            /*18*/new Position("Tennessee", Color.ORANGE),
            /*19*/new Position("New York", Color.ORANGE),
            /*20*/new Position("Free Parking", Color.GRAY),
            /*21*/new Position("Kentucky", Color.red),
            /*22*/new Position("Chance", Color.gray),
            /*23*/new Position("Indiana", Color.red),
            /*24*/new Position("Illinois", Color.red),
            /*25*/new Position("B&O Railroad", Color.black),
            /*26*/new Position("Atlantic", Color.getHSBColor(0.16666f, 1, 1)),
            /*27*/new Position("Vermont", Color.getHSBColor(0.166666f, 1, 1)),
            /*28*/new Position("Water Works", Color.black),
            /*29*/new Position("Marvin Gardens", Color.getHSBColor(0.16666f, 1, 1)),
            /*30*/new Position("Go To Jail", Color.gray),
            /*31*/new Position("Pacific", Color.getHSBColor(0.35833f, 1, 0.4f)),
            /*32*/new Position("North Carolina", Color.getHSBColor(0.35833f, 1, 0.4f)),
            /*33*/new Position("Community Chest", Color.gray),
            /*34*/new Position("Pennsylvania", Color.getHSBColor(0.35833f, 1, 0.4f)),
            /*35*/new Position("Short Line Railroad", Color.black),
            /*36*/new Position("Chance", Color.gray),
            /*37*/new Position("Park Place", Color.BLUE),
            /*38*/new Position("Luxury Tax", Color.gray),
            /*39*/new Position("Boardwalk", Color.BLUE),
    };
    private final ArrayList<PositionStat[]> moveStats = new ArrayList<>();
    private PositionStat[] currentMoveStats;
    private PositionStat[] nextMoveStats;
    private final MonopolyVisualizer visualizer;
    private int move = 0;
    private boolean displayProbability = true;
    private int count = 0;

    public MonopolyStatsMain() {
        currentMoveStats = new PositionStat[positions.length];
        for (int idx = 0; idx < positions.length; idx++) {
            currentMoveStats[idx] = new PositionStat(positions[idx], 0, 0);
        }
        currentMoveStats[0].setProbabilityOnPosition(1);
        visualizer = new MonopolyVisualizer(positions, currentMoveStats);
        visualizer.addKeyListener(this);
        moveStats.add(currentMoveStats);
    }

    public static void main(String[] args) {
        new MonopolyStatsMain();
    }

    private void nextRoll() {
        move++;
        if (moveStats.size() > move) {
            currentMoveStats = moveStats.get(move);
        } else {
            nextMoveStats = getNextMoveArray();
            for (int idx = 0; idx < currentMoveStats.length; idx++) {
                updateProbabilitiesAtIndex(nextMoveStats, idx, currentMoveStats[idx].getProbabilityOnPosition(), 0);
            }
            moveStats.add(currentMoveStats);
            currentMoveStats = nextMoveStats;
        }
    }

    private PositionStat[] getNextMoveArray() {
        PositionStat[] nextMoveArray = new PositionStat[positions.length];
        for (int idx = 0; idx < currentMoveStats.length; idx++) {
            nextMoveArray[idx] = new PositionStat(positions[idx], currentMoveStats[idx].getAverageTimesLanded(), 0);
        }
        return nextMoveArray;
    }

    private void updateProbabilitiesAtIndex(PositionStat[] nextMoveStats, int idx, double probabilityAtIdx, int doubleCount) {
        for (int die1 = 1; die1 <= 6; die1++) {
            for (int die2 = 1; die2 <= 6; die2++) {
                int currentIdx = (die1 + die2 + idx) % 40;
                // Add position to average times landed

                int closestRailroad = 5;
                if (currentIdx == 7) {
                    closestRailroad = 15;
                } else if (currentIdx == 22) {
                    closestRailroad = 25;
                }
                // Calculate probability this move (and any future movement from this roll) happens
                if (currentIdx == 30) { // Go To Jail
                    nextMoveStats[10].addProbability(probabilityAtIdx * (1.0 / 36));
                    nextMoveStats[10].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                    nextMoveStats[currentIdx].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                } else if (die1 == die2) { // Doubles
                    if (doubleCount < 2) {
                        if (currentIdx == 2 || currentIdx == 17 || currentIdx == 33) { // Community Chest
                            continueRollAtIndices(nextMoveStats, probabilityAtIdx * (1.0 / 17) * (1.0 / 36), doubleCount, 0, 10);
                            updateAverageTotalFromCard(nextMoveStats, probabilityAtIdx, 17, 0, 10);
                            updateProbabilitiesAtIndex(nextMoveStats, currentIdx, (probabilityAtIdx * (1.0 / 36) * (15.0 / 17)), doubleCount + 1);
                        } else if (currentIdx == 7 || currentIdx == 22 || currentIdx == 36) {
                            continueRollAtIndices(nextMoveStats, probabilityAtIdx * (1.0 / 15) * (1.0 / 36), doubleCount,
                                    0, 5, 10, 11, 24, 39, (currentIdx + 37) % 39,
                                    (currentIdx < 12 || 28 < currentIdx) ? 12 : 28, closestRailroad);
                            updateAverageTotalFromCard(nextMoveStats, probabilityAtIdx, 15,
                                    0, 5, 10, 11, 24, 39, (currentIdx + 37) % 39,
                                    (currentIdx < 12 || 28 < currentIdx) ? 12 : 28, closestRailroad);
                            updateProbabilitiesAtIndex(nextMoveStats, currentIdx, (probabilityAtIdx * (1.0 / 36) * (6.0 / 15)), doubleCount + 1);
                        } else {
                            nextMoveStats[currentIdx].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                            updateProbabilitiesAtIndex(nextMoveStats, currentIdx, (probabilityAtIdx * (1.0 / 36)), doubleCount + 1);
                        }
                    } else {
                        nextMoveStats[10].addProbability(probabilityAtIdx * (1.0 / 36));
                        nextMoveStats[10].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                    }
                } else if (currentIdx == 2 || currentIdx == 17 || currentIdx == 33) { // Community Chest
                    updateProbabilityFromCard(nextMoveStats, currentIdx, probabilityAtIdx, 17, 0 , 10);
                    nextMoveStats[currentIdx].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                } else if (currentIdx == 7 || currentIdx == 22 || currentIdx == 36) { // Chance
                    updateProbabilityFromCard(nextMoveStats, currentIdx, probabilityAtIdx, 15,
                            0, 5, 10, 11, 24, 39, (currentIdx + 37) % 39,
                            (currentIdx < 12 || 28 < currentIdx) ? 12 : 28, closestRailroad);
                    nextMoveStats[currentIdx].addAverageTimesLanded(probabilityAtIdx * (1.0 / 36));
                } else { // Not Doubles, Didn't Move
                    nextMoveStats[(currentIdx)].addProbability((1.0 / 36) * probabilityAtIdx);
                    nextMoveStats[currentIdx].addAverageTimesLanded(probabilityAtIdx * 1.0 / 36);
                }
            }
        }
    }

    private void continueRollAtIndices(PositionStat[] nextMoveStats, double probabilityAtIdx, int doubleCount, int ... indices) {
        for (int idx : indices) {
            updateProbabilitiesAtIndex(nextMoveStats, idx, probabilityAtIdx, doubleCount + 1);
        }
    }

    private void updateProbabilityFromCard(PositionStat[] nextMoveStats, int currentIdx, double probabilityAtIdx, int numCards, int ... positions) {
        for (int position : positions) {
            nextMoveStats[position].addProbability(probabilityAtIdx * (1.0 / numCards) * (1.0 / 36));
            nextMoveStats[position].addAverageTimesLanded(probabilityAtIdx * (1.0 / numCards) * (1.0 / 36));
        }
        nextMoveStats[currentIdx].addProbability((1.0 / 36) * (((double) numCards - positions.length) / numCards) * probabilityAtIdx);
    }

    private void updateAverageTotalFromCard(PositionStat[] nextMoveStats, double probabilityAtIdx, int numCards, int ... positions) {
        for (int position : positions) {
            nextMoveStats[position].addAverageTimesLanded(probabilityAtIdx * (1.0 / numCards) * (1.0 / 36));
        }
    }

    private void displayCurrentPosition() {
        if (displayProbability) {
            visualizer.displayProbability(currentMoveStats, move);
        } else {
            visualizer.displayAverage(currentMoveStats, move);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            nextRoll();
            displayCurrentPosition();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            double sum = 0;
            for (PositionStat position : currentMoveStats) {
                sum += position.getProbabilityOnPosition();
            }
            System.out.println(sum);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (move > 1) {
                move -= 1;
                currentMoveStats = moveStats.get(move);
                displayCurrentPosition();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            displayProbability = !displayProbability;
            displayCurrentPosition();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
