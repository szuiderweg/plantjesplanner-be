package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "selected_plants")
public class SelectedPlant {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    private Double gardenMatchScore;


    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    //getters & setters
    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Double getGardenMatchScore() {
        return gardenMatchScore;
    }

    public void setGardenMatchScore(Double gardenMatchScore) {
        this.gardenMatchScore = gardenMatchScore;
    }
}
