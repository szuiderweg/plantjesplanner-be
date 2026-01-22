package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.Locale;
import nl.novi.be_plantjesplanner.entities.Plant;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.DesignRepository;
import nl.novi.be_plantjesplanner.repositories.PlantRepository;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DesignService {
    private final DesignRepository designRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    public DesignService(DesignRepository designRepository, UserRepository userRepository, PlantRepository plantRepository){
        this.designRepository = designRepository;
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
    }

    public Design getDesignById(Long id){
        Design design = designRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("geen tuinontwerp gevonden"));
        return design;
    }

    public Design getDesignOfUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("gebruiker niet gevonden"));

        Design design = user.getDesign();
        if(design == null){
            throw new RecordNotFoundException("geen tuinontwerp gevonden voor gebruiker");
        }
        return design;
    }

    public Design updateDesignOfUser(String username, Design designUpdate){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("gebruiker niet gevonden"));
        Design design = user.getDesign();
        if(design == null){
            throw new RecordNotFoundException("geen tuinontwerp gevonden voor gebruiker");
        }

        //update simple design properties
        design.setTitle(designUpdate.getTitle());
        design.setGardenSize((designUpdate.getGardenSize()));

        //update design locale
      if (designUpdate.getLocale() != null)//if new locale data is present in the update
      {
          Locale localeUpdate = designUpdate.getLocale();

          if(design.getLocale() == null){
              design.setLocale(localeUpdate);//set a locale for the first time
          }else {
              Locale existingLocale = design.getLocale();
              existingLocale.setSunlight(designUpdate.getLocale().getSunlight());
              existingLocale.setMoisture(designUpdate.getLocale().getMoisture());
              existingLocale.setWindTolerance(designUpdate.getLocale().getWindTolerance());
              existingLocale.setSoilType(designUpdate.getLocale().getSoilType());
              existingLocale.setOpenGroundOnly(designUpdate.getLocale().getOpenGroundOnly());
          }
      }
        return design;
    }

    //helper methods related to SelectedPlants of Design

    //add a plant to a design
    public Design addPlantToDesign(String username, Long plantId, int amount){
        //find design that belongs to the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));
        Design design = user.getDesign();
        if (design == null){
            throw new RecordNotFoundException("geen tuinontwerp gevonden");
        }

        Plant plant =  plantRepository.findById(plantId)
                .orElseThrow(() -> new RecordNotFoundException("Plant niet gevonden"));

        design.addPlantToSelectedPlants(plant, amount);

        return design;
    }

    //change the amount of a selectedPlant
    public void updateSelectedPlantAmount(String username, Long selectedPlantId, int amount){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));

        Design design = user.getDesign();
        if (design == null){
            throw new RecordNotFoundException("geen tuinontwerp gevonden");
        }
        design.updateAmountSelectedPlants(selectedPlantId, amount);
    }

    //remove a selectedPlant from design
    public void removePlantFromMyDesign(String username, Long selectedPlantId){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));

        Design design = user.getDesign();
        if (design == null){
            throw new RecordNotFoundException("geen tuinontwerp gevonden");
        }
        design.removeSelectedPlant(selectedPlantId);

    }


}
