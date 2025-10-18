package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "moodboard_items")
public class MoodboardItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
       private String caption;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moodboardItemImage_id")
       private ImageMetadata image;

    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;

    public Long getId() {
        return id;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

//    public ImageMetadata getImage() {
//        return image;
//    }
//
//    public void setImage(ImageMetadata image) {
//        this.image = image;
//    }
}

