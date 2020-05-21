import java.util.Arrays;

public class LoopIterationSpeed {

    private static final int NUM_LOOP = 100_000_000;
    private int[] testArray = new int[NUM_LOOP];

    public static void main(String[] args) {
        new LoopIterationSpeed();
    }

    private LoopIterationSpeed() {
        singleLoop();
        doubleLoop();

        long doubleLoopTime = doubleLoop();
        long singleLoopTime = singleLoop();

        System.out.printf("Single Loop is %f%s faster than a double Loop", (((double) doubleLoopTime - singleLoopTime) / doubleLoopTime), "%");

    }

    private long doubleLoop() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_LOOP; i++) {
            // Some random Math operations
            int r = (i + 3 * i) & 0xFF;
            testArray[i] = r;
        }
        for (int i = 0; i < NUM_LOOP; i++) {
            // Rounding the result divided by 22
            testArray[i] = Math.round(testArray[i] / 22);
        }
        return (System.nanoTime() - startTime);
    }

    private long singleLoop() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_LOOP; i++) {
            // Some random Math operations
            int r = (i + 3 * i) & 0xFF;
            // Rounding the result divided by 22
            r = Math.round(r / 22);
            testArray[i] = r;
        }
        return (System.nanoTime() - startTime);
    }
}
