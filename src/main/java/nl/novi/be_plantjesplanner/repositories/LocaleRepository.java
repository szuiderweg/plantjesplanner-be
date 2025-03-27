package nl.novi.be_plantjesplanner.repositories;

import nl.novi.be_plantjesplanner.entities.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocaleRepository extends JpaRepository<Locale, Long> {
}
