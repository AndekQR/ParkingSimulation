package app;

import java.util.SplittableRandom;

public class MyRandom {

    private final SplittableRandom random;

    public MyRandom() {
        this.random = new SplittableRandom();
    }


    public int getRandomNumber(int from, int to) {
        return random.nextInt(from, to);
    }

    public double getRandomNumber() {
        return random.nextDouble();
    }
}
