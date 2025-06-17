package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "designs")
public class Design{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "design")
    private User user;

    @OneToOne
    @JoinColumn(name = "locale_id")
    private Locale gardenInfo;

    private String gardenName;
    private Double plantedArea;
    private Integer totalAmountOfPlants;
    private Double plantMeterScore;

    @OneToMany(mappedBy= "design", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectedPlant> selectedPlants;

    @OneToMany(mappedBy= "design", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoodboardItem> moodboard;
//    private List<BloomingCalendar> bloomingCalendar;

    //TODO: een aantal properties (user, de lists, gardeninfo) moeten mogelijk geen setter hebben omdat de listitems op een andere plek worden gemaakt of dat de waarde final is.
    public Long getId() {
        return id;
    }

//getter and setters
}
