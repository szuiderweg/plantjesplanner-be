//package nl.novi.be_plantjesplanner.services;
//
//import nl.novi.be_plantjesplanner.dtos.SelectedPlantDto;
//import nl.novi.be_plantjesplanner.entities.Plant;
//import nl.novi.be_plantjesplanner.entities.SelectedPlant;
//import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
//import nl.novi.be_plantjesplanner.helpers.Mapper;
//import nl.novi.be_plantjesplanner.repositories.PlantRepository;
//import nl.novi.be_plantjesplanner.repositories.SelectedPlantRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class SelectedPlantService {
//    private final SelectedPlantRepository selectedPlantRepository;
//    private final PlantRepository plantRepository;
//
//    public SelectedPlantService(SelectedPlantRepository selectedPlantRepository, PlantRepository plantRepository){
//        this.selectedPlantRepository = selectedPlantRepository;
//        this.plantRepository = plantRepository;
//    }
//
//    //corresponds to Post request of a selectedplant in the selectedplant controller
//    //a plant is only assigned to a selected plant once during the creation of the selectedplant and a plant is mandatory for a new selectedplant. only the quantity can be changed or the entire selectedplant can be deleted and replaced.
//    public SelectedPlantDto addSelectedPlant(SelectedPlantDto spDto, Long plantId) //todo enforce that a plant can only be selected once per design. doe check in save selectedplant
//    {
//        //convert selectedplantdto to selectedplant
//        SelectedPlant newSelectedPlant = mapFromSelectedPlantDto(spDto);
//        //look up plant by plantid from plantrepository
//        Optional<Plant> assignedPlant = plantRepository.findById(plantId);
//
//        if(assignedPlant.isPresent()){ //use selectedplant.setplant to assign plant to selectedplant
//            newSelectedPlant.setPlant(assignedPlant.get());
//        }
//        else{
//            //throw error if not plant not found with this id is not found
//            throw new RecordNotFoundException("plant met id "+plantId+" niet gevonden!");
//        }
//        //save selectedplant to repository (including plant)
//
//        newSelectedPlant = selectedPlantRepository.save(newSelectedPlant);
//        //convert saved selectedplant to dto and return it
//        return mapToSelectedPlantDto(newSelectedPlant);
//    }
//
//    //updateSelectedPlantById
//    public SelectedPlantDto updateSelectedPlantById(SelectedPlantDto selectedPlantDto, Long id){
//        SelectedPlant selectedPlantUpdate = mapFromSelectedPlantDto(selectedPlantDto);
//
//        Optional<SelectedPlant> selectedPlantOptional = selectedPlantRepository.findById(id);
//        if(selectedPlantOptional.isPresent()){
//            SelectedPlant foundSelectedPlant = selectedPlantOptional.get();
//            //only the quantity of an existing selectedPlant can be updated
//            foundSelectedPlant.setQuantity(selectedPlantUpdate.getQuantity());
//            selectedPlantRepository.save(foundSelectedPlant);
//            return mapToSelectedPlantDto(foundSelectedPlant);
//        }
//        else{
//            throw new RecordNotFoundException("geen uitgekozen plant gevonden met id "+id+" , dus ook niet aangepast");
//        }
//    }
//
//    //delete SelectedPlant by Id
//    public void deleteSelectedPlantById(Long id){
//        selectedPlantRepository.deleteById(id);
//    }
//
//    //get Selectedplant by id
//    public SelectedPlantDto getSelectedPlantById(Long id){
//        Optional<SelectedPlant> selectedPlantOptional = selectedPlantRepository.findById(id);
//        if(selectedPlantOptional.isPresent()){
//            return mapToSelectedPlantDto(selectedPlantOptional.get());
//        }
//        else{
//            throw new RecordNotFoundException("geen lievelingsplant gevonden met id "+id);
//        }
//    }
//
//    //GET all selected plants
//    public List<SelectedPlantDto> getAllSelectedPlants(){
//        List<SelectedPlant>  foundSelectedPlants = selectedPlantRepository.findAll();
//
//        List<SelectedPlantDto> foundSelectedPlantsDto = new ArrayList<>();
//        for(SelectedPlant selectedPlant : foundSelectedPlants){
//            SelectedPlantDto foundSelectedPlantDto = mapToSelectedPlantDto(selectedPlant);
//            foundSelectedPlantsDto.add(foundSelectedPlantDto);
//        }
//        return foundSelectedPlantsDto;
//    }
//
//
//}
