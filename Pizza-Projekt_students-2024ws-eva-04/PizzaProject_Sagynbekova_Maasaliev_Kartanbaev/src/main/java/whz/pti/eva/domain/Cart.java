package whz.pti.eva.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntity<Long> {

    int quantity;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", unique = true)
    Customer customer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "cart_item",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    private String sessionId;
    
    private LocalDateTime createdAt; 
    
    public Cart() {}

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public void addItem(Item item) {
        for (Item existingItem : items) {
            if (existingItem.getPizza().getId().equals(item.getPizza().getId()) &&
                existingItem.getPizzaSize() == item.getPizzaSize()) {
                existingItem.increaseQuantity(item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.removeIf(existingItem ->
            existingItem.getPizza().getId().equals(item.getPizza().getId()) &&
            existingItem.getPizzaSize() == item.getPizzaSize()
        );
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(Item::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}
