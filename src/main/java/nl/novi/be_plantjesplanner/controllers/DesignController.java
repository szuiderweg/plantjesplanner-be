package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.dtos.DesignDto;
import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.services.DesignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static nl.novi.be_plantjesplanner.helpers.Mapper.mapToDesignDto;
import static nl.novi.be_plantjesplanner.helpers.Mapper.mapToPlantDto;

@RestController
@RequestMapping("/designs")
public class DesignController {
    private final DesignService designService;

    public DesignController(DesignService designService){
        this.designService = designService;
    }

    @GetMapping("/me")
    public ResponseEntity<DesignDto> getMyDesign(Authentication authentication){
        String username = authentication.getName();
        Design design = designService.getDesignOfUser(username);
        return ResponseEntity.ok().body(mapToDesignDto(design));
    }
}
