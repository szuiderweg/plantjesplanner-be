package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.SelectedPlant;
import nl.novi.be_plantjesplanner.repositories.SelectedPlantRepository;
import org.springframework.stereotype.Service;

@Service
public class SelectedPlantService {
    private final SelectedPlantRepository selectedPlantRepository;
    public SelectedPlantService(SelectedPlantRepository selectedPlantRepository){
        this.selectedPlantRepository = selectedPlantRepository;
    }

    //corresponds to Post request of a selectedplant in the selectedplant controller
    //todo implement DTO
    public SelectedPlant saveSelectedPlant(SelectedPlant sp){
        return selectedPlantRepository.save(sp);
    }
    //todo mapToDTO and mapFromDTO methods
    //DTO mappers


}
