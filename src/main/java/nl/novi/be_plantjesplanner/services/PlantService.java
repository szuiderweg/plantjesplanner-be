package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.PlantDto;
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

    //DTO mappers
    private PlantDto mapToPlantDto(Plant plant){
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setDutchName(plant.getDutchName());
        plantDto.setLatinName(plant.getLatinName());
        plantDto.setPlantDescription(plant.getPlantDescription());
        plantDto.setWaterPreference(plant.getWaterPreference());
        plantDto.setSunPreference(plant.getSunPreference());
        plantDto.setWindTolerance(plant.getWindTolerance());
        plantDto.setBloomColour(plant.getBloomColour());
        plantDto.setHeight(plant.getHeight());
        plantDto.setFootprint(plant.getFootprint());
        plantDto.setPottedPlant(plant.getPottedPlant());
        plantDto.setSoilPreference(plant.getSoilPreference());

        return plantDto;
    }

    private Plant mapFromPlantDto(PlantDto plantDto){
        Plant plant = new Plant();
        plant.setDutchName(plantDto.getDutchName());
        plant.setLatinName(plantDto.getLatinName());
        plant.setPlantDescription(plantDto.getPlantDescription());
        plant.setWaterPreference(plantDto.getWaterPreference());
        plant.setSunPreference(plantDto.getSunPreference());
        plant.setWindTolerance(plantDto.getWindTolerance());
        plant.setBloomColour(plantDto.getBloomColour());
        plant.setHeight(plantDto.getHeight());
        plant.setFootprint(plantDto.getFootprint());
        plant.setPottedPlant(plantDto.getPottedPlant());
        plant.setSoilPreference(plantDto.getSoilPreference());

        return plant;
    }

}
