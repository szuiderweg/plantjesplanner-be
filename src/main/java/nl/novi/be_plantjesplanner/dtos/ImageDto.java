package nl.novi.be_plantjesplanner.dtos;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

public record ImageDto(Resource resource, MediaType mediaType) {
}

