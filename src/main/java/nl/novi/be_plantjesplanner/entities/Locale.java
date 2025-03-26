package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import nl.novi.be_plantjesplanner.enumerations.Moisture;
import nl.novi.be_plantjesplanner.enumerations.Sunlight;
import nl.novi.be_plantjesplanner.enumerations.WindTolerance;

@Entity
@Table(name = "locales")
public class Locale{
    @Id
    @GeneratedValue
    private Long id;
    private Sunlight sunlight;
    private Moisture moisture;
    private WindTolerance windTolerance;
    private String soilType;

}
