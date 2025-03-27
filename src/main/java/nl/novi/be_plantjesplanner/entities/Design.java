package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "designs")
public class Design{
    @Id
    @GeneratedValue
    private Long id;

//    private User user;
    private String name;
//    private GardenInfo gardenInfo;
    private Double plantedArea;
    private Integer totalAmountOfPlants;
    private Double plantMeterScore;
//    private List<SelectedPlant> selectedPlants;
//    private List<MoodboardItem> moodboard;
//    private List<BloomingMonths> bloomingCalendar;

    //TODO: een aantal properties (user, de lists, gardeninfo) moeten mogelijk geen setter hebben omdat de listitems op een andere plek worden gemaakt of dat de waarde final is.
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public GardenInfo getGardenInfo() {
//        return gardenInfo;
//    }
//
//    public void setGardenInfo(GardenInfo gardenInfo) {
//        this.gardenInfo = gardenInfo;
//    }

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

//    public List<SelectedPlant> getSelectedPlants() {
//        return selectedPlants;
//    }
//
//    public void setSelectedPlants(List<SelectedPlant> selectedPlants) {
//        this.selectedPlants = selectedPlants;
//    }
//
//    public List<MoodboardItem> getMoodboard() {
//        return moodboard;
//    }
//
//    public void setMoodboard(List<MoodboardItem> moodboard) {
//        this.moodboard = moodboard;
//    }
//
//    public List<BloomingMonths> getBloomingCalendar() {
//        return bloomingCalendar;
//    }
//
//    public void setBloomingCalendar(List<BloomingMonths> bloomingCalendar) {
//        this.bloomingCalendar = bloomingCalendar;
//    }
}
