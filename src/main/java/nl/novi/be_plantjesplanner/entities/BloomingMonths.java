package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

@Entity
public class BloomingMonths {
//    TODO in Bloomingperiod service de logica stoppen om van de losse kolommen een mooie array maken voor de frontwend kalender en een string met de namen van de bloeimaanden erin voor postman apicalls. die gaan in de bloeiDTO
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private boolean january = false;

    @Column(nullable = false)
    private boolean february = false;

    @Column(nullable = false)
    private boolean march = false;

    @Column(nullable = false)
    private boolean april = false;

    @Column(nullable = false)
    private boolean may = false;

    @Column(nullable = false)
    private boolean june = false;

    @Column(nullable = false)
    private boolean july = false;

    @Column(nullable = false)
    private boolean august = false;

    @Column(nullable = false)
    private boolean september = false;

    @Column(nullable = false)
    private boolean october = false;

    @Column(nullable = false)
    private boolean november = false;

    @Column(nullable = false)
    private boolean december = false;

    public Long getId() {
        return id;
    }

    public boolean bloomsInJanuary() {
        return january;
    }

    public void setJanuary(boolean january) {
        this.january = january;
    }

    public boolean bloomsInFebruary() {
        return february;
    }

    public void setFebruary(boolean february) {
        this.february = february;
    }

    public boolean bloomsInMarch() {
        return march;
    }

    public void setMarch(boolean march) {
        this.march = march;
    }

    public boolean bloomsInApril() {
        return april;
    }

    public void setApril(boolean april) {
        this.april = april;
    }

    public boolean bloomsInMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    public boolean bloomsInJune() {
        return june;
    }

    public void setJune(boolean june) {
        this.june = june;
    }

    public boolean bloomsInJuly() {
        return july;
    }

    public void setJule(boolean july) {
        this.july = july;
    }

    public boolean bloomsInAugust() {
        return august;
    }

    public void setAugust(boolean august) {
        this.august = august;
    }

    public boolean bloomsInSeptember() {
        return september;
    }

    public void setSeptember(boolean september) {
        this.september = september;
    }

    public boolean bloomsInOctober() {
        return october;
    }

    public void setOctober(boolean october) {
        this.october = october;
    }

    public boolean bloomsInNovember() {
        return november;
    }

    public void setNovember(boolean november) {
        this.november = november;
    }

    public boolean bloomsInDecember() {
        return december;
    }

    public void setDecember(boolean december) {
        this.december = december;
    }
}
