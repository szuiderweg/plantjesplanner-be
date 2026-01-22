package nl.novi.be_plantjesplanner.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nl.novi.be_plantjesplanner.dtos.AddSelectedPlantDto;
import nl.novi.be_plantjesplanner.dtos.DesignDto;
import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.services.DesignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static nl.novi.be_plantjesplanner.helpers.Mapper.*;

@RestController
@RequestMapping("/designs")
public class DesignController {
    private final DesignService designService;

    public DesignController(DesignService designService){
        this.designService = designService;
    }

    //retrieve design of current user
    @GetMapping("/me")
    public ResponseEntity<DesignDto> getMyDesign(Authentication authentication){
        String username = authentication.getName();
        Design design = designService.getDesignOfUser(username);
        return ResponseEntity.ok().body(mapToDesignDto(design));
    }

    //update whole design of current user except for the SelectedPlants
    @PutMapping("/me")
    public ResponseEntity<DesignDto> updateMyDesign(@RequestBody DesignDto designUpdateDto, Authentication authentication){
        String username = authentication.getName();
        Design designUpdate = mapFromDesignDto(designUpdateDto);
        Design updatedDesign = designService.updateDesignOfUser(username, designUpdate);
        return ResponseEntity.ok().body(mapToDesignDto(updatedDesign));
    }


    //requests for selectedPlants of a design

    // add a plant to a design as a selected plant
    @PostMapping(value="/me/selected-plants")
    public ResponseEntity<DesignDto> addPlantToMyDesign(@Valid @RequestBody AddSelectedPlantDto dto, Authentication authentication){
        String username = authentication.getName();

        Design updatedDesign = designService.addPlantToDesign(username, dto.plantId(), dto.amount());
        return ResponseEntity.ok().body(Mapper.mapToDesignDto(updatedDesign));
    }

    //change amount of a selectedPlant
    @PatchMapping(value = "/me/selected-plants/{selectedPlantId}")
    public ResponseEntity<Void> updateSelectedPlantAmount(@PathVariable Long selectedPlantId, @RequestParam @Min(1) int amount, Authentication authentication){
        String username = authentication.getName();
        designService.updateSelectedPlantAmount(username, selectedPlantId, amount);
        return ResponseEntity.noContent().build();
    }

    //remove a selectedPlant from a user's design
    @DeleteMapping(value = "/me/selected-plants/{selectedPlantId}")
    public ResponseEntity<Void> deleteSelectedPlant(@PathVariable Long selectedPlantId, Authentication authentication){
        String username = authentication.getName();
        designService.removePlantFromMyDesign(username, selectedPlantId);
        return ResponseEntity.noContent().build();
    }
}
