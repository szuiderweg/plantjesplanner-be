package nl.novi.be_plantjesplanner.controllers;
import nl.novi.be_plantjesplanner.entities.Plant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/plants")
//this is a basic version of the plant controller that just returns some string messages (or nothing) It does not communicate with other layers yet.
public class PlantController {

   //POST a single plant
   //todo: repository
   //todo: service
   //todo: connect repository to service
   //todo: connect controller to service
   //todo: complete POST method
   //todo: Postman plant POST request
   @PostMapping("/")
   public ResponseEntity<Plant> postPlant(@RequestBody Plant plant){
      return ResponseEntity.status(HttpStatus.CREATED).body(plant);
   }

    //GET a specific plant

    //GET all plants

   //edit a specific plant


    //delete a specific plant

    //dto mappers

    //plantEntity2Dto
    //plantD


}


