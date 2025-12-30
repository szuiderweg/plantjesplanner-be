package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "designs")
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double gardenSize; //in square meters m2

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "locale_id", unique = true)
    private Locale locale;

    @OneToMany(mappedBy = "design",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SelectedPlant> selectedPlants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGardenSize() {
        return gardenSize;
    }

    public void setGardenSize(double gardenSize) {
        this.gardenSize = gardenSize;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Set<SelectedPlant> getSelectedPlants() {
        return selectedPlants;
    }

    public void setSelectedPlants(Set<SelectedPlant> selectedPlants) {
        this.selectedPlants = selectedPlants;
    }
}
