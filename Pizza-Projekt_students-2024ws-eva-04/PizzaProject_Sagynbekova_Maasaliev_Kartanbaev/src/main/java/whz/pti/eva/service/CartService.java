package whz.pti.eva.service;

import whz.pti.eva.domain.Cart;
import whz.pti.eva.domain.Item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartService {
    Cart getCartByCustomer(Long customerId); 
    void addItemToCart(Long customerId, Long pizzaId, String pizzaSize, int quantity); 
    void updateItemQuantity(Cart cart, UUID itemId, int newQuantity);
    void removeItemFromCart(Long customerId, UUID itemId); 
    void clearCart(Long customerId);
    Cart getOrCreateCart(String sessionId);
    void mergeCarts(String sessionId, Long userId);
    void cleanUpOldCarts();
    void addItemToAnonymousCart(String sessionId, Long pizzaId, String pizzaSize, int quantity);
    Optional<Cart> getCartBySessionId(String sessionId);
    void updateItemQuantityForCustomer(Long customerId, UUID itemId, int newQuantity);
    void updateItemQuantityForAnonymous(String sessionId, UUID itemId, int newQuantity);
}
