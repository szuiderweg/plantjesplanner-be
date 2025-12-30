package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import nl.novi.be_plantjesplanner.enumerations.ColorGroup;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String dutchName ="";
    private String latinName ="";

    // maximum length of description is 1000 characters. default length of 255 characters was too short > probably needs finetuning
    @Length(max = 1000)
    private String description ="";
    private Double height = 0.00;//typical vertical size [meters]
    private Double footprint =0.00;//typical area needed by the plant [meters^2]

    private String bloomColorHex="#000000";//hex value of typical flower color for use in the frontend
    @Enumerated(EnumType.STRING)
    private ColorGroup bloomColorGroup=ColorGroup.GEEN;//enum used to search for plants by general (flower) color

    private Boolean published = false;//sets visibility of a plant:  with users Designer-role only see plant with published = true
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "locale_id", unique = true)
    private Locale locale;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blooming_calendar_id")
    private BloomingCalendar bloomingCalendar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plantavatar_id")
    private ImageMetadata plantAvatar;
    //

    public Long getId() {// getter only, since id is automatically generated
        return id;
    }

    public String getDutchName() {
        return dutchName;
    }

    public void setDutchName(String dutchName) {
        this.dutchName = dutchName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public BloomingCalendar getBloomingCalendar() {
        return bloomingCalendar;
    }

    public void setBloomingCalendar
            (BloomingCalendar bloomingCalendar) {
        this.bloomingCalendar = bloomingCalendar;
    }

    public ImageMetadata getPlantAvatar() {
        return plantAvatar;
    }

    public void setPlantAvatar(ImageMetadata plantAvatar) {
        this.plantAvatar = plantAvatar;
    }
}
