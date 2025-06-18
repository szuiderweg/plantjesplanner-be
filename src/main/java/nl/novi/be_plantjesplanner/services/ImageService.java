package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadDto;
import nl.novi.be_plantjesplanner.dtos.ImageMetadataDto;
import nl.novi.be_plantjesplanner.dtos.ImageUploadDto;
import nl.novi.be_plantjesplanner.entities.Image;
import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.exceptions.UnreadableFileException;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.repositories.ImageRepository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDirectory;
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg", "image/jpg","image/webp", "image/svg+xml" ); //todo: deze constant op een gepaste plek parkeren

    public ImageService(ImageRepository imageRepository, String folderName){
        this.imageRepository = imageRepository;
        String uploadDir = "../"+folderName;
        this.uploadDirectory = Paths.get(System.getProperty("user.dir"),uploadDir).toString();
        createImageUploadDirectory();
    }

    public Image saveImage(ImageUploadDto imageUploadDto)
    {
        MultipartFile uploadedImage = imageUploadDto.file();
        checkUploadedImage(uploadedImage);
       String originalFilename = uploadedImage.getOriginalFilename();
       try{
           String storedFilename = System.currentTimeMillis()+"_"+originalFilename;//make the storedfilename unique by adding the currrent time to the filename. This is to prevent confusion when downloading images with identical original names
           Path filepath = Paths.get(uploadDirectory, storedFilename);
           Files.copy(uploadedImage.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);
           Image savedImage = new Image(originalFilename, storedFilename);
           imageRepository.save(savedImage);
           return savedImage;
       }catch(IOException e){
           throw new RuntimeException("opslaan mislukt",e);
       }
    }

    public Image updateImage(ImageUploadDto imageUploadDto, String requestedFileName) {
        MultipartFile newFile = imageUploadDto.file();
        checkUploadedImage(newFile);//validate uploaded image

        //retrieve filepath of existing file
        Path oldFilePath = Paths.get(uploadDirectory, requestedFileName);
        if (!Files.exists(oldFilePath)) {
            throw new RecordNotFoundException("Afbeelding niet gevonden: " + requestedFileName);
        }
        //generate new unique filename and path for updated file
        String originalFileName = newFile.getOriginalFilename();
        String newStoredFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path newFilePath = Paths.get(uploadDirectory, newStoredFileName);
        Image newImage = new Image();
        try {
            //delete the old file
            Files.deleteIfExists(oldFilePath);
            //overwrite the existing file
            Files.copy(newFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

            //update metadata in database

            Optional<Image> imageOptional = imageRepository.findByStoredFilename(requestedFileName);
            if (imageOptional.isPresent()) {
                newImage = imageOptional.get();
                newImage.setStoredFilename(newStoredFileName);
                newImage.setOriginalFilename(originalFileName);
                newImage.setUploadDateTime();
                imageRepository.save(newImage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fout bij updaten van bestand: " + requestedFileName);
        }
        return newImage;
    }


    public ImageDownloadDto getImageDto(String fileName){
           try {
               Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
               Resource resource = new UrlResource(filePath.toUri());//retrieve file based on unique filename and upload directory location

               if (!resource.exists()) {
                   throw new RecordNotFoundException("afbeelding niet gevonden");
               }
               else if (!resource.isReadable()) {
                    throw new UnreadableFileException("dit bestand is onleesbaar: "+fileName);
               }
                //MIME-type ophalen en controleren
                Optional<MediaType> mediaTypeOptional = MediaTypeFactory.getMediaType(resource);
                if (mediaTypeOptional.isEmpty() || !ALLOWED_TYPES.contains(mediaTypeOptional.get().toString())) {
                throw new InvalidImageTypeException("Ongeldig bestandstype: "+mediaTypeOptional.get());
                }
               return new ImageDownloadDto(resource, mediaTypeOptional.get());
            }catch (IOException e) {
            throw new RuntimeException("Fout bij ophalen van bestand: " + fileName, e);
        }

    }

    public ImageMetadataDto getImageMetadataDto(String fileName){
            //retrieve metadata from database
            Optional<Image> imageOptional = imageRepository.findByStoredFilename(fileName);
            if(imageOptional.isPresent()) {
                Image image = imageOptional.get();
                return new ImageMetadataDto(image.getId(), image.getOriginalFilename(), image.getStoredFilename(), image.getUploadDateTime());
            }
            else{
                throw new RecordNotFoundException("afbeelding niet gevonden in database");
            }
    }
//todo BUG check gebruik van deze method door plant service want ik weet niet of het nu goed werkt
    public void deleteImageById(Long id, Boolean isQuiet) {
        //isQuiet == false: indicates that an error can be thrown that interrupts the flow of the program
        //isQuiet == true: an error message is printed and the program continues

        //before the image is deleted from the database, the original filename is needed to delete the file from the filesystem.
            Optional<Image> imageOptional = imageRepository.findById(id);
            String storedFilename;
            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();
                storedFilename = image.getStoredFilename();
                //delete metadata from database
                imageRepository.deleteById(id);

            } else if (!isQuiet) {
                throw new RecordNotFoundException();
            }else{
                return;
            }

        try {
            if(storedFilename!=null){
                //determine filepath and delete file from filesystem if it exists
                Path filePath = Paths.get(uploadDirectory).resolve(storedFilename).normalize();
                Files.deleteIfExists(filePath);
            }

        } catch (IOException e) {
            if(!isQuiet){
            throw new RecordNotFoundException("Bestand niet gevonden, dus ook niet verwijderd "+storedFilename);
            }
            else{
                System.out.println("bestand niet gevonden met id: "+id);
            }
        }
    }


    //local Helpers
    private void createImageUploadDirectory() {//create upload directory if it does not exist yet
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Upload directory aangemaakt: "+ uploadDirectory);
            } catch (IOException e) {
                throw new RuntimeException("Kon upload-map niet aanmaken", e);
            }
        } else {
            System.out.println("Upload directory bestaat al: "+ uploadDirectory);
        }
    }

    private void checkUploadedImage(MultipartFile file){//for POST and PUT requests the validation of the uploaded file is identical
        if(file.isEmpty()){
            throw new IllegalArgumentException("Het bestand is leeg! probeer het opnieuw");
        }
        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType.toLowerCase())) {
            String errorMessage = "Ongeldig bestandstype: Alleen .png, .jpeg, jpg, .webp en .svg zijn toegestaan.";
            throw new InvalidImageTypeException(errorMessage);
        }
    }

}
