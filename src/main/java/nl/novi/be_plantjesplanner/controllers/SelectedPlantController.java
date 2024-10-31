package nl.novi.be_plantjesplanner.controllers;

import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.dtos.SelectedPlantDto;
import nl.novi.be_plantjesplanner.entities.SelectedPlant;
import nl.novi.be_plantjesplanner.services.SelectedPlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/selected_plants")
public class SelectedPlantController {
    private final SelectedPlantService selectedPlantService;

    public SelectedPlantController(SelectedPlantService selectedPlantService){ this.selectedPlantService = selectedPlantService;
    }

    //POST a new selectedPlant
    //todo implement dto
    @PostMapping
    public ResponseEntity<SelectedPlantDto> postSelectedPlant(@Valid @RequestBody SelectedPlantDto selectedPlantDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedPlantService.saveSelectedPlant(selectedPlantDto));
    }

    //edit (PUT) a selectedPlant
    @PutMapping("/{id}")
    public ResponseEntity<SelectedPlantDto> updateSelectedPlant(@Valid @RequestBody SelectedPlantDto selectedPlantDto, @PathVariable Long id) {
        SelectedPlantDto updatedSelectedPlantDto = selectedPlantService.updateSelectedPlantById(selectedPlantDto, id);
        return ResponseEntity.ok().body(updatedSelectedPlantDto);
    }

    //DELETE a specific selected plant by id
//    @DeleteMapping("/{id}")

}
