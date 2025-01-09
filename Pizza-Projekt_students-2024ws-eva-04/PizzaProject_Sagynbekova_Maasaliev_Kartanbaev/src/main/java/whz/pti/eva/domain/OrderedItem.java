package whz.pti.eva.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedItem extends BaseEntity<Long>{

    long pizzaID;
    String name;
    int quantity;
    String userId;

    @ManyToOne
    @JoinColumn(name = "ordered_id", nullable = false)
    Ordered ordered;

    public long getPizzaID() {
        return pizzaID;
    }    

    public void setPizzaID(long pizzaID) {    
        this.pizzaID = pizzaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Ordered getOrdered() {
        return ordered;
    }

    public void setOrdered(Ordered ordered) {
        this.ordered = ordered;
    }

}
