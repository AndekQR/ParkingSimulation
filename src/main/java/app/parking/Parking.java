package app.parking;

import app.Main;
import app.parking.parkingSpaces.DisabledParkingSpace;
import app.parking.parkingSpaces.NormalParkingSpace;
import app.parking.parkingSpaces.ParkingSpace;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Parking extends HBox {


    public static final double BUILDING_WIDTH = 50;

    private final Pane building;
    private final ParkingTemplate parkingTemplate;

    public Parking() {
        this.building = this.getBuilding();
        this.parkingTemplate = new ParkingTemplate(9, 20);
        initParkingTemplate();
        this.getChildren().addAll(building, parkingTemplate);
    }

    private void initParkingTemplate() {
        for (int i = 0; i < parkingTemplate.getNodesInHeight(); i++) {
            parkingTemplate.setRowOf(NormalParkingSpace.class, i);
        }

        parkingTemplate.setRowOf(StraightRoad.class, 1);
        parkingTemplate.setRowOf(StraightRoad.class, 4);
        parkingTemplate.setRowOf(StraightRoad.class, 7);
        parkingTemplate.setColumnOf(StraightRoad.class, 0);

        parkingTemplate.setCellOf(DisabledParkingSpace.class, 0, 0);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 0);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 2);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 3);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 5);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 6);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 0, 8);
        parkingTemplate.setCellOf(DisabledParkingSpace.class, 1, 8);
    }

    private Pane getBuilding() {
        Pane pane = new Pane();
        pane.setPrefHeight(Main.WINDOW_HEIGHT);
        pane.setPrefWidth(BUILDING_WIDTH);
        pane.setStyle("-fx-background-color: yellow");
        return pane;
    }

    public double getParkingSpaceWidth() {
        return parkingTemplate.getParkingSpaceWidth();
    }

    public double getParkingSpaceHeight() {
        return parkingTemplate.getParkingSpaceHeight();
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingTemplate.getAddedElements().stream()
                .flatMap(Collection::stream)
                .filter(node -> (node instanceof NormalParkingSpace) || (node instanceof DisabledParkingSpace))
                .map(node -> (ParkingSpace) node)
                .collect(Collectors.toList());
    }
}
