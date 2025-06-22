package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import nl.novi.be_plantjesplanner.enumerations.Role;

import java.time.ZonedDateTime;
//TODO dit is een placeholder class om een Design aan te linken
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @Column(nullable = false)// for JBDC authentication
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(updatable = false, nullable = false)
    private ZonedDateTime creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="design_id")
    private Design2 design2;

    @PrePersist
    public void prePersist(){
        this.creationDate = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Design2 getDesign2() {
        return design2;
    }

    public void setDesign2(Design2 design2) {
        this.design2 = design2;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
