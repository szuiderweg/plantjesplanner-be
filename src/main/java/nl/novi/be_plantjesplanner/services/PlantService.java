package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadFileDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.entities.BloomingCalendar;
import nl.novi.be_plantjesplanner.entities.ImageMetadata;
import nl.novi.be_plantjesplanner.entities.Locale;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import nl.novi.be_plantjesplanner.services.image.ImageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Plant savePlant(Plant newPlant, MultipartFile newPlantAvatar) {
        if (plantRepository.existsByDutchNameIgnoreCase(newPlant.getDutchName())) {
            throw new DuplicateResourceException("Er bestaat al een plant met deze naam: " + newPlant.getDutchName());
        }
        if (newPlantAvatar!= null && !newPlantAvatar.isEmpty()) {
            ImageMetadata plantAvatarMetadata = imageService.saveImage(newPlantAvatar); //save new avatar file in local filesystem, and create metadata about the avatar file
            newPlant.setPlantAvatar(plantAvatarMetadata);//save avatar metadata as part of the new plant
        }

        Plant savedPlant = plantRepository.save(newPlant);
        return savedPlant;
    }

    //corresponds to updatePlant request in the PlantController
    public Plant updatePlantById(Plant updatedPlant , MultipartFile updatedAvatarFile, Long id){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant existingPlant = plantOptional.get();

            //update basic plant properties
            existingPlant.setDutchName(updatedPlant.getDutchName());
            existingPlant.setLatinName(updatedPlant.getLatinName());
            existingPlant.setDescription(updatedPlant.getDescription());
            existingPlant.setHeight(updatedPlant.getHeight());
            existingPlant.setFootprint(updatedPlant.getFootprint());
            existingPlant.setBloomColorHex(updatedPlant.getBloomColorHex());
            existingPlant.setBloomColorGroup(updatedPlant.getBloomColorGroup());
            existingPlant.setPublished(updatedPlant.isPublished());

            //update plant Locale ()
            Locale updatedLocale = updatedPlant.getLocale();
            if(updatedLocale != null){
                existingPlant.setLocale(updatedLocale);
            }
            //update plant BloomingCalendar ()
            BloomingCalendar updatedBloomingCalendar = updatedPlant.getBloomingCalendar();
            if(updatedBloomingCalendar != null){
                existingPlant.setBloomingCalendar(updatedBloomingCalendar);
            }
            //update plant avatar image (optional)
            if (updatedAvatarFile != null) {
                if (existingPlant.getPlantAvatar() == null){
                    ImageMetadata newImageMetadata = imageService.saveImage(updatedAvatarFile);
                    existingPlant.setPlantAvatar(newImageMetadata);
                }else{
                    String oldAvatarFilename = existingPlant.getPlantAvatar().getStoredFilename();//retrieve the filename of the old image
                    ImageMetadata updatedAvatarMetadata = imageService.updateImage(updatedAvatarFile, oldAvatarFilename);//update plant avatar file and generate new metadata
                    existingPlant.setPlantAvatar(updatedAvatarMetadata);
                }
            }
            plantRepository.save(existingPlant);
            return existingPlant;
        }
        else{
            throw new RecordNotFoundException("geen plant gevonden met id "+ id+" , dus ook niet aangepast");
        }
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

    public ImageDownloadFileDto getPlantAvatarByPlantId(Long id){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant foundPlant = plantOptional.get();
            String avatarStoredFileName = foundPlant.getPlantAvatar().getStoredFilename();

            if(avatarStoredFileName != null){
                return imageService.getImageDto(avatarStoredFileName);
            }else{
                throw new RecordNotFoundException("geen afbeelding gevonden bij plant met id: "+ id);
            }
        }
        else{
            throw new RecordNotFoundException("geen plant gevonden met id "+ id);
        }
    }
        //corresponds to GET all Plants request in the PlantController
    public List<Plant> getAllPlants()
    {
        List<Plant> foundPlants = plantRepository.findAll();
        return foundPlants;
    }

    //search for plants by Dutch name
    public List<Plant> getPlantsByDutchName(String dutchName)
    {
        List<Plant> foundPlants = plantRepository.findByDutchNameContainingIgnoreCase(dutchName);
        return foundPlants;
    }

    @Transactional
    public void deletePlantById(Long id){
        //look up the id of the image linked to this plant and delete the image file first, then the rest of the plant
       Plant plant = plantRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("geen plant gevonden met id: "+id));

            if(plant.getPlantAvatar() != null){
            Long plantAvatarId = plant.getPlantAvatar().getId();
            imageService.deleteImageById(plantAvatarId, true);
            }

        plantRepository.delete(plant);
    }
}
