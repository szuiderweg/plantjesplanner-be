package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.DesignRepository;
import org.springframework.stereotype.Service;

@Service
public class DesignService {
    private final DesignRepository designRepository;

    public DesignService(DesignRepository designRepository){
        this.designRepository = designRepository;
    }

    public Design getDesignById(Long id){
        Design design = designRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("geen tuinontwerp gevonden"));
        return design;
    }
}
