package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.services.SelectedPlantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/selected_plants")
public class SelectedPlantController {
    private final SelectedPlantService selectedPlantService;

    public SelectedPlantController(SelectedPlantService selectedPlantService){ this.selectedPlantService = selectedPlantService;
    }



}
