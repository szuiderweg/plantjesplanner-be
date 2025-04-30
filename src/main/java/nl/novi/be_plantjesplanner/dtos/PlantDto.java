package nl.novi.be_plantjesplanner.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nl.novi.be_plantjesplanner.enumerations.ColorGroup;
import org.springframework.lang.Nullable;

public record PlantDto(
        Long id,
        @NotBlank(message = "Plantnaam is verplicht.")
        String dutchName,
        String latinName,
        String description,
        Double height,
        Double footprint,
        String bloomColorHex,
        ColorGroup bloomColorGroup,
        @NotNull Boolean published,
        @Nullable LocaleDto localeDto,
        @Nullable BloomingCalendarDto bloomingCalendarDto,
        @Nullable ImageMetadataDto plantAvatarDto) {}
