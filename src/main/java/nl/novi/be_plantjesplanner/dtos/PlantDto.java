package nl.novi.be_plantjesplanner.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nl.novi.be_plantjesplanner.enumerations.ColorGroup;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public record PlantDto(
        Long id,
        @NotBlank(message = "Plantnaam is verplicht.")
        @Length(min = 2, max = 50, message = "De plantnaam moet tussen de 2 en 50 tekens zijn")
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
