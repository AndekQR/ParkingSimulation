package app.parking.parkingSpaces;

import app.MyRandom;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public abstract class ParkingSpace extends StackPane {

    protected String id;
    protected boolean free = true;
    private double width;
    private double height;
    private String parkingColorHex = "#f1faee";
    private ImageView currentCarImage = null;
    private MyRandom myRandom;

    public ParkingSpace(String id, double width, double height) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.myRandom = new MyRandom();
        this.initView();
    }

    private void initView() {
        this.setPrefHeight(this.height);
        this.setPrefWidth(this.width);
        this.setStyle("-fx-background-color: " + parkingColorHex);
        Text text = new Text(id);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(text);
    }

    protected void setParkingColor(String colorHex) {
        this.setStyle("-fx-background-color: " + colorHex);
    }

    public void setCar(ImageView imageView) {
        this.free = false;
        currentCarImage = imageView;
        this.initTimerToLeaveParkingSPace();

        Platform.runLater(() -> this.getChildren().add(imageView));

    }

    public boolean isParkingSpaceFree() {
        return this.free;
    }

    private void initTimerToLeaveParkingSPace() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::removeCar, myRandom.getTimeToLeaveParkingSpace(), TimeUnit.MILLISECONDS);
        executorService.shutdown();
    }

    protected void removeCar() {
        if (!free && currentCarImage != null) {
            this.free = true;
            Platform.runLater(() -> this.getChildren().remove(currentCarImage));
        }
    }

}
