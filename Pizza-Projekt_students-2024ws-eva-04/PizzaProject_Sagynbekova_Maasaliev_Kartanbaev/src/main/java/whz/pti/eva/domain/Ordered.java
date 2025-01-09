package whz.pti.eva.domain;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordered extends BaseEntity<Long>{

    int numberOfItems;
    String userId;

    @OneToMany(mappedBy = "ordered", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OrderedItem> orderedItems;

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setItems(Set<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;    
    }

}
