import java.util.concurrent.CountDownLatch;

public class ConcatThread extends Thread {
    CountDownLatch latch;
    private int starting_value;
    private int max;
    private ConcatResult result;

    public ConcatThread(int starting_value, int max, ConcatResult result, CountDownLatch latch) {
        this.starting_value = starting_value;
        this.max = max;
        this.result = result;
        this.latch = latch;
    }

    public void run() {
        int a = starting_value;
        int b = starting_value;
        int[] minimum_values = new int[]{0, 0};

        double minimum = 999999;

        while (a < max) {
            if (b >= max) {
                a++;
                b = starting_value;
            }

            long sum = a * b;

            double new_b = Math.floor(Math.log10(b));
            double new_a = (a * Math.pow(10, (new_b+1)));

            double concat = new_a + b;
            double difference = Math.abs(concat - sum);

            if (difference < minimum) {
                minimum = difference;
                minimum_values = new int[]{a, b};
            }
            b++;
        }

        if (minimum < result.minimum) {
            result.minimum = minimum;
            result.values = minimum_values;
        }

        if (latch != null) latch.countDown();
    }
}
