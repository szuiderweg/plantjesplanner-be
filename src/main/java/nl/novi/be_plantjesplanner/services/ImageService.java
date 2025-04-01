package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.ImageDto;
import nl.novi.be_plantjesplanner.entities.Image;
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
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDirectory;
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg", "image/jpg","image/tiff", "image/svg+xml" );

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
        this.uploadDirectory = Paths.get(System.getProperty("user.dir"),"../uploads").toString();
        createImageUploadDirectory();
    }

    private void createImageUploadDirectory() {//create upload directory if it does not exist yet
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Upload directory aangemaakt:"+ uploadDirectory);
            } catch (IOException e) {
                throw new RuntimeException("Kon upload-map niet aanmaken", e);
            }
        } else {
            System.out.println("Upload directory bestaat al.");
        }
    }


    public String saveImage(MultipartFile uploadedImage)
    {
       if(uploadedImage.isEmpty()){
           throw new IllegalArgumentException("het bestand is leeg, probeer het nog eens!");
       }
        String contentType = uploadedImage.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType.toLowerCase())) {
            throw new InvalidImageTypeException("Ongeldig bestandstype: Alleen PNG, JPG, JPEG, TIFF en SVG zijn toegestaan.");
        }

       String originalFilename = uploadedImage.getOriginalFilename();
       try{
           String storedFilename = System.currentTimeMillis()+"_"+originalFilename;//make the storedfilename unique by adding the currrent time to the filename. This is to prevent confusion when downloading images with identical original names
           Path filepath = Paths.get(uploadDirectory, storedFilename);
           Files.copy(uploadedImage.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);
           Image savedImage = new Image(originalFilename, storedFilename);
           imageRepository.save(savedImage);
           return "Afbeelding is opgeslagen als "+ storedFilename;//return save-message only a message if save is successful.
       }catch(IOException e){
           throw new RuntimeException("opslaan mislukt",e);//TODO error afhandelen door error controller
       }
    }

    public ImageDto getImageDto(String fileName){

           try {
               Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
               Resource resource = new UrlResource(filePath.toUri());//retrieve file based on unique filename and upload directory location

               if (!resource.exists() || !resource.isReadable()) {
                   throw new RecordNotFoundException();
               }
               else if (!resource.isReadable()) {
                    throw new UnreadableFileException("dit bestand is onleesbaar: "+fileName);
               }
                //MIME-type ophalen en controleren
                Optional<MediaType> mediaTypeOptional = MediaTypeFactory.getMediaType(resource);
                if (mediaTypeOptional.isEmpty() || !ALLOWED_TYPES.contains(mediaTypeOptional.get().toString())) {
                throw new InvalidImageTypeException("Ongeldig bestandstype: "+mediaTypeOptional.get().toString());
                }
                return new ImageDto(resource, mediaTypeOptional.get());
            }catch (IOException e) {
            throw new RuntimeException("Fout bij ophalen van bestand: " + fileName, e);
        }

    }


}
