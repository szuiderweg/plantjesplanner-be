package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageDownloadFileDto;
import nl.novi.be_plantjesplanner.dtos.ImageMetadataDto;
import nl.novi.be_plantjesplanner.entities.ImageMetadata;
import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.exceptions.UnreadableFileException;
import nl.novi.be_plantjesplanner.repositories.ImageRepository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static nl.novi.be_plantjesplanner.helpers.FileChecker.checkMediaType;

public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDirectory;

    public ImageService(ImageRepository imageRepository, String folderName){
        this.imageRepository = imageRepository;
        //set up the folder in local filesystem that will store imagefiles in the same directory as the Plantjesplanner app.
        String uploadDir = "../"+folderName;
        this.uploadDirectory = Paths.get(System.getProperty("user.dir"),uploadDir).toString();
        createImageUploadDirectory();
    }

    //saves imagefile in local filesystem and creates, saves and returns image metadata
    public ImageMetadata saveImage(MultipartFile uploadedImage)
    {
       String originalFilename = uploadedImage.getOriginalFilename();
       try{
           String storedFilename = System.currentTimeMillis()+"_"+originalFilename;//make the storedfilename unique by adding the currrent time to the filename. This is to prevent confusion when downloading images with identical original names
           Path filepath = Paths.get(uploadDirectory, storedFilename);
           Files.copy(uploadedImage.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);//save the uploadedImage in filesystem with modified filename
           ImageMetadata savedImageMetadata = new ImageMetadata(originalFilename, storedFilename);
           imageRepository.save(savedImageMetadata);
           return savedImageMetadata;
       }catch(IOException e){
           throw new RuntimeException("opslaan mislukt",e);
       }
    }

    //retrieves old image file, replaces with new file and updates+returns  the image metadata
    public ImageMetadata updateImage(MultipartFile newFile, String requestedFileName) {
        //retrieve filepath of existing file
        Path oldFilePath = Paths.get(uploadDirectory, requestedFileName);
        if (!Files.exists(oldFilePath)) {
            throw new RecordNotFoundException("Afbeelding niet gevonden: " + requestedFileName);
        }
        //generate new unique filename and path for updated file
        String originalFileName = newFile.getOriginalFilename();
        String newStoredFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path newFilePath = Paths.get(uploadDirectory, newStoredFileName);
        ImageMetadata newImageMetadata = new ImageMetadata();
        try {
            //delete the old file first
            Files.deleteIfExists(oldFilePath);
            //save new file
            Files.copy(newFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

            //update metadata in database

            Optional<ImageMetadata> imageOptional = imageRepository.findByStoredFilename(requestedFileName);
            if (imageOptional.isPresent()) {
                newImageMetadata = imageOptional.get();
                newImageMetadata.setStoredFilename(newStoredFileName);
                newImageMetadata.setOriginalFilename(originalFileName);
                newImageMetadata.setUploadDateTime();
                imageRepository.save(newImageMetadata);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fout bij updaten van bestand: " + requestedFileName);
        }
        return newImageMetadata;
    }


    //retrieves an image file and returns it in a DTO together with its mediatype
    public ImageDownloadFileDto getImageDto(String fileName){
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
                if (mediaTypeOptional.isEmpty() || !checkMediaType(mediaTypeOptional.get())) {
                throw new InvalidImageTypeException("Ongeldig bestandstype: "+mediaTypeOptional.get());
                }
               return new ImageDownloadFileDto(resource, mediaTypeOptional.get());
            }catch (IOException e) {
            throw new RuntimeException("Fout bij ophalen van bestand: " + fileName, e);
        }

    }

    public ImageMetadata getImageMetadata(String fileName){
            //retrieve metadata from database
            Optional<ImageMetadata> imageOptional = imageRepository.findByStoredFilename(fileName);
            if(imageOptional.isPresent()) {
                return imageOptional.get();
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
            Optional<ImageMetadata> imageOptional = imageRepository.findById(id);
            String storedFilename;
            if (imageOptional.isPresent()) {
                ImageMetadata image = imageOptional.get();
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



}
