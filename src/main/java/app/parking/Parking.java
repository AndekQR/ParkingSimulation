package app.parking;

import app.Main;
import app.controls.ControlPanel;
import app.logging.LogPanel;
import app.parking.parkingSpaces.DisabledParkingSpace;
import app.parking.parkingSpaces.NormalParkingSpace;
import app.parking.parkingSpaces.ParkingSpace;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Parking extends GridPane {

    private static final double SPACES_IN_ROW = 10;
    private static final double ROWS = 6;
    private static final double GAP_SIZE = 5;
    private static final double PADDING_SIZE = 10;

    @Getter
    private final List<ParkingSpace> parkingSpaces;

    public Parking() {
        this.setupView();
        parkingSpaces = this.generateParkingSPaces();
//        this.addParkingSpacesToView();

    }


    private void addParkingSpacesToView() {
        int parkingSpaceIndex = 0;
        for (int i = 0; i < SPACES_IN_ROW; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (j == 1 || j == 4) { //road
                    StraightRoad.addToParent(this, i, j, this.getParkingSpaceWidth(),
                            this.getParkingSpaceHeight());
                } else {
                    this.add(this.parkingSpaces.get(parkingSpaceIndex), i, j);
                    parkingSpaceIndex++;

                }
            }
        }
    }

    private void setupView() {
        this.setVgap(GAP_SIZE);
        this.setHgap(GAP_SIZE);
        this.setStyle("-fx-background-color: #6eeb83");
        this.setPadding(new Insets(PADDING_SIZE));

    }

    public Double getParkingSpaceWidth() {
        return (Main.WINDOW_WIDTH - LogPanel.PANEL_WIDTH) / SPACES_IN_ROW - GAP_SIZE - (2 * PADDING_SIZE / SPACES_IN_ROW);
    }

    public Double getParkingSpaceHeight() {
        return (Main.WINDOW_HEIGHT - ControlPanel.HEIGHT) / ROWS - GAP_SIZE - (2 * PADDING_SIZE / SPACES_IN_ROW);
    }

    private List<ParkingSpace> generateParkingSPaces() {
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        for (int i = 0; i < 34; i++) {
            ParkingSpace parkingSpace = new NormalParkingSpace(String.valueOf(i), this.getParkingSpaceWidth(),
                    this.getParkingSpaceHeight());
            parkingSpaces.add(parkingSpace);
        }

        for (int i = 0; i < 6; i++) {
            ParkingSpace disabledParkingSpace = new DisabledParkingSpace(this.getParkingSpaceWidth(),
                    this.getParkingSpaceHeight(),
                    i + "D");
            parkingSpaces.add(disabledParkingSpace);

        }
        return parkingSpaces;
    }

    private Node getBuilding() {
        Pane pane = new Pane();
        pane.setPrefHeight(Main.WINDOW_HEIGHT);
        pane.setPrefWidth(50);
        pane.setStyle("-fx-border-style: solid");
        return pane;
    }

}
