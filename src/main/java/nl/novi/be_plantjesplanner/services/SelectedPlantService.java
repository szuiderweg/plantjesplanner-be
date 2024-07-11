package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.repositories.SelectedPlantRepository;
import org.springframework.stereotype.Service;

@Service
public class SelectedPlantService {
    private final SelectedPlantRepository selectedPlantRepository;
    public SelectedPlantService(SelectedPlantRepository selectedPlantRepository){
        this.selectedPlantRepository = selectedPlantRepository;
    }


}
