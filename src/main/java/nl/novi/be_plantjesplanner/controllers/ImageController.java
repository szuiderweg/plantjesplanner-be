package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadDto;
import nl.novi.be_plantjesplanner.services.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/image")
public class ImageController {
    private final ImageService imageService;
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping//TODO uplaod DTO implementeren . Dit is een andere DTO omdat voor het downloaden een Multipart file naar de servicegaat  en voor uploaden de uitkomt een Resource is. Dus de input is wezenlijk anders dan de output.
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Geen bestand ontvangen.");
        }
        String savedImageMessage = imageService.saveImage(file);
        return ResponseEntity.ok(savedImageMessage);
    }

    @PutMapping("/{fileName}")
    public ResponseEntity<String> updateImage(@PathVariable String fileName, @RequestParam("file") MultipartFile file){
        String message = imageService.updateImage(fileName,file);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{fileName}")//GET request for the image without metadata
    public ResponseEntity<Resource>downloadImage(@PathVariable String fileName){
        ImageDownloadDto imageDownloadDto = imageService.getImageDto(fileName);
        return ResponseEntity.ok().contentType(imageDownloadDto.mediaType()).body(imageDownloadDto.resource());
    }
    //TODO GET request for either metadata only or image+metadata. kijk in postman wat eruit komt
    //TODO adapt ImageDTO to include metadata
    //TODO PUT request
    //TODO DEL request
}

