package nl.novi.be_plantjesplanner.configuration;

import nl.novi.be_plantjesplanner.repositories.ImageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "plantImageService")
    public ImageService plantImageService(ImageRepository imageRepository){
        return new ImageService(imageRepository, "plant_avatars");
    }

//    @Bean(name = "moodboardImageService")
//    public ImageService moodboardImageService(ImageRepository imageRepository){
//        return new ImageService(imageRepository, "moodboard_items");
//    }
}
