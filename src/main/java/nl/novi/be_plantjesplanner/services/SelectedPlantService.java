package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.SelectedPlantDto;
import nl.novi.be_plantjesplanner.entities.SelectedPlant;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.SelectedPlantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SelectedPlantService {
    private final SelectedPlantRepository selectedPlantRepository;
    public SelectedPlantService(SelectedPlantRepository selectedPlantRepository){
        this.selectedPlantRepository = selectedPlantRepository;
    }

    //corresponds to Post request of a selectedplant in the selectedplant controller
    public SelectedPlantDto saveSelectedPlant(SelectedPlantDto spDto)
    {
        SelectedPlant savedSelectedPlant = selectedPlantRepository.save(mapFromSelectedPlantDto(spDto));
        return mapToSelectedPlantDto(savedSelectedPlant);
    }

    //updateSelectedPlantById
    public SelectedPlantDto updateSelectedPlantById(SelectedPlantDto selectedPlantDto, Long id){
        SelectedPlant selectedPlantUpdate = mapFromSelectedPlantDto(selectedPlantDto);

        Optional<SelectedPlant> selectedPlantOptional = selectedPlantRepository.findById(id);
        if(selectedPlantOptional.isPresent()){
            SelectedPlant foundSelectedPlant = selectedPlantOptional.get();
            //only the quantity of an existing selected plant can be updated
            foundSelectedPlant.setQuantity(selectedPlantUpdate.getQuantity());
            selectedPlantRepository.save(foundSelectedPlant);
            return mapToSelectedPlantDto(foundSelectedPlant);
        }
        else{
            throw new RecordNotFoundException("geen uitgekozen plant gevonden met id "+id+" , dus ook niet aangepast");
        }

        //todo: functie als quantity <1 , dan delete de selected plant
    }





    //DTO mappers
    private SelectedPlantDto mapToSelectedPlantDto(SelectedPlant selectedPlant){
        SelectedPlantDto selectedPlantDto = new SelectedPlantDto();
        selectedPlantDto.setId(selectedPlant.getId());
        selectedPlantDto.setQuantity(selectedPlant.getQuantity());
        selectedPlantDto.setPlant(selectedPlant.getPlant());

        return selectedPlantDto;
    }

    private SelectedPlant mapFromSelectedPlantDto(SelectedPlantDto selectedPlantDto){
        SelectedPlant selectedPlant = new SelectedPlant();
        //no setter for id since this will be generated automatically for a new selectedPlant
        selectedPlant.setQuantity(selectedPlantDto.getQuantity());
        selectedPlant.setPlant(selectedPlantDto.getPlant());

        return selectedPlant;
    }

}
