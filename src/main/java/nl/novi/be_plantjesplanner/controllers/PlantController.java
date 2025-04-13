package nl.novi.be_plantjesplanner.controllers;
import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.services.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value ="/plants")
public class PlantController {
   private final PlantService plantService;

   public PlantController(PlantService plantService)
   {this.plantService = plantService;}


   //POST a new plant
   @PostMapping
   public ResponseEntity<PlantDto> postPlant(@Valid @RequestBody PlantDto plantDto){
      return ResponseEntity.status(HttpStatus.CREATED).body(plantService.savePlant(plantDto));
   }

   // edit (PUT) a specific plant
   @PutMapping("/{id}")
   public ResponseEntity<PlantDto> updatePlant(@Valid @RequestBody PlantDto plantDto, @PathVariable Long id){
      PlantDto updatedPlantDto = plantService.updatePlantById(plantDto, id);
      return ResponseEntity.ok().body(updatedPlantDto);
   }


   //DELETE a specific plant
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletePlant(@PathVariable Long id){
      plantService.deletePlantById(id);
      return ResponseEntity.noContent().build();
   }

   //GET all plants
   @GetMapping
   public ResponseEntity<List<PlantDto>> getAllPlants(){
      List<PlantDto> foundPlantsDto = plantService.getAllPlants();
      return ResponseEntity.ok().body(foundPlantsDto);
   }
   //GET a specific plant by id
   @GetMapping("/{id}")
   public ResponseEntity<PlantDto> getPlant(@PathVariable("id") Long id){
      return ResponseEntity.ok(plantService.getPlantById(id));
   }


   //todo GET plants by name

   //todo GET plants that match properties of myGarden

   //todo GET plants that match one custom criterium



}


