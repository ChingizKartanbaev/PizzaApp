package whz.pti.eva.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whz.pti.eva.domain.Item;
import whz.pti.eva.repository.ItemRepository;
import whz.pti.eva.service.ItemService;

import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItemById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));
    }

    @Override
    @Transactional
    public void updateItemQuantity(UUID itemId, int newQuantity) {
        Item item = getItemById(itemId);
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        item.setQuantity(newQuantity);
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeItem(UUID itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }
}
