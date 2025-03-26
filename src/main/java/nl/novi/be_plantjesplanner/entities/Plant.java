package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import nl.novi.be_plantjesplanner.enumerations.ColorGroup;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue
    private Long id;
    // todo set up errorhandling of errors caused by non-unique plantnames
    @Column(unique = true)
    private String dutchName;
    private String latinName;
    @Length(max = 1000)
// maximum length of description is 1000 characters. default length of 255 characters was too short > probably needs finetuning
    private String description;
    private Double height;//typical vertical size [meters]
    private Double footprint;//typical area needed by the plant [meters^2]

    private String bloomColorHex;//hex value of typical flower color for use in the frontend
    private ColorGroup bloomColorGroup;//enum used to search for plants by general flower color
    private Locale locale;//TODO fix error with relation to Locale
    private BloomingMonths bloomingMonths;//TODO fix error with relation
    private Image image; //TODO fix error with relation


    public Long getId() {// getter only, since id is automatically generated
        return id;
    }

    public String getDutchName() {
        return dutchName;
    }

    public void setDutchName(String dutchName) {
        this.dutchName = dutchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getFootprint() {
        return footprint;
    }

    public void setFootprint(Double footprint) {
        this.footprint = footprint;
    }

    public String getBloomColorHex() {
        return bloomColorHex;
    }

    public void setBloomColorHex(String bloomColorHex) {
        this.bloomColorHex = bloomColorHex;
    }

    public ColorGroup getBloomColorGroup() {
        return bloomColorGroup;
    }

    public void setBloomColorGroup(ColorGroup bloomColorGroup) {
        this.bloomColorGroup = bloomColorGroup;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public BloomingMonths getBloomingMonths() {
        return bloomingMonths;
    }

    public void setBloomingMonths(BloomingMonths bloomingMonths) {
        this.bloomingMonths = bloomingMonths;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
