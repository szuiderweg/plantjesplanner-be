package nl.novi.be_plantjesplanner.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddSelectedPlantDto(@NotNull Long plantId, @Min(1) int amount) {}
