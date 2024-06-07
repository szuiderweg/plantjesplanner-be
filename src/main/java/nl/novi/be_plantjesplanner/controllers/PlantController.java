package nl.novi.be_plantjesplanner.controllers;
import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.entities.Plant;
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
   public ResponseEntity<Plant> postPlant(@Valid @RequestBody Plant plant){
      return ResponseEntity.status(HttpStatus.CREATED).body(plantService.savePlant(plant));
   }


   // edit (PUT) a specific plant


   //DELETE a specific plant

   //GET all plants
   @GetMapping
   public ResponseEntity<List<Plant>> getAllPlants(){
      List<Plant> foundPlants = plantService.getAllPlants();
      return ResponseEntity.ok().body(foundPlants);
   }
   //GET a specific plant by id
   @GetMapping("/{id}")
   public ResponseEntity<Plant> getPlant(@PathVariable("id") Long id){
      return ResponseEntity.ok(plantService.getPlantById(id));
   }



   //todo GET plants that match properties of myGarden

   //todo GET plants that match custom criteria






    //dto mappers

    //plantEntity2Dto
    //plantD


}


