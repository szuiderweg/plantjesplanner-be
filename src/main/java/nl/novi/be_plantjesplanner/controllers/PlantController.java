package nl.novi.be_plantjesplanner.controllers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import nl.novi.be_plantjesplanner.dtos.ImageDownloadFileDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.services.PlantService;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static nl.novi.be_plantjesplanner.helpers.Mapper.mapFromPlantDto;
import static nl.novi.be_plantjesplanner.helpers.Mapper.mapToPlantDto;
import nl.novi.be_plantjesplanner.helpers.FileChecker;

@RestController
@RequestMapping(value ="/plants")
public class PlantController {
   private final PlantService plantService;

   public PlantController(PlantService plantService) {
      this.plantService = plantService;
   }

   //POST a new plant - admin only
   @PostMapping
   public ResponseEntity<PlantDto> postPlant(@Valid @RequestPart("plant") PlantDto plantDto, @RequestPart(value = "image", required = false) MultipartFile file) {
      if(file != null) {
         FileChecker.checkUploadedImage(file);
      }
      Plant newPlant = mapFromPlantDto(plantDto);
      newPlant = plantService.savePlant(newPlant, file);
      PlantDto savedPlantDto = mapToPlantDto(newPlant);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedPlantDto);
   }

   // edit (PUT) a specific plant - admin only
   @PutMapping("/{id}")
   public ResponseEntity<PlantDto> updatePlant(@Valid @RequestPart("plant") PlantDto plantDto,@RequestPart(value = "image", required = false) MultipartFile file, @PathVariable Long id){
      if(file != null) {
         FileChecker.checkUploadedImage(file);
      }
      Plant updatedPlant = mapFromPlantDto(plantDto);
      updatedPlant = plantService.updatePlantById(updatedPlant,file, id);
      PlantDto updatedPlantDto = mapToPlantDto(updatedPlant);
      return ResponseEntity.ok().body(updatedPlantDto);
   }

   //GET a specific plant by id - admin & designer
   @GetMapping("/{id}")
   public ResponseEntity<PlantDto> getPlant(@PathVariable("id") Long id){
      return ResponseEntity.ok(Mapper.mapToPlantDto(plantService.getPlantById(id)));
   }

   //GET avatar image of a specific plant by plant id - admin & designer
   @GetMapping("/{id}/avatar")
   public ResponseEntity<Resource> downloadPlantAvatar(@PathVariable("id") Long id){
      ImageDownloadFileDto imageDownloadDto = plantService.getPlantAvatarByPlantId(id);
      return ResponseEntity.ok().contentType(imageDownloadDto.mediaType()).body(imageDownloadDto.resource());
   }

   //GET all plants - admin & designer
   @GetMapping
   public ResponseEntity<List<PlantDto>> getAllPlants(){
      List<Plant> foundPlants = plantService.getAllPlants();
      return ResponseEntity.ok().body(Mapper.mapToPlantDtoList(foundPlants));
   }
   //GET plant by dutch name - admin & designer
   //todo: include Latin name in search
   @GetMapping("/search")
   ResponseEntity<List<PlantDto>> getPlantsByDutchName(@RequestParam @NotBlank @Length(min = 1, max = 50, message = "De zoekterm moet tussen de 1 en 50 tekens zijn") String name){
      List<Plant> foundPlants = plantService.getPlantsByDutchName(name);
      return ResponseEntity.ok().body(Mapper.mapToPlantDtoList(foundPlants));
   }

   //DELETE plant - admin only
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletePlant(@PathVariable Long id){
      plantService.deletePlantById(id);
      return ResponseEntity.noContent().build();
   }
}

