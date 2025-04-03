package nl.novi.be_plantjesplanner.dtos;
import org.springframework.web.multipart.MultipartFile;

public record ImageUploadDto(MultipartFile file, String fileName) {
}

