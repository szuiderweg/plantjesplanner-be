package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;

import java.util.DuplicateFormatFlagsException;
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

    //helper methods selectedPlants

    //add plant to design
    public void addPlantToSelectedPlants(Plant plant, int amount){
       //check if plant is already present in this designs SelectedPlants set
        for(SelectedPlant selectedPlant : selectedPlants){
            if(selectedPlant.getPlant().equals(plant)){
                throw new DuplicateResourceException("Deze plant heb je al");
            }
        }
        //otherwise add a new SelectedPlant to the design
        SelectedPlant sp = new SelectedPlant();
        sp.setDesign(this);
        sp.setPlant(plant);
        sp.setAmount(amount);
        selectedPlants.add(sp);
    }

    //edit amount of a selectedplant
    public void updateAmountSelectedPlants(Long selectedPlantId, int newAmount){
        if(newAmount <= 0){
            throw new IllegalArgumentException("aantal moet 1 of meer zijn");
        }
        for (SelectedPlant sp : selectedPlants) {
            if (sp.getId().equals(selectedPlantId)) {
                sp.setAmount(newAmount);
                return;
            }
        }
        //if plant is not found, throw exception
        throw new RecordNotFoundException("gekozen plant met id " + selectedPlantId + " niet gevonden");
    }

    //remove selectedPlant from design
    public void removeSelectedPlant(Long selectedPlantId){

        boolean removed = selectedPlants.removeIf(sp -> sp.getId().equals(selectedPlantId));

        if (!removed) {
            throw new RecordNotFoundException( "SelectedPlant met id " + selectedPlantId + " niet gevonden");
        }
    }

}
