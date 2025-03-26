package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity
@Table(name = "moodboarditems")
public class MoodboardItem {
        @Id
        @GeneratedValue
        private Long id;
       private String caption;
        private Image image;//TODO error fixen door relatie te leggen

    public Long getId() {
        return id;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

