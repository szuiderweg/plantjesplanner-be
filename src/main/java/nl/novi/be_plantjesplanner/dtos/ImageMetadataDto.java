package nl.novi.be_plantjesplanner.dtos;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

public record ImageMetadataDto(String originalFilename, String storedFilename, ZonedDateTime uploadDateTime) {
}

