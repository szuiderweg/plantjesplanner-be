package nl.novi.be_plantjesplanner.entities;

import jakarta.persistence.*;

@Entity
public class BloomingMonths {
//    TODO in Bloomingperiod service de logica stoppen om van de losse kolommen een mooie array maken voor de frontwend kalender en een string met de namen van de bloeimaanden erin voor postman apicalls. die gaan in de bloeiDTO
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private boolean januari = false;

    @Column(nullable = false)
    private boolean februari = false;

    @Column(nullable = false)
    private boolean maart = false;

    @Column(nullable = false)
    private boolean april = false;

    @Column(nullable = false)
    private boolean mei = false;

    @Column(nullable = false)
    private boolean juni = false;

    @Column(nullable = false)
    private boolean juli = false;

    @Column(nullable = false)
    private boolean augustus = false;

    @Column(nullable = false)
    private boolean september = false;

    @Column(nullable = false)
    private boolean oktober = false;

    @Column(nullable = false)
    private boolean november = false;

    @Column(nullable = false)
    private boolean december = false;

    public Long getId() {
        return id;
    }

    public boolean isJanuari() {
        return januari;
    }

    public void setJanuari(boolean januari) {
        this.januari = januari;
    }

    public boolean isFebruari() {
        return februari;
    }

    public void setFebruari(boolean februari) {
        this.februari = februari;
    }

    public boolean isMaart() {
        return maart;
    }

    public void setMaart(boolean maart) {
        this.maart = maart;
    }

    public boolean isApril() {
        return april;
    }

    public void setApril(boolean april) {
        this.april = april;
    }

    public boolean isMei() {
        return mei;
    }

    public void setMei(boolean mei) {
        this.mei = mei;
    }

    public boolean isJuni() {
        return juni;
    }

    public void setJuni(boolean juni) {
        this.juni = juni;
    }

    public boolean isJuli() {
        return juli;
    }

    public void setJuli(boolean juli) {
        this.juli = juli;
    }

    public boolean isAugustus() {
        return augustus;
    }

    public void setAugustus(boolean augustus) {
        this.augustus = augustus;
    }

    public boolean isSeptember() {
        return september;
    }

    public void setSeptember(boolean september) {
        this.september = september;
    }

    public boolean isOktober() {
        return oktober;
    }

    public void setOktober(boolean oktober) {
        this.oktober = oktober;
    }

    public boolean isNovember() {
        return november;
    }

    public void setNovember(boolean november) {
        this.november = november;
    }

    public boolean isDecember() {
        return december;
    }

    public void setDecember(boolean december) {
        this.december = december;
    }
}
