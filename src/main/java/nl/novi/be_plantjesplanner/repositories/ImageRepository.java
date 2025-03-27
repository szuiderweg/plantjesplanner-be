package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
