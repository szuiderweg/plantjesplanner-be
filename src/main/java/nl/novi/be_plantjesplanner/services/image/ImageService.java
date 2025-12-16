package nl.novi.be_plantjesplanner.services.image;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadFileDto;
import nl.novi.be_plantjesplanner.entities.ImageMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageMetadata saveImage(MultipartFile uploadedImage);
    ImageMetadata updateImage(MultipartFile newFile, String requestedFileName);
    ImageDownloadFileDto getImageDto(String fileName);
    ImageMetadata getImageMetadata(String fileName);
    void deleteImageById(Long id, Boolean isQuiet);
}
