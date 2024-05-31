package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    public PlantService(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }

    //corresponds to postPlant request in the PlantController
    public Plant savePlant(Plant plant)
    {
        return plantRepository.save(plant);
    }

    //corresponds to GET all Plants request in the PlantController
    public List<Plant> getAllPlants()
    {
        return plantRepository.findAll();
    }

}
