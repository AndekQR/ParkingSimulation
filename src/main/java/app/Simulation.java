package app;

import app.controls.ControlPanel;
import app.logging.LogPanel;
import app.parking.Parking;
import app.parking.cars.RandomCar;
import app.parking.parkingSpaces.DisabledParkingSpace;
import app.parking.parkingSpaces.ParkingSpace;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Simulation {

    private final LogPanel logPanel;
    private final ControlPanel controlPanel;
    private final Parking parking;

    private final List<ParkingSpace> parkingSpaceList;

    private final MyRandom myRandom;

    public Simulation() {
        this.logPanel = new LogPanel();
        this.controlPanel = new ControlPanel();
        this.parking = new Parking();

        this.parkingSpaceList = parking.getParkingSpaces();
        this.myRandom = new MyRandom();
    }

    public HBox getView() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        vBox.getChildren().addAll(parking);
        hBox.getChildren().addAll(vBox, logPanel);
        return hBox;
    }

    private RandomCar getRandomCar() {
        return new RandomCar(parking.getParkingSpaceWidth() * 0.38, parking.getParkingSpaceHeight() * 0.38);
    }

    /**
     * losowanie samochodu czy zwykły czy mające karte na miejsce niepełnosprawne
     * dodanie budynku z lewej strony i liczenie odległości miejsca prakingowego od niego żeby policzyć
     * prawdopodibeństwo zaprakowania
     * średni, minimalny, maksymalny czas postoju auta na parkingu
     */
    public void startSimulation() {
        initParkingSpacesProbabilities();
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::parkCar, 0, myRandom.getRandomNumber(1, 3) * 100L,
                TimeUnit.MILLISECONDS);
//        scheduledThreadPoolExecutor.shutdown();
    }

    //https://math.stackexchange.com/questions/3312609/percentage-probability-based-on-distance-high-percentage-for-low-value
    private void initParkingSpacesProbabilities() {
        Node building = parking.getBuilding();
        Bounds bounds = building.localToScene(building.getBoundsInLocal());
        Pair<Double, Double> buildingCenter = new Pair<>(bounds.getMaxX() / 2, bounds.getMaxY() / 2);

        //miejsce parkingowe, odwrócona odległość od budynku
        Map<ParkingSpace, Double> map =
                parkingSpaceList.stream().collect(Collectors.toMap(Function.identity(),
                        parkingSpace -> {
                            Bounds parkingSpaceBounds = parkingSpace.localToScene(parkingSpace.getBoundsInLocal());
                            Pair<Double, Double> parkingSpaceCenter =
                                    new Pair<>(parkingSpaceBounds.getMinX() + (parkingSpaceBounds.getWidth() / 2),
                                            parkingSpaceBounds.getMinY() + (parkingSpaceBounds.getHeight() / 2));
                            return 1 / calculateDistance(buildingCenter, parkingSpaceCenter);
                        }));
        double sumDistance = map.values().stream().reduce(0D, Double::sum);

        map.forEach((parkingSpace, distance) -> {
            double probability = distance / sumDistance;
            parkingSpace.setChooseProbability(probability);
        });
    }


    private Double calculateDistance(Pair<Double, Double> from, Pair<Double, Double> to) {
        return Math.sqrt(Math.pow(to.getValue() - from.getValue(), 2) + Math.pow(to.getKey() - from.getKey(), 2));
    }

    //https://stackoverflow.com/questions/9330394/how-to-pick-an-item-by-its-probability
    private ParkingSpace getRandomParkingSpace() {
        double randomNumber = myRandom.getRandomNumber();
        double cumulativeProbability = 0.0;
        for (ParkingSpace parkingSpace : parkingSpaceList) {
            cumulativeProbability += parkingSpace.getChooseProbability();
            if (randomNumber <= cumulativeProbability) return parkingSpace;
        }
        return null;
    }

    private void parkCar() {
        ParkingSpace randomParkingSpace = getRandomParkingSpace();
        RandomCar randomCar = getRandomCar();
        if (randomParkingSpace != null && randomParkingSpace.isParkingSpaceFree()) {
            if (!randomCar.isDisabledDriver() && randomParkingSpace instanceof DisabledParkingSpace) {
                //zwykli kierowcy nie mogę parkować na miejscu dla niepełnosprawnych
                return;
            } else {
                String message = randomCar.isDisabledDriver() ? "Disabled " : "Normal " + "driver parked in " +
                        "parking place nr. " + randomParkingSpace.getParkingSpaceNumber();
//                logPanel.addMessage(message);
                logPanel.addCar(randomCar);
                randomParkingSpace.setCar(randomCar);
            }
        }
    }
}
