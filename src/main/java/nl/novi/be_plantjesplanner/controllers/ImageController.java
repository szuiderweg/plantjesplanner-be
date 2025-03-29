package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.services.ImageService;
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
            return ResponseEntity.badRequest().body("Geen bestand ontvangen.");//TODO is dit ok als error afhandeling?
        }
        try {
            // Bestandsnaam (uniek maken)
            String savedImageName = imageService.saveImage(file);
            return ResponseEntity.ok("afbeelding " +savedImageName+" is opgeslagen.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Fout in controller bij uploaden.");
        }
    }
}
