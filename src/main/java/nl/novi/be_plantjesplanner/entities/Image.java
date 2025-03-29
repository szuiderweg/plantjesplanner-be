package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

import java.nio.file.Path;
import java.time.ZonedDateTime;
//TODO wanneer image uploader werkt, overbodige properties en getters+setters verwijderen
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private String originalFilename;
    @Column(unique = true)
    private String storedFilename;
//    private Path filepath;
    private final ZonedDateTime uploadDateTime;

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

}
