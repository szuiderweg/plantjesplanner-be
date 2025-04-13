package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByStoredFilename(String requestedFilename);

    void deleteByStoredFilename(String storedFilename);
}
