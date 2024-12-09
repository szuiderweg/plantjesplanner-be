package nl.novi.be_plantjesplanner.dtos;

import org.hibernate.validator.constraints.Length;

public class PlantDto {
    private Long id;
    private String dutchName;
//    private String latinName;
//    @Length(max = 1000)// maximum length of description is 1000 characters.
//    private String plantDescription;
//    private String waterPreference;
//    private String sunPreference;
//    private String windTolerance;
//    private String bloomColour;
//    private Double height;//vertical size [meters]
//    private Double footprint;//horizontal size [meters^2]
//    private Boolean pottedPlant;//plant can survive and thrive  in a pot
//    private String soilPreference;

    //getters &setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDutchName() {
        return dutchName;
    }

    public void setDutchName(String dutchName) {
        this.dutchName = dutchName;
    }

//    public String getLatinName() {
//        return latinName;
//    }
//
//    public void setLatinName(String latinName) {
//        this.latinName = latinName;
//    }
//
//    public String getPlantDescription() {
//        return plantDescription;
//    }
//
//    public void setPlantDescription(String plantDescription) {
//        this.plantDescription = plantDescription;
//    }
//
//    public String getWaterPreference() {
//        return waterPreference;
//    }
//
//    public void setWaterPreference(String waterPreference) {
//        this.waterPreference = waterPreference;
//    }
//
//    public String getSunPreference() {
//        return sunPreference;
//    }
//
//    public void setSunPreference(String sunPreference) {
//        this.sunPreference = sunPreference;
//    }
//
//    public String getWindTolerance() {
//        return windTolerance;
//    }
//
//    public void setWindTolerance(String windTolerance) {
//        this.windTolerance = windTolerance;
//    }
//
//    public String getBloomColour() {
//        return bloomColour;
//    }
//
//    public void setBloomColour(String bloomColour) {
//        this.bloomColour = bloomColour;
//    }
//
//    public Double getHeight() {
//        return height;
//    }
//
//    public void setHeight(Double height) {
//        this.height = height;
//    }
//
//    public Double getFootprint() {
//        return footprint;
//    }
//
//    public void setFootprint(Double footprint) {
//        this.footprint = footprint;
//    }
//
//    public Boolean getPottedPlant() {
//        return pottedPlant;
//    }
//
//    public void setPottedPlant(Boolean pottedPlant) {
//        this.pottedPlant = pottedPlant;
//    }
//
//    public String getSoilPreference() {
//        return soilPreference;
//    }
//
//    public void setSoilPreference(String soilPreference) {
//        this.soilPreference = soilPreference;
//    }
}
