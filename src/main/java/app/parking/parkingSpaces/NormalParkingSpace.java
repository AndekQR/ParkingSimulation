package app.parking.parkingSpaces;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class NormalParkingSpace extends ParkingSpace {

    private final String parkingColor="#f1faee";

    public NormalParkingSpace(String id, double width, double height) {
        super(id, width, height);
        this.setParkingColor(parkingColor);
    }
}
