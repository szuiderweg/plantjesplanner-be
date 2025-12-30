package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "selected_plants", uniqueConstraints = {@UniqueConstraint(columnNames = {"design_id", "plant_id"})})
public class SelectedPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "design_id", nullable = false)
    private Design design;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }
}
