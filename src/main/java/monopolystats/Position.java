package monopolystats;

import java.awt.*;

public class Position {

    private final String name;
    private final Color color;
    private double probability = 0;
    private double bufferedProbability = 0;

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
