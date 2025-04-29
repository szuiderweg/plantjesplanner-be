package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import nl.novi.be_plantjesplanner.enumerations.Moisture;
import nl.novi.be_plantjesplanner.enumerations.Sunlight;
import nl.novi.be_plantjesplanner.enumerations.WindTolerance;

@Entity
@Table(name = "locales")
public class Locale{
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Sunlight sunlight;
    @Enumerated(EnumType.STRING)
    private Moisture moisture;
    @Enumerated(EnumType.STRING)
    private WindTolerance windTolerance;
    private String soilType;
    private Boolean openGroundOnly;

    @OneToOne(mappedBy = "locale")
    private Plant plant;

//    @OneToOne(mappedBy = "locale")
//    private Design design;


    //getters and setters
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

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

//    public Design getDesign() {
//        return design;
//    }
//
//    public void setDesign(Design design) {
//        this.design = design;
//    }
}
