package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
