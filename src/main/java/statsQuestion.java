import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class statsQuestion {

    private static final int NUM_TRIALS = 100_000_0;

    public static void main(String[] args) {
        BigDecimal sum = new BigDecimal("0");

        for (int i = 0; i < NUM_TRIALS; i++) {
            sum = sum.add(new BigDecimal(Integer.toString(getNumThrows())));
        }

        System.out.printf("Average is: %2.4f", sum.divide(new BigDecimal(Integer.toString(NUM_TRIALS)), RoundingMode.HALF_UP).doubleValue());
    }



    private static int[] initThrowCountList(int size) {
        int[] fillerList = new int[size];
        for(int i = 0; i < size; i++) {
            fillerList[i] = 0;
        }
        return fillerList;
    }

    private static int getNumThrows() {
        int numThrows = 0;
        while(true) {
            numThrows++;
            double probable = Math.random();
            if (probable <= 0.2) {
                break;
            }
        }
        return numThrows;
    }

}
