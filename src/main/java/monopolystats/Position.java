package monopolystats;

import java.awt.*;

public class Position {

    private final String name;
    private final Color color;
    private double probability;
    private double bufferedProbability = 0;
    private double averageTimesLanded = 0;

    public Position(String name, Color color, double probability) {
        this.name = name;
        this.color = color;
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getBufferedProbability() {
        return bufferedProbability;
    }

    public double getAverageTimesLanded() {
        return averageTimesLanded;
    }

    public void addAverageTimesLanded(double averageTimesLanded) {
        this.averageTimesLanded += averageTimesLanded;
    }

    public void addBufferedProbability(double bufferedProbability) {
        this.bufferedProbability += bufferedProbability;
    }

    public void setBufferedProbability(double bufferedProbability) {
        this.bufferedProbability = bufferedProbability;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
