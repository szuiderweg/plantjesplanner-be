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
    private Boolean openGroundOnly;

    public Long getId() {
        return id;
    }
    public Sunlight getSunlight() {
        return sunlight;
    }

    public void setSunlight(Sunlight sunlight) {
        this.sunlight = sunlight;
    }

    public Moisture getMoisture() {
        return moisture;
    }

    public void setMoisture(Moisture moisture) {
        this.moisture = moisture;
    }

    public WindTolerance getWindTolerance() {
        return windTolerance;
    }

    public void setWindTolerance(WindTolerance windTolerance) {
        this.windTolerance = windTolerance;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public Boolean getOpenGroundOnly() {
        return openGroundOnly;
    }

    public void setOpenGroundOnly(Boolean openGroundOnly) {
        this.openGroundOnly = openGroundOnly;
    }
}
