package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageUploadDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.entities.Image;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final ImageService imageService;

    public PlantService(PlantRepository plantRepository, @Qualifier("plantImageService") ImageService imageService) {
        this.plantRepository = plantRepository;
        this.imageService = imageService;
    }

    //corresponds to postPlant request in the PlantController
    public PlantDto savePlant(PlantDto plantDto, ImageUploadDto imageUploadDto) {
        Plant newPlant = Mapper.mapFromPlantDto(plantDto);

        if (plantRepository.existsByDutchNameIgnoreCase(plantDto.dutchName())) {
            throw new DuplicateResourceException("Er bestaat al een plant met deze naam: " + newPlant.getDutchName());
        }
        if (imageUploadDto.file() != null && !imageUploadDto.file().isEmpty()) {
            Image newImage = imageService.saveImage(imageUploadDto);
            newPlant.setPlantAvatar(newImage);
        }

        Plant savedPlant = plantRepository.save(newPlant);
        return Mapper.mapToPlantDto(savedPlant);
    }
}
//    //corresponds to updatePlant request in the PlantController
//    public PlantDto updatePlantById(PlantDto plantDto , Long id){
//       Plant plantUpdate = mapFromPlantDto(plantDto);
//
//        Optional<Plant> plantOptional = plantRepository.findById(id);
//        if(plantOptional.isPresent()){
//            Plant foundPlant = plantOptional.get();
//            //update all fieldvalues in foundplant with values from  new plant
//            foundPlant.setDutchName(plantUpdate.getDutchName());
////            foundPlant.setLatinName(plantUpdate.getLatinName());
////            foundPlant.setPlantDescription(plantUpdate.getPlantDescription());
////            foundPlant.setWaterPreference(plantUpdate.getWaterPreference());
////            foundPlant.setSunPreference(plantUpdate.getSunPreference());
////            foundPlant.setWindTolerance(plantUpdate.getWindTolerance());
////            foundPlant.setBloomColour(plantUpdate.getBloomColour());
////            foundPlant.setHeight(plantUpdate.getHeight());
////            foundPlant.setFootprint(plantUpdate.getFootprint());
////            foundPlant.setPottedPlant(plantUpdate.getPottedPlant());
////            foundPlant.setSoilPreference(plantUpdate.getSoilPreference());
////            //todo put additional fields here
//            plantRepository.save(foundPlant);
//            return mapToPlantDto(foundPlant);
//
//        }
//        else{
//            throw new RecordNotFoundException("geen plant gevonden met id "+ id+" , dus ook niet aangepast");
//        }
//    }
//
//    public void deletePlantById(Long id){
//        plantRepository.deleteById(id);
//    }
//
//    public PlantDto getPlantById(Long id){
//      Optional<Plant> plantOptional = plantRepository.findById(id);
//      if(plantOptional.isPresent()){
//          return mapToPlantDto(plantOptional.get());
//      }
//      else{
//          throw new RecordNotFoundException("geen plant gevonden met id "+ id);
//      }
//    }
//
//    //corresponds to GET all Plants request in the PlantController
//    public List<PlantDto> getAllPlants()
//    {
//        List<Plant> foundPlants = plantRepository.findAll();
//
//        List<PlantDto> foundPlantsDto = new ArrayList<>();
//        for(Plant plant : foundPlants){
//            PlantDto foundPlantDto = mapToPlantDto(plant);
//            foundPlantsDto.add(foundPlantDto);
//        }
//        return foundPlantsDto;
//    }
//
//}
