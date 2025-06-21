package nl.novi.be_plantjesplanner.dtos;

import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.Plant;

public record SelectedPlantDto(Long id, Integer quantity, Design2Dto design2Dto, PlantDto plantDto){}
