package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.SelectedPlant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedPlantRepository extends JpaRepository<SelectedPlant, Long> {
}
