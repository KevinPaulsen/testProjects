public class BitShiftDivideTest {

    public static final int NUM_TEST = Integer.MAX_VALUE;

    public static void main(String[] args) {
        new BitShiftDivideTest();
    }

    private BitShiftDivideTest() {
        divdieTest();
        divdieTest();
        divdieTest();
        divdieTest();


        System.out.println("Multiply: " + divdieTest());
        System.out.println("Bit Shift: " + bitShiftTest());
        System.out.println("Difference: " + (divdieTest() - bitShiftTest()));
    }

    private long bitShiftTest() {
        int sum = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_TEST; i++) {
            sum += i << 4;
        }
        return System.nanoTime() - startTime;
    }

    private long divdieTest() {
        int sum = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_TEST; i++) {
            sum += i * 16;
        }
        return System.nanoTime() - startTime;
    }
}

