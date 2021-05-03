package app.logging;

import app.Main;
import app.parking.cars.RandomCar;
import app.utils.Helpers;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ilość noralnych kierowców
 * ilość niepełnosrawych kierowóców
 * procenty
 * kierowócw aktualnie i od początku smulacji
 * średni czas odjazdu
 * maksymlany i min czas odjazdu
 */
@Slf4j
public class LogPanel extends BorderPane {

    public static final double PANEL_WIDTH = 300;
    private static final double PADDING_SIZE = 10;
    private final List<RandomCar> cars = Collections.synchronizedList(new ArrayList<>());
    private final TextArea textArea;


    public LogPanel() {
        this.setPrefWidth(PANEL_WIDTH);
        textArea = new TextArea();
        this.setCenter(textArea);
        textArea.setEditable(false);
        textArea.setStyle("-fx-border-color: black");
        textArea.setStyle("-fx-border-style: none solid none solid");
        textArea.setStyle("-fx-padding: 1px");
        textArea.setPrefWidth(PANEL_WIDTH);
        textArea.setPrefHeight(Main.WINDOW_HEIGHT);
    }


    public void addCar(RandomCar car) {
        cars.add(car);
        Platform.runLater(this::updateStatistics);
    }

    private void updateStatistics() {

        textArea.clear();
        ArrayList<RandomCar> copy = new ArrayList<>(cars);
        textArea.appendText("----------From simulation start----------" + "\n");
        textArea.appendText(messageDrivers(copy));
        textArea.appendText(leaveParkingSPaceTime(copy));

        textArea.appendText("\n\n\n");

        List<RandomCar> parked = copy.stream().filter(RandomCar::isParked).collect(Collectors.toList());
        textArea.appendText("---------At the moment---------" + "\n");
        textArea.appendText(messageDrivers(parked));
        textArea.appendText(leaveParkingSPaceTime(parked));

    }

    private String messageDrivers(List<RandomCar> cars) {
        long numDisabled = cars.stream().filter(RandomCar::isDisabledDriver).count();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disabled drivers: ").append(numDisabled).append("\n");
        stringBuilder.append("Normal drivers: ").append(cars.size() - numDisabled).append("\n");
        stringBuilder.append("All drivers: ").append(cars.size()).append("\n");
        return stringBuilder.toString();
    }

    private String leaveParkingSPaceTime(List<RandomCar> cars) {
        long minMs = cars.stream().mapToLong(RandomCar::getTimeToLeaveParkingSpace).min().orElse(0);
        long maxMs = cars.stream().mapToLong(RandomCar::getTimeToLeaveParkingSpace).max().orElse(0);
        double avgMs = cars.stream().mapToLong(RandomCar::getTimeToLeaveParkingSpace).average().orElse(0);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Min departure time: ").append(minMs / 1000).append(" s\n");
        stringBuilder.append("Max departure time: ").append(maxMs / 1000).append(" s\n");
        stringBuilder.append("Average departure time: ").append(Helpers.round(avgMs / 1000, 2)).append(" s\n");
        return stringBuilder.toString();
    }

}
