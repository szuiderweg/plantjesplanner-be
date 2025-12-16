package nl.novi.be_plantjesplanner.services.image;

import nl.novi.be_plantjesplanner.repositories.ImageRepository;
import org.springframework.stereotype.Service;
@Service("plantImageService")
public class PlantImageService extends AbstractImageService {

    public PlantImageService(ImageRepository imageRepository){
        super(imageRepository,"plant_avatars");
    }
}
