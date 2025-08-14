package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageMetadata, Long> {
    Optional<ImageMetadata> findByStoredFilename(String requestedFilename);

    void deleteByStoredFilename(String storedFilename);
}
