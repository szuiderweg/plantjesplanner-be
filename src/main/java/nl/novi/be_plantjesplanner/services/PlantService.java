package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
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

    public Plant updatePlantById(Plant plant , Long id){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant foundPlant = plantOptional.get();
            //update all fieldvalues in foundplant with values from  new plant
            foundPlant.setDutchName(plant.getDutchName());
            foundPlant.setLatinName(plant.getLatinName());
            foundPlant.setPlantDescription(plant.getPlantDescription());
            foundPlant.setWaterPreference(plant.getWaterPreference());
            foundPlant.setSunPreference(plant.getSunPreference());
            foundPlant.setWindTolerance(plant.getWindTolerance());
            foundPlant.setBloomColour(plant.getBloomColour());
            foundPlant.setHeight(plant.getHeight());
            foundPlant.setFootprint(plant.getFootprint());
            foundPlant.setPottedPlant(plant.getPottedPlant());
            foundPlant.setSoilPreference(plant.getSoilPreference());
            //todo put additional fields here
            return plantRepository.save(foundPlant);
        }
        else{
            throw new RecordNotFoundException("geen plant gevonden met id "+ id+" , dus ook niet aangepast");
        }
    }

    public void deletePlantById(Long id){
        plantRepository.deleteById(id);
    }

    public Plant getPlantById(Long id){
      Optional<Plant> plantOptional = plantRepository.findById(id);
      if(plantOptional.isPresent()){
          return plantOptional.get();
      }
      else{
          throw new RecordNotFoundException("geen plant gevonden met id "+ id);
      }
    }

    //corresponds to GET all Plants request in the PlantController
    public List<Plant> getAllPlants()
    {
        return plantRepository.findAll();
    }

}
