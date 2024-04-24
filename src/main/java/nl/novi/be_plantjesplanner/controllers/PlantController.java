package nl.novi.be_plantjesplanner.controllers;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.services.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/plants")
public class PlantController {
   private final PlantService plantService;

   public PlantController(PlantService plantService)
   {this.plantService = plantService;}


   //POST a single plant
   //done: repository
   //done: service
   //done: connect repository to service
   //done: connect service to controller
   //done: complete controller POST method
   //todo: Postman plant POST request
   @PostMapping
   public ResponseEntity<Plant> postPlant(@RequestBody Plant plant){
      return ResponseEntity.status(HttpStatus.CREATED).body(plantService.savePlant(plant));
   }

    //GET a specific plant

    //GET all plants

   //edit a specific plant


    //delete a specific plant

    //dto mappers

    //plantEntity2Dto
    //plantD


}


