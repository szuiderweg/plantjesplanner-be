
//TODO voor het inleveren deze klasse verwijderen. requests op images lopen altijd via de controller van een parent entity

//package nl.novi.be_plantjesplanner.controllers;
//
//import nl.novi.be_plantjesplanner.dtos.ImageDownloadDto;
//import nl.novi.be_plantjesplanner.dtos.ImageMetadataDto;
//import nl.novi.be_plantjesplanner.dtos.ImageUploadDto;
//import nl.novi.be_plantjesplanner.services.ImageService;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping(value = "/image")
//public class ImageController {
//    private final ImageService imageService;
//    public ImageController(ImageService imageService){
//        this.imageService = imageService;
//    }
//
//    @PostMapping//uplaod DTO implementeren . Dit is een andere DTO omdat voor het downloaden een Multipart file naar de servicegaat  en voor uploaden de uitkomt een Resource is. Dus de input is wezenlijk anders dan de output.
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Geen bestand ontvangen.");
//        }
//        ImageUploadDto imageUploadDto = new ImageUploadDto(file, " ");
//        String savedImageMessage = imageService.saveImage(imageUploadDto);
//        return ResponseEntity.ok(savedImageMessage);
//    }
//
//    @PutMapping("/{fileName}")
//    public ResponseEntity<String> updateImage(@PathVariable String fileName, @RequestParam("file") MultipartFile file){
//        ImageUploadDto imageUploadDto = new ImageUploadDto(file, fileName);
//        String message = imageService.updateImage(imageUploadDto);
//        return ResponseEntity.ok(message);
//    }
//
//    @GetMapping("/{fileName}")//GET request for the image without metadata
//    public ResponseEntity<Resource>downloadImage(@PathVariable String fileName){
//        ImageDownloadDto imageDownloadDto = imageService.getImageDto(fileName);
//        return ResponseEntity.ok().contentType(imageDownloadDto.mediaType()).body(imageDownloadDto.resource());
//    }
//
//    @GetMapping("/metadata/{fileName}")//GET request for the metadata of the image
//    public ResponseEntity<ImageMetadataDto>downloadImageMetadata(@PathVariable String fileName){
//        ImageMetadataDto imageMetadataDto= imageService.getImageMetadataDto(fileName);
//
//        return ResponseEntity.ok(imageMetadataDto);
//    }
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deletePlant(@PathVariable Long id){
////        plantService.deletePlantById(id);
////        return ResponseEntity.noContent().build();
////    }
//}
//
