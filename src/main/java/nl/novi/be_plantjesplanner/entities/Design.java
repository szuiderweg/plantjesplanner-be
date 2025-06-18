package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "designs")
public class Design{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "design")
    private User user;

    @OneToOne
    @JoinColumn(name = "locale_id")
    private Locale gardenInfo;

    private String gardenName;
    private Double plantedArea;
    private Integer totalAmountOfPlants;
    private Double plantMeterScore;

    @OneToMany(mappedBy= "design", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectedPlant> selectedPlants;

    @OneToMany(mappedBy= "design", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoodboardItem> moodboard;
//    private List<BloomingCalendar> combinedBloomingCalendar;//todo dit implementeren. deze lijst wordt niet gepersist , maar na het ophalen van de selected plants van een ontwerp wordt deze lijst samengesteld
    //
    //TODO: een aantal properties (user, de lists, gardeninfo) moeten mogelijk geen setter hebben omdat de listitems op een andere plek worden gemaakt of dat de waarde final is.

    //getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Locale getGardenInfo() {
        return gardenInfo;
    }

    public void setGardenInfo(Locale gardenInfo) {
        this.gardenInfo = gardenInfo;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public Double getPlantedArea() {
        return plantedArea;
    }

    public void setPlantedArea(Double plantedArea) {
        this.plantedArea = plantedArea;
    }

    public Integer getTotalAmountOfPlants() {
        return totalAmountOfPlants;
    }

    public void setTotalAmountOfPlants(Integer totalAmountOfPlants) {
        this.totalAmountOfPlants = totalAmountOfPlants;
    }

    public Double getPlantMeterScore() {
        return plantMeterScore;
    }

    public void setPlantMeterScore(Double plantMeterScore) {
        this.plantMeterScore = plantMeterScore;
    }

    public List<SelectedPlant> getSelectedPlants() {
        return selectedPlants;
    }

    public void setSelectedPlants(List<SelectedPlant> selectedPlants) {
        this.selectedPlants = selectedPlants;
    }

    public List<MoodboardItem> getMoodboard() {
        return moodboard;
    }

    public void setMoodboard(List<MoodboardItem> moodboard) {
        this.moodboard = moodboard;
    }
}
