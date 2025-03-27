package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<Design, Long> {
}
