package app.parking.parkingSpaces;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class DisabledParkingSpace extends ParkingSpace {

    private final String parkingColor="#0077b6";

    public DisabledParkingSpace(double width, double height, String id) {
        super(id, width, height);

        this.setParkingColor(parkingColor);
    }

}
