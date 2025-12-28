package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
