package monopolystats;

public class PositionStat {
    Position position;
    double averageTimesLanded;
    double probabilityOnPosition;

    public PositionStat(Position position, double averageTimesLanded, double probabilityOnPosition) {
        this.position = position;
        this.averageTimesLanded = averageTimesLanded;
        this.probabilityOnPosition = probabilityOnPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getAverageTimesLanded() {
        return averageTimesLanded;
    }

    public void setAverageTimesLanded(double averageTimesLanded) {
        this.averageTimesLanded = averageTimesLanded;
    }

    public double getProbabilityOnPosition() {
        return probabilityOnPosition;
    }

    public void setProbabilityOnPosition(double probabilityOnPosition) {
        this.probabilityOnPosition = probabilityOnPosition;
    }

    public void addAverageTimesLanded(double averageTimesLanded) {
        this.averageTimesLanded += averageTimesLanded;
    }

    public void addProbability(double probabilityOnPosition) {
        this.probabilityOnPosition += probabilityOnPosition;
    }
}
