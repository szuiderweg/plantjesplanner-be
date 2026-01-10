package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.DesignRepository;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
