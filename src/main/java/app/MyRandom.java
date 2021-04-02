package app;

import java.util.Random;

public class MyRandom {

    private Random random;

    public MyRandom() {
        this.random = new Random();
    }

    public long getTimeToLeaveParkingSpace() {
        return getRandomNumber(5,20) * 1000L;
    }

    public int getRandomNumber(int from, int to) {
        return random.nextInt(to - from) + from;
    }
}
