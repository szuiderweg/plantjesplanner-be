package nl.novi.be_plantjesplanner.dtos;

import nl.novi.be_plantjesplanner.enumerations.Moisture;
import nl.novi.be_plantjesplanner.enumerations.Sunlight;
import nl.novi.be_plantjesplanner.enumerations.WindTolerance;

public record LocaleDto(Sunlight sunlight, Moisture moisture, WindTolerance windTolerance, String soilType, Boolean openGroundOnly) {
}
