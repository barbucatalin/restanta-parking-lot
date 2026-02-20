package example.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "licensePlate")
    private String plateNumber; // Am schimbat din licensePlate în plateNumber

    @Column(name = "parkingSpot")
    private String spotLocation; // Am schimbat din parkingSpot în spotLocation

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User carOwner; // Am schimbat din owner în carOwner

    // --- Constructori ---
    public Car() {
    }

    // --- Getters & Setters ---

    public Long getId() {
        return this.id;
    }

    public String getLicensePlate() {
        return plateNumber;
    }

    public void setLicensePlate(String licensePlate) {
        this.plateNumber = licensePlate;
    }

    public String getParkingSpot() {
        return spotLocation;
    }

    public void setParkingSpot(String parkingSpot) {
        this.spotLocation = parkingSpot;
    }

    public User getOwner() {
        return carOwner;
    }

    public void setOwner(User owner) {
        this.carOwner = owner;
    }
}
