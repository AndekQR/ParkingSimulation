package app.parking.cars;

import app.MyRandom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

public class RandomCar extends ImageView {

    private final static double disabledDriverProbability = 0.05;

    private final MyRandom random;

    @Getter
    private final boolean disabledDriver;

    @Getter
    private final long timeToLeaveParkingSpace;

    @Getter
    @Setter
    private boolean parked = false;

    public RandomCar(double width, double height) {
        this.random = new MyRandom();
        boolean disabledPersonRandom = isDisabledPersonRandom();
        disabledDriver = disabledPersonRandom;
        timeToLeaveParkingSpace = randomTimeLeaveParkingSpace();
        this.setImage(getCarImage(disabledPersonRandom));
        this.setRotate(90);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    private Image getCarImage(boolean disabledDriver) {
        if (disabledDriver) return new Image(getClass().getResource("../../../disabledCar.png").toString());
        else return new Image(getClass().getResource("../../../car.png").toString());
    }

    private boolean isDisabledPersonRandom() {
        double randomNumber = random.getRandomNumber();
        return randomNumber <= disabledDriverProbability;
    }

    private long randomTimeLeaveParkingSpace() {
        return random.getRandomNumber(5, 20) * 1000L;
    }
}
