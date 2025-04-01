package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.dtos.ImageDto;
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

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Geen bestand ontvangen.");
        }
        String savedImageMessage = imageService.saveImage(file);
        return ResponseEntity.ok(savedImageMessage);//todo: output string ombouwen naar stored filename , want die is nodig voor het downloaden of de deze toevoegen aan de string
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource>downloadImage(@PathVariable String fileName){
        ImageDto imageDto = imageService.getImageDto(fileName);
        return ResponseEntity.ok().contentType(imageDto.mediaType()).body(imageDto.resource());
    }

}

