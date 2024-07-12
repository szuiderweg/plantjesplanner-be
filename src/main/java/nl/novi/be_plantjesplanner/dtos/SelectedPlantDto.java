package nl.novi.be_plantjesplanner.dtos;

public class SelectedPlantDto{
    private Long id;
    private Integer quantity;

    //    todo: configure relation with plant entity. for now this string is a placeholder.
    private String plant;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}
