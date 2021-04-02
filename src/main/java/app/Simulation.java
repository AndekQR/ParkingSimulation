package app;

import app.controls.ControlPanel;
import app.logging.LogPanel;
import app.parking.Parking;
import app.parking.parkingSpaces.ParkingSpace;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private final LogPanel logPanel;
    private final ControlPanel controlPanel;
    private final Parking parking;

    private final List<ParkingSpace> parkingSpaceList;

    private Image carImage = null;
    private MyRandom myRandom;


    public Simulation() {
        this.logPanel = new LogPanel();
        this.controlPanel = new ControlPanel();
        this.parking = new Parking();

        this.parkingSpaceList = parking.getParkingSpaces();
        this.myRandom = new MyRandom();
    }

    public BorderPane getView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(parking);
        borderPane.setBottom(controlPanel);
        borderPane.setRight(logPanel);
        return borderPane;
    }

    private Image getCarImage() {
        return new Image(getClass().getResourceAsStream("../car.png"));
    }

    private ImageView getCarImageView() {
        if (this.carImage == null) {
            this.carImage = this.getCarImage();
        }
        ImageView imageView = new ImageView(this.carImage);
        imageView.setRotate(90);
        imageView.setFitWidth(parking.getParkingSpaceWidth() * 0.38);
        imageView.setFitHeight(parking.getParkingSpaceHeight() * 0.38);
        return imageView;
    }

    /**
     * losowanie samochodu czy zwykły czy mające karte na miejsce niepełnosprawne
     * dodanie budynku z lewej strony i liczenie odległości miejsca prakingowego od niego żeby policzyć
     * prawdopodibeństwo zaprakowania
     *średni, minimalny, maksymalny czas postoju auta na parkingu
     *
     */
    public void startSimulation() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            ImageView carImageView = getCarImageView();
            int randomNumber = myRandom.getRandomNumber(0, parkingSpaceList.size() - 1);
            ParkingSpace parkingSpace = parkingSpaceList.get(randomNumber);
            if (parkingSpace.isParkingSpaceFree()) parkingSpace.setCar(carImageView);
        }, 0, myRandom.getRandomNumber(1, 4) * 100L, TimeUnit.MILLISECONDS);
    }

    private void parkCar() {

    }
}
