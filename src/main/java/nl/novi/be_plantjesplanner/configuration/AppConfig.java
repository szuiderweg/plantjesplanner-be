package nl.novi.be_plantjesplanner.configuration;

import nl.novi.be_plantjesplanner.repositories.ImageRepository;
import nl.novi.be_plantjesplanner.services.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ImageService imageService(ImageRepository imageRepository){
        String folderName = "uploads";
        return new ImageService(imageRepository, folderName);
    }
}
