package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{
    Optional<User> findByUsername(String username);
}
