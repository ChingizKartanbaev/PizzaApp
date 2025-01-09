package whz.pti.eva.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import whz.pti.eva.domain.enums.PizzaSize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item extends BaseEntity<UUID> {

    int quantity; 
    
    @ManyToOne
    Pizza pizza;

    @Enumerated(EnumType.STRING)
    PizzaSize pizzaSize; 

    @ManyToMany(mappedBy = "items")
    List<Cart> carts = new ArrayList<>();

    public Item() {}

    public Item(int quantity, Pizza pizza, PizzaSize pizzaSize) {
        this.quantity = quantity;
        this.pizza = pizza;
        this.pizzaSize = pizzaSize;
    }

    public UUID getItemId() {
        return id;
    }

    public void setItemId(UUID id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal price = switch (pizzaSize) {
            case SMALL -> pizza.getPriceSmall();
            case MEDIUM -> pizza.getPriceMedium();
            case LARGE -> pizza.getPriceLarge();
        };
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity - amount <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 1");
        }
        this.quantity -= amount;
    }
}
