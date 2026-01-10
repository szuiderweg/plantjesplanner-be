package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.Locale;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.DesignRepository;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesignService {
    private final DesignRepository designRepository;
    private final UserRepository userRepository;

    public DesignService(DesignRepository designRepository, UserRepository userRepository){
        this.designRepository = designRepository;
        this.userRepository = userRepository;
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

    @Transactional
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

}
