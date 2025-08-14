package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadFileDto;
import nl.novi.be_plantjesplanner.dtos.ImageUploadFileDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.entities.ImageMetadata;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public PlantDto savePlant(PlantDto plantDto, ImageUploadFileDto imageUploadDto) {
        Plant newPlant = Mapper.mapFromPlantDto(plantDto);

        if (plantRepository.existsByDutchNameIgnoreCase(newPlant.getDutchName())) {
            throw new DuplicateResourceException("Er bestaat al een plant met deze naam: " + newPlant.getDutchName());
        }
        if (imageUploadDto.file() != null && !imageUploadDto.file().isEmpty()) {
            ImageMetadata newImage = imageService.saveImage(imageUploadDto);
            newPlant.setPlantAvatar(newImage);
        }

        Plant savedPlant = plantRepository.save(newPlant);
        return Mapper.mapToPlantDto(savedPlant);
    }

    //corresponds to updatePlant request in the PlantController
    public PlantDto updatePlantById(PlantDto plantDto , ImageUploadFileDto imageUploadDto, Long id){
       Plant plantUpdate = Mapper.mapFromPlantDto(plantDto);

        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant existingPlant = plantOptional.get();

            //update basic plant properties
            existingPlant.setDutchName(plantUpdate.getDutchName());
            existingPlant.setLatinName(plantUpdate.getLatinName());
            existingPlant.setDescription(plantUpdate.getDescription());
            existingPlant.setHeight(plantUpdate.getHeight());
            existingPlant.setFootprint(plantUpdate.getFootprint());
            existingPlant.setBloomColorHex(plantUpdate.getBloomColorHex());
            existingPlant.setBloomColorGroup(plantUpdate.getBloomColorGroup());
            existingPlant.setPublished(plantUpdate.isPublished());

            //update plant Locale (optional)
            if(plantDto.localeDto() != null){
                existingPlant.setLocale(Mapper.mapFromLocaleDto(plantDto.localeDto()));
            }
            //update plant BloomingCalendar (optional)
            if(plantDto.bloomingCalendarDto() != null){
                existingPlant.setBloomingCalendar(Mapper.mapFromBloomingCalendarDto(plantDto.bloomingCalendarDto()));
            }
            //update plant avatar image (optional)
            if (imageUploadDto.file() != null && !imageUploadDto.file().isEmpty()) {
                String oldAvatarFilename = existingPlant.getPlantAvatar().getStoredFilename();//retrieve the filename of the old image
                ImageMetadata imageUpdate = imageService.updateImage(imageUploadDto,oldAvatarFilename);
                existingPlant.setPlantAvatar(imageUpdate);//
            }

            plantRepository.save(existingPlant);
            return Mapper.mapToPlantDto(existingPlant);
        }
        else{
            throw new RecordNotFoundException("geen plant gevonden met id "+ id+" , dus ook niet aangepast");
        }
    }

    public PlantDto getPlantById(Long id){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            return Mapper.mapToPlantDto(plantOptional.get());
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
    public List<PlantDto> getAllPlants()
    {
        List<Plant> foundPlants = plantRepository.findAll();

        List<PlantDto> foundPlantsDto = new ArrayList<>();
        for(Plant plant : foundPlants){
            PlantDto foundPlantDto = Mapper.mapToPlantDto(plant);
            foundPlantsDto.add(foundPlantDto);
        }
        return foundPlantsDto;
    }

    //search for plants by Dutch name
    public List<PlantDto> getPlantsByDutchName(String dutchName)
    {
        List<Plant> foundPlants = plantRepository.findByDutchNameContainingIgnoreCase(dutchName);

        List<PlantDto> foundPlantsDto = new ArrayList<>();
        for(Plant plant : foundPlants){
            PlantDto foundPlantDto = Mapper.mapToPlantDto(plant);
            foundPlantsDto.add(foundPlantDto);
        }
        return foundPlantsDto;
    }

    public void deletePlantById(Long id){
        //look up the id of the image linked to this plant and delete the image file first, then the rest of the plant
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant foundPlant = plantOptional.get();
            Long plantAvatarId = foundPlant.getPlantAvatar().getId();
            imageService.deleteImageById(plantAvatarId, true);
        }
        plantRepository.deleteById(id);
    }
}
