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
    public PlantDto savePlant(PlantDto plantDto)
    {
        Plant savedPlant = plantRepository.save(mapFromPlantDto(plantDto));
        return mapToPlantDto(savedPlant);
    }

    //corresponds to updatePlant request in the PlantController
    public PlantDto updatePlantById(PlantDto plantDto , Long id){
       Plant plantUpdate = mapFromPlantDto(plantDto);

        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant foundPlant = plantOptional.get();
            //update all fieldvalues in foundplant with values from  new plant
            foundPlant.setDutchName(plantUpdate.getDutchName());
//            foundPlant.setLatinName(plantUpdate.getLatinName());
//            foundPlant.setPlantDescription(plantUpdate.getPlantDescription());
//            foundPlant.setWaterPreference(plantUpdate.getWaterPreference());
//            foundPlant.setSunPreference(plantUpdate.getSunPreference());
//            foundPlant.setWindTolerance(plantUpdate.getWindTolerance());
//            foundPlant.setBloomColour(plantUpdate.getBloomColour());
//            foundPlant.setHeight(plantUpdate.getHeight());
//            foundPlant.setFootprint(plantUpdate.getFootprint());
//            foundPlant.setPottedPlant(plantUpdate.getPottedPlant());
//            foundPlant.setSoilPreference(plantUpdate.getSoilPreference());
//            //todo put additional fields here
            plantRepository.save(foundPlant);
            return mapToPlantDto(foundPlant);

        }
        else{
            throw new RecordNotFoundException("geen plant gevonden met id "+ id+" , dus ook niet aangepast");
        }
    }

    public void deletePlantById(Long id){
        plantRepository.deleteById(id);
    }

    public PlantDto getPlantById(Long id){
      Optional<Plant> plantOptional = plantRepository.findById(id);
      if(plantOptional.isPresent()){
          return mapToPlantDto(plantOptional.get());
      }
      else{
          throw new RecordNotFoundException("geen plant gevonden met id "+ id);
      }
    }

    //corresponds to GET all Plants request in the PlantController
    public List<PlantDto> getAllPlants()
    {
        List<Plant> foundPlants = plantRepository.findAll();

        List<PlantDto> foundPlantsDto = new ArrayList<>();
        for(Plant plant : foundPlants){
            PlantDto foundPlantDto = mapToPlantDto(plant);
            foundPlantsDto.add(foundPlantDto);
        }
        return foundPlantsDto;
    }

    //DTO mappers
    private PlantDto mapToPlantDto(Plant plant){
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setDutchName(plant.getDutchName());
//        plantDto.setLatinName(plant.getLatinName());
//        plantDto.setPlantDescription(plant.getPlantDescription());
//        plantDto.setWaterPreference(plant.getWaterPreference());
//        plantDto.setSunPreference(plant.getSunPreference());
//        plantDto.setWindTolerance(plant.getWindTolerance());
//        plantDto.setBloomColour(plant.getBloomColour());
//        plantDto.setHeight(plant.getHeight());
//        plantDto.setFootprint(plant.getFootprint());
//        plantDto.setPottedPlant(plant.getPottedPlant());
//        plantDto.setSoilPreference(plant.getSoilPreference());

        return plantDto;
    }

    private Plant mapFromPlantDto(PlantDto plantDto){
        Plant plant = new Plant();
        //no setter for id since this will be generated automatically for a new plant
        plant.setDutchName(plantDto.getDutchName());
//        plant.setLatinName(plantDto.getLatinName());
//        plant.setPlantDescription(plantDto.getPlantDescription());
//        plant.setWaterPreference(plantDto.getWaterPreference());
//        plant.setSunPreference(plantDto.getSunPreference());
//        plant.setWindTolerance(plantDto.getWindTolerance());
//        plant.setBloomColour(plantDto.getBloomColour());
//        plant.setHeight(plantDto.getHeight());
//        plant.setFootprint(plantDto.getFootprint());
//        plant.setPottedPlant(plantDto.getPottedPlant());
//        plant.setSoilPreference(plantDto.getSoilPreference());

        return plant;
    }

}
