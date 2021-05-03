package app.parking.parkingSpaces;

import app.MyRandom;
import app.parking.cars.RandomCar;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public abstract class ParkingSpace extends StackPane {

    protected Text text;
    protected boolean free = true;
    private final double width;
    private final double height;
    private final String parkingColorHex = "#f1faee";
    private RandomCar currentCar = null;
    private final MyRandom myRandom;

    @Getter
    private double chooseProbability = 0;

    public ParkingSpace(String id, double width, double height) {
        this.width = width;
        this.height = height;
        this.myRandom = new MyRandom();
        this.initView(id);
    }

    private void initView(String name) {
        this.setPrefHeight(this.height);
        this.setPrefWidth(this.width);
        this.setStyle("-fx-background-color: " + parkingColorHex);
        text = new Text(name);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(text);
    }

    public void setChooseProbability(double chooseProbability) {
        this.chooseProbability = chooseProbability;
    }

    public String getParkingSpaceNumber() {
        return text.getText();
    }

    protected void setParkingColor(String colorHex) {
        this.setStyle("-fx-background-color: " + colorHex);
    }

    public void setCar(RandomCar randomCar) {
        randomCar.setParked(true);
        this.free = false;
        currentCar = randomCar;
        this.initTimerToLeaveParkingSPace(randomCar);

        Platform.runLater(() -> this.getChildren().add(randomCar));

    }

    public boolean isParkingSpaceFree() {
        return this.free;
    }

    private void initTimerToLeaveParkingSPace(RandomCar randomCar) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> removeCar(randomCar), randomCar.getTimeToLeaveParkingSpace(), TimeUnit.MILLISECONDS);
        executorService.shutdown();
    }

    protected void removeCar(RandomCar randomCar) {
        if (!free && currentCar != null) {
            randomCar.setParked(false);
            this.free = true;
            Platform.runLater(() -> this.getChildren().remove(currentCar));
        }
    }

    private DoubleBinding getCenterXBinding() {
        return this.layoutXProperty().add(this.getBoundsInParent().getWidth() / 2.0);
    }

    private DoubleBinding getCenterYBinding() {
        return this.layoutYProperty().add(this.getBoundsInParent().getWidth() / 2.0);
    }

}
