package nl.novi.be_plantjesplanner.entities;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
//TODO wanneer image uploader werkt, overbodige properties en getters+setters verwijderen
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFilename;
    @Column(unique = true)
    private String storedFilename;
    private ZonedDateTime uploadDateTime;

    @OneToOne(mappedBy = "plantAvatar")
    private Plant plant;


    public Image(){
        uploadDateTime = ZonedDateTime.now();
    }
    public Image(String originalFilename, String storedFilename) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
//        this.filepath = filepath;
        uploadDateTime = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoredFilename() {
        return storedFilename;
    }

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }

    public ZonedDateTime getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime() {
        this.uploadDateTime = ZonedDateTime.now();
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
