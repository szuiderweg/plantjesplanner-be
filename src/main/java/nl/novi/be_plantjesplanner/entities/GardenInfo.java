package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//TODO deze entiteit samenvoegen met Design
@Entity
@Table(name = "gardens")
public class GardenInfo {
    @Id
    @GeneratedValue
    private Long id;
    private Double area;// area available for plants in square meters
//    private Locale locale;//TODO error fixen door relatie te leggen

    public Long getId() {
        return id;
    }


    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

}
