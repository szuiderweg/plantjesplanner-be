package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

@Entity
public class BloomingCalendar {
//    TODO in Bloomingperiod service de logica stoppen om van de losse kolommen een mooie array maken voor de frontend kalender en een string met de namen van de bloeimaanden erin voor postman apicalls. die gaan in de bloeiDTO
    //TODO for now no service and repo for this entity. It is managed by the parent Plant
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Boolean january = false;

    @Column(nullable = false)
    private Boolean february = false;

    @Column(nullable = false)
    private Boolean march = false;

    @Column(nullable = false)
    private Boolean april = false;

    @Column(nullable = false)
    private Boolean may = false;

    @Column(nullable = false)
    private Boolean june = false;

    @Column(nullable = false)
    private Boolean july = false;

    @Column(nullable = false)
    private Boolean august = false;

    @Column(nullable = false)
    private Boolean september = false;

    @Column(nullable = false)
    private Boolean october = false;

    @Column(nullable = false)
    private Boolean november = false;

    @Column(nullable = false)
    private Boolean december = false;

    @OneToOne(mappedBy = "bloomingMonths")
    private Plant plant;

    public Long getId() {
        return id;
    }

    public Boolean bloomsInJanuary() {
        return january;
    }

    public void setJanuary(Boolean january) {
        this.january = january;
    }

    public Boolean bloomsInFebruary() {
        return february;
    }

    public void setFebruary(Boolean february) {
        this.february = february;
    }

    public Boolean bloomsInMarch() {
        return march;
    }

    public void setMarch(Boolean march) {
        this.march = march;
    }

    public Boolean bloomsInApril() {
        return april;
    }

    public void setApril(Boolean april) {
        this.april = april;
    }

    public Boolean bloomsInMay() {
        return may;
    }

    public void setMay(Boolean may) {
        this.may = may;
    }

    public Boolean bloomsInJune() {
        return june;
    }

    public void setJune(Boolean june) {
        this.june = june;
    }

    public Boolean bloomsInJuly() {
        return july;
    }

    public void setJuly(Boolean july) {
        this.july = july;
    }

    public Boolean bloomsInAugust() {
        return august;
    }

    public void setAugust(Boolean august) {
        this.august = august;
    }

    public Boolean bloomsInSeptember() {
        return september;
    }

    public void setSeptember(Boolean september) {
        this.september = september;
    }

    public Boolean bloomsInOctober() {
        return october;
    }

    public void setOctober(Boolean october) {
        this.october = october;
    }

    public Boolean bloomsInNovember() {
        return november;
    }

    public void setNovember(Boolean november) {
        this.november = november;
    }

    public Boolean bloomsInDecember() {
        return december;
    }

    public void setDecember(Boolean december) {
        this.december = december;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
