package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "designs")
public class Design{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //private List<selectedPlant> selectedPlants
    //moodboard
    //myGarden
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
