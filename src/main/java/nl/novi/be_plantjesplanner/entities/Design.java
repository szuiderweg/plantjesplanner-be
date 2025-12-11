package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "design")
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "username", nullable = false) //in the database this column contains the username as foreign key to the user table. The relation between a User and a Design is maintained by a database constraint on the Design table instead of a JPA mapping. see the file "schema.sql" for details  todo: dit is wel een design keuze. Ik doe het zo om op deze manier het beheren van users+ authorities helemaal over te laten aan JDBC.
    private String username;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }//get setter voor username omdat het een foreign key van de user tabel is
}
