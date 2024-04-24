package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import org.springframework.stereotype.Service;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    public PlantService(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }

    //
    public Plant savePlant(Plant plant)
    {
        System.out.println("ho stop!");
        return plantRepository.save(plant);
    }
}
