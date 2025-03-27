package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.MoodboardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodboardItemRepository extends JpaRepository<MoodboardItem, Long> {
}
