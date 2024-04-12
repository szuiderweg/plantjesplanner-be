package nl.novi.be_plantjesplanner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plants")
//this is a basic version of the plant controller that just returns some string messages (or nothing) It does not communicate with other layers yet.
public class PlantController {
    @PostMapping//add a new plant
    public ResponseEntity<String> addPlant(@RequestBody String plant){
        return ResponseEntity.status(HttpStatus.CREATED).body(plant);
    }
    @GetMapping//get all plants
   public ResponseEntity<String> getPlants(){
       return ResponseEntity.ok("hier heb je plantjes!");

   }
   @GetMapping("/{id}")//get a specific plant
   public ResponseEntity<String> getPlant(@PathVariable long id){
       String requestedPlant = "hier heb je deze mooie plant met nummer "+ id;
       return ResponseEntity.ok(requestedPlant);
   }

   //edit a specific plant


    //delete a specific plant

    //dto mappers

    //plantEntity2Dto
    //plantD


}


