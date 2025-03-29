package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Image;
import nl.novi.be_plantjesplanner.repositories.ImageRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDirectory;

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
        this.uploadDirectory = Paths.get(System.getProperty("user.dir"),"../uploads").toString();
        createUploadDirectory();

    }

    public String saveImage(MultipartFile uploadedImage)
    {
       if(uploadedImage.isEmpty()){
           throw new IllegalArgumentException("het bestand is leeg, probeer het nog eens!");
       }
       String originalFilename = uploadedImage.getOriginalFilename();
       try{
           String storedFilename = System.currentTimeMillis()+"_"+originalFilename;//make the storedfilename unique by adding the currrent time to the filename. This is to prevent confusion when downloading the images
           Path filepath = Paths.get(uploadDirectory, storedFilename);
           Files.copy(uploadedImage.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);
           Image savedImage = new Image(originalFilename, storedFilename);
           imageRepository.save(savedImage);
           return originalFilename;//return save-message only a message if save is successful
       }catch(IOException e){
           throw new RuntimeException("opslaan mislukt",e);//TODO error afhandelen door error controller
       }
    }

    private void createUploadDirectory() {//create upload directory if it does not exist yet
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);

            } catch (IOException e) { //TODO errors netter afhandelen met error controller
                throw new RuntimeException("Kon upload-map niet aanmaken", e);
            }
        } else {
            System.out.println("Upload directory bestaat al.");
        }
    }

    //getters and setters
    public String getUploadDirectory() {
        return uploadDirectory;
    }
}
