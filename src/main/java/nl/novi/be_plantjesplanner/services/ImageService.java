package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.repositories.ImageRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDirectory;

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
        this.uploadDirectory = Paths.get(System.getProperty("user.dir"),"../uploads").toString();
        createUploadDirectory();//create upload folder

    }
    private void createUploadDirectory() {
        Path path = Paths.get(uploadDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Upload directory aangemaakt op: " + path.toAbsolutePath());
            } catch (IOException e) { //TODO errors netter afhandelen met error controller
                throw new RuntimeException("Kon upload-map niet aanmaken!", e);
            }
        } else {
            System.out.println("Upload directory bestaat al: " + path.toAbsolutePath());
        }
    }

    //getters and setters
    public String getUploadDirectory() {
        return uploadDirectory;
    }
}
