package nl.novi.be_plantjesplanner.dtos;

import java.time.ZonedDateTime;

public record ImageMetadataDto(Long id, String originalFilename, String storedFilename, ZonedDateTime uploadDateTime) {
}

