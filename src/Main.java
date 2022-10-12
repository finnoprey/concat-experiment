import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        int max_value = 10000000;
        int threads = max_value/100;

        ConcatResult result = new ConcatResult();
        result.minimum = 9999999;

        if (((double) max_value /threads) % 1 != 0) {
            System.out.println("Please ensure values can be evenly split across the specified number of threads.");
            return;
        }

        int portion = max_value / threads;
        long start_time = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new ConcatThread(((i + 1) * (portion) - portion) + 1, portion * (i + 1), result, latch));
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("Error waiting for all threads to resolve.");
        }

        double time_elapsed = (System.nanoTime() - start_time) / 1000000000D;
        System.out.println("Checked: " + max_value);
        System.out.println("Minimum: " + result.minimum);
        System.out.println("Values: " + result.values[0] + ", " + result.values[1]);
        System.out.println("Threads: " + threads);
        System.out.printf("Time: %.3fs %n", time_elapsed);

    }
}