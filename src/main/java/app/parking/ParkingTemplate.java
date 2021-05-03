package app.parking;

import app.Main;
import app.logging.LogPanel;
import app.parking.parkingSpaces.DisabledParkingSpace;
import app.parking.parkingSpaces.NormalParkingSpace;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class ParkingTemplate extends GridPane {

    public static final double GAP_SIZE = 5;
    public static final double PADDING_SIZE = 10;

    private final int nodesInRow;
    private final int nodesInHeight;

    private final List<List<Node>> addedElements;

    public ParkingTemplate(int nodesInHeight, int nodesInWidth) {
        this.nodesInHeight = nodesInHeight;
        this.nodesInRow = nodesInWidth;
        this.addedElements = new ArrayList<>(nodesInRow);
        for (int i = 0; i < nodesInRow; i++) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (int i1 = 0; i1 < nodesInHeight; i1++) {
                nodes.add(null);
            }
            this.addedElements.add(nodes);

        }

        this.setVgap(GAP_SIZE);
        this.setHgap(GAP_SIZE);
        this.setStyle("-fx-background-color: #6eeb83");
        this.setPadding(new Insets(PADDING_SIZE));
        this.setPrefWidth(Main.WINDOW_WIDTH - LogPanel.PANEL_WIDTH - Parking.BUILDING_WIDTH);
    }

    public void setRowOf(Class<?> elementType, int row) {
        for (int i = 0; i < nodesInRow; i++) {
            try {
                Node element = getElement(i + "_" + row, elementType);
                setOrReplaceChild(element, i, row);
            } catch (IllegalArgumentException exception) {
                log.error(exception.getLocalizedMessage());
            }
        }
    }

    public void setColumnOf(Class<?> type, int column) {
        for (int i = 0; i < nodesInHeight; i++) {
            try {
                Node element = getElement(column + i + "", type);
                setOrReplaceChild(element, column, i);
            } catch (IllegalArgumentException exception) {
                log.error(exception.getLocalizedMessage());
            }
        }
    }

    public void setCellOf(Class<?> type, int x, int y) {
        try {
            Node element = getElement(x + "_" + y, type);
            setOrReplaceChild(element, x, y);
        } catch (IllegalArgumentException exception) {
            log.error(exception.getLocalizedMessage());
        }
    }

    private void setOrReplaceChild(Node element, int x, int y) {
        Node node = this.addedElements.get(x).get(y);
        if (node != null) {
            this.getChildren().remove(node);
        }
        this.addedElements.get(x).set(y, element);
        this.add(element, x, y);
    }

    private Node getElement(String name, Class<?> type) throws IllegalArgumentException {
        if (type == NormalParkingSpace.class) {
            return new NormalParkingSpace(name, getParkingSpaceWidth(), getParkingSpaceHeight());
        } else if (type == DisabledParkingSpace.class) {
            return new DisabledParkingSpace(getParkingSpaceWidth(), getParkingSpaceHeight(), name);
        } else if (type == StraightRoad.class) {
            return new StraightRoad(getParkingSpaceWidth(), getParkingSpaceHeight());
        }
        throw new IllegalArgumentException("Bad type");
    }

    public Double getParkingSpaceWidth() {
        return (Main.WINDOW_WIDTH - LogPanel.PANEL_WIDTH - Parking.BUILDING_WIDTH) / nodesInHeight - GAP_SIZE - (2 * PADDING_SIZE / nodesInRow);
    }

    public Double getParkingSpaceHeight() {
        return (Main.WINDOW_HEIGHT) / nodesInHeight - GAP_SIZE - (2 * PADDING_SIZE / nodesInRow);
    }
}
