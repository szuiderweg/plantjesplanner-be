package nl.novi.be_plantjesplanner.dtos;

import java.util.Set;

public record DesignDto(Long id, String title, double gardenSize, LocaleDto localeDto, Set<SelectedPlantDto> selectedPlantDtoSet) {
}
