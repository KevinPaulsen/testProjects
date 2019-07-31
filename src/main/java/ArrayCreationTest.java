import java.util.ArrayList;

public class ArrayCreationTest {

    private static final long NUM_TRIALS = 1000_000;

    public static void main(String[] args) {

        long timeForNew = timeForNewArr();
        long timeForSame = timeForSameArr();

        timeForNew = timeForNewArr();
        timeForSame = timeForSameArr();

        System.out.printf("Time for  new: %10dms\n", timeForNew);
        System.out.printf("Time for same: %10dms\n\n", timeForSame);

        System.out.printf("Time for new is %3.2f%s the speed of time for same.\n",
                100.0 * (((double) timeForNew) / ((double) timeForSame)), "%");
        System.out.printf("Time for same is %3.2f%s the speed of time for new.\n",
                100.0 * (((double) timeForSame) / ((double) timeForNew)), "%");
        for (int i = 0; i < 20; i++) {
            System.out.print("-");
        }
        System.out.println();
        double speedOfNew = Math.abs(100 - (100 * (((double) timeForNew) / ((double) timeForSame))));
        if (timeForNew < timeForSame) {
            System.out.printf("Creating new Arrays was %3.2f%s faster than reusing arrays.", speedOfNew, "%");
        } else {
            System.out.printf("Creating new Arrays was %3.2f%s slower than reusing arrays.", speedOfNew, "%");
        }//*/
    }

    private static long timeForNewArr() {
        long startTime = System.currentTimeMillis();
        double ans = 0;
        for (int num = 0; num < NUM_TRIALS; num++) {
            double[] arr = new double[100];
            setValues(arr);
            ans = (ans + arr[33]) % 234234;
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long timeForSameArr() {
        long startTime = System.currentTimeMillis();
        double ans = 0;
        double[] arr = new double[100];
        for (int num = 0; num < NUM_TRIALS; num++) {
            setValues(arr);
            ans = (ans + arr[33]) % 234234;
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long timeForNew() {
        long startTime = System.currentTimeMillis();
        for (int num = 0; num < NUM_TRIALS; num++) {
            ArrayList<Double> list = newObjects();
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long timeForSame() {
        ArrayList<Double> sameList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            sameList.add(0.0);
        }
        long startTime = System.currentTimeMillis();
        for (int num = 0; num < NUM_TRIALS; num++) {
            sameObject(sameList);
        }
        return System.currentTimeMillis() - startTime;
    }

    private static ArrayList<Double> newObjects() {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(Math.random());
        }
        return list;
    }

    private static void sameObject(ArrayList<Double> list) {
        for (int i = 0; i < 100; i++) {
            list.set(i, Math.random());
        }
    }

    private static void setValues(double[] list) {
        for (int i = 0; i < 100; i++) {
            list[i] = Math.random();
        }
    }
}
