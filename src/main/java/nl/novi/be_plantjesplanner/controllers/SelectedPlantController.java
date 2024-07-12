package nl.novi.be_plantjesplanner.controllers;

import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.entities.SelectedPlant;
import nl.novi.be_plantjesplanner.services.SelectedPlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/selected_plants")
public class SelectedPlantController {
    private final SelectedPlantService selectedPlantService;

    public SelectedPlantController(SelectedPlantService selectedPlantService){ this.selectedPlantService = selectedPlantService;
    }

    //POST a new selectedPlant
    //todo implement dto
    @PostMapping
    public ResponseEntity<SelectedPlant> postSelectedPlant(@Valid @RequestBody SelectedPlant selectedPlant){
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedPlantService.saveSelectedPlant(selectedPlant));
    }

}
