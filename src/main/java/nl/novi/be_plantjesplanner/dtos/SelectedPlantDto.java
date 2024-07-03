package nl.novi.be_plantjesplanner.dtos;

public class SelectedPlantDto{
    private Long id;
    private Integer quantity;
    //foreign key voor plant


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
}
