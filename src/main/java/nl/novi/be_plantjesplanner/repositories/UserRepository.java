package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>{
}
