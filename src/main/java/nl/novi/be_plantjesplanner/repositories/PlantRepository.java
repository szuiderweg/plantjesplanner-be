package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    // Check if a plant of the same dutchName already exists
    boolean existsByDutchNameIgnoreCase(String dutchName);

    List<Plant> findByDutchNameContainingIgnoreCase(String dutchName);
}
