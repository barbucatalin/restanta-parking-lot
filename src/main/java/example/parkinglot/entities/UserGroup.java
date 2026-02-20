package example.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usergroups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100)
    private String memberName; // Schimbat din username în memberName

    @Column(name = "userGroup")
    private String groupType; // Schimbat din userGroup în groupType

    // Constructor implicit necesar pentru JPA
    public UserGroup() {
    }

    public Long getId() {
        return id;
    }

    // Păstrăm numele metodelor originale pentru a nu strica restul aplicației
    public String getUsername() {
        return this.memberName;
    }

    public void setUsername(String username) {
        this.memberName = username;
    }

    public String getUserGroup() {
        return this.groupType;
    }

    public void setUserGroup(String userGroup) {
        this.groupType = userGroup;
    }
}

