package nl.novi.be_plantjesplanner.dtos;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

public record ImageDownloadFileDto(Resource resource, MediaType mediaType) {
}

