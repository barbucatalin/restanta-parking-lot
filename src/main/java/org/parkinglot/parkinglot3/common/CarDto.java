package org.parkinglot.parkinglot3.common;

public class CarDto {

    private final Long carId;
    private final String plateNum;
    private final String spotLabel;
    private final String ownerUser;

    // Constructor cu denumiri de parametri modificate
    public CarDto(Long id, String plate, String spot, String owner) {
        this.carId = id;
        this.plateNum = plate;
        this.spotLabel = spot;
        this.ownerUser = owner;
    }

    // Getters cu nume neschimbate pentru a păstra compatibilitatea cu JSP (${car.id}, ${car.licensePlate})
    public Long getId() {
        return this.carId;
    }

    public String getLicensePlate() {
        return this.plateNum;
    }

    public String getParkingSpot() {
        return this.spotLabel;
    }

    public String getOwnerName() {
        return this.ownerUser;
    }
}
