package nl.novi.be_plantjesplanner.controllers;
import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.dtos.ImageUploadDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.services.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value ="/plants")
public class PlantController {
   private final PlantService plantService;

   public PlantController(PlantService plantService) {
      this.plantService = plantService;
   }

   //POST a new plant
   @PostMapping
   public ResponseEntity<PlantDto> postPlant(@Valid @RequestPart("plant") PlantDto plantDto, @RequestPart(value = "image", required = false) MultipartFile file) {
      ImageUploadDto imageUploadDto = new ImageUploadDto(file, "");
      PlantDto savedPlantDto = plantService.savePlant(plantDto, imageUploadDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedPlantDto);
   }

   // edit (PUT) a specific plant
   @PutMapping("/{id}")
   public ResponseEntity<PlantDto> updatePlant(@Valid @RequestPart("plant") PlantDto plantDto,@RequestPart(value = "image", required = false) MultipartFile file, @PathVariable Long id){
      ImageUploadDto imageUploadDto = new ImageUploadDto(file,"");
      PlantDto updatedPlantDto = plantService.updatePlantById(plantDto,imageUploadDto, id);
      return ResponseEntity.ok().body(updatedPlantDto);
   }

   //GET a specific plant by id
   @GetMapping("/{id}")
   public ResponseEntity<PlantDto> getPlant(@PathVariable("id") Long id){
      return ResponseEntity.ok(plantService.getPlantById(id));
   }
//

   // //todo GET plant image
//
//   //todo GET plants by name
//

//
//   //GET all plants
//   @GetMapping
//   public ResponseEntity<List<PlantDto>> getAllPlants(){
//      List<PlantDto> foundPlantsDto = plantService.getAllPlants();
//      return ResponseEntity.ok().body(foundPlantsDto);
//   }
//

//
//   //todo GET plants that match properties of myGarden
//
//   //todo GET plants that match one custom criterium
//
   //   //DELETE a specific plant
//   @DeleteMapping("/{id}")
//   public ResponseEntity<Void> deletePlant(@PathVariable Long id){
//      plantService.deletePlantById(id);
//      return ResponseEntity.noContent().build();
//   }
//
//
}
//
//
