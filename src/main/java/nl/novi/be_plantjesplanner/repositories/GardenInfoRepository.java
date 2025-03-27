package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.GardenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenInfoRepository extends JpaRepository<GardenInfo, Long> {
}
