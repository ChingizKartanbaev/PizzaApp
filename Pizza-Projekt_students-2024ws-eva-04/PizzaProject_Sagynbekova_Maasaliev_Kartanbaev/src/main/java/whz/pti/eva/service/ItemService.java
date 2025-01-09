package whz.pti.eva.service;

import whz.pti.eva.domain.Item;

import java.util.UUID;

public interface ItemService {
    Item getItemById(UUID itemId);
    void updateItemQuantity(UUID itemId, int newQuantity); 
    void removeItem(UUID itemId);
}
