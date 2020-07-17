package monopolystats;

import java.awt.*;

public class Position {

    private final String name;
    private final Color color;

    public Position(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
