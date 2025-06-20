package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
//TODO dit is een placeholder class om een Design aan te linken
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private final ZonedDateTime zonedDateTime;
    @OneToOne
    @JoinColumn(name ="design_id")
    private Design design;

    public User(){
        this.zonedDateTime = ZonedDateTime.now();
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.zonedDateTime = ZonedDateTime.now();
        this.design = new Design();
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

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
