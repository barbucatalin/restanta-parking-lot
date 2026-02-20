package example.parkinglot.entities;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    // Folosim mappedBy "carOwner" pentru a se potrivi cu modificarea facuta in clasa Car precedenta
    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> vehicles = new LinkedList<>();

    public User() {
        // Constructor explicit
    }

    // --- Identitate ---
    public Long getId() {
        return this.id;
    }

    // --- Informatii Utilizator ---
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- Relatie Vehicule ---
    public List<Car> getCars() {
        return (List<Car>) this.vehicles;
    }

    public void setCars(List<Car> cars) {
        if (cars != null) {
            this.vehicles = new LinkedList<>(cars);
        }
    }
}
