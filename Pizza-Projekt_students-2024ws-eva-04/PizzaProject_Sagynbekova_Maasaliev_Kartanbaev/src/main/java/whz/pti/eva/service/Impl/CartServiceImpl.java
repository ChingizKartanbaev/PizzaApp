package whz.pti.eva.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whz.pti.eva.domain.Cart;
import whz.pti.eva.domain.Customer;
import whz.pti.eva.domain.Item;
import whz.pti.eva.domain.Pizza;
import whz.pti.eva.domain.enums.PizzaSize;
import whz.pti.eva.repository.CartRepository;
import whz.pti.eva.repository.CustomerRepository;
import whz.pti.eva.repository.ItemRepository;
import whz.pti.eva.repository.PizzaRepository;
import whz.pti.eva.service.CartService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final PizzaRepository pizzaRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;

    public CartServiceImpl(CartRepository cartRepository, PizzaRepository pizzaRepository, ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.pizzaRepository = pizzaRepository;
        this.itemRepository = itemRepository;
		this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Cart getCartByCustomer(Long customerId) {
        return cartRepository.findByCustomer_Id(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    Customer customer = new Customer();
                    customer.setId(customerId); 
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public void addItemToCart(Long customerId, Long pizzaId, String pizzaSize, int quantity) {
        Cart cart = getCartByCustomer(customerId);

        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found with id: " + pizzaId));

        PizzaSize size = PizzaSize.valueOf(pizzaSize.toUpperCase());

        Optional<Item> existingItem = cart.getItems().stream()
                .filter(item -> item.getPizza().getId().equals(pizzaId) && item.getPizzaSize() == size)
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
        } else {
            Item newItem = new Item(quantity, pizza, size);
            cart.addItem(newItem);
            itemRepository.save(newItem);
        }

        cart.setQuantity(cart.getTotalItems());
        cartRepository.save(cart);
    }

    @Transactional
    public void updateItemQuantity(Cart cart, UUID itemId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        if (!cart.getItems().contains(item)) {
            throw new IllegalArgumentException("Item does not belong to the specified cart");
        }

        item.setQuantity(newQuantity);
        itemRepository.save(item);

        cart.setQuantity(cart.getTotalItems());
        cartRepository.save(cart);
    }

    
    @Override
    @Transactional
    public void updateItemQuantityForCustomer(Long customerId, UUID itemId, int newQuantity) {
        Cart cart = getCartByCustomer(customerId);
        updateItemQuantity(cart, itemId, newQuantity);
    }

    @Override
    @Transactional
    public void updateItemQuantityForAnonymous(String sessionId, UUID itemId, int newQuantity) {
        Cart cart = getCartBySessionId(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for session ID: " + sessionId));
        updateItemQuantity(cart, itemId, newQuantity);
    }




    @Override
    @Transactional
    public void removeItemFromCart(Long customerId, UUID itemId) {
        Cart cart = getCartByCustomer(customerId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        cartRepository.deleteItemFromCart(cart.getId(), item.getId());

        if (item.getCarts().isEmpty()) {
            itemRepository.delete(item);
        }
    }

    @Override
    @Transactional
    public void clearCart(Long customerId) {
        Cart cart = getCartByCustomer(customerId);
        cart.clear();
        cart.setQuantity(0);
        cartRepository.save(cart);
    }

	@Override
	@Transactional
	public Cart getOrCreateCart(String sessionId) {
	    return cartRepository.findBySessionId(sessionId)
	            .orElseGet(() -> {
	                Cart cart = new Cart();
	                cart.setSessionId(sessionId);
	                cart.setCreatedAt(LocalDateTime.now());
	                return cartRepository.save(cart);
	            });
	}

	@Override
	public void mergeCarts(String sessionId, Long userId) {
		   Cart anonymousCart = cartRepository.findBySessionId(sessionId).orElse(null);
		    Cart userCart = cartRepository.findByCustomer_Id(userId)
		            .orElseGet(() -> {
		                Cart cart = new Cart();
		                cart.setCustomer(customerRepository.findById(userId).orElseThrow());
		                return cartRepository.save(cart);
		            });

		    if (anonymousCart != null) {
		        userCart.getItems().addAll(anonymousCart.getItems());
		        cartRepository.save(userCart); // Сохраняем обновлённую пользовательскую корзину
		        cartRepository.delete(anonymousCart); // Удаляем анонимную корзину
		    }
	}

	@Override
	public void cleanUpOldCarts() {
	    LocalDateTime cutoff = LocalDateTime.now().minusDays(1); // Удаляем корзины старше 1 дня
	    List<Cart> oldCarts = cartRepository.findAllByCustomerIsNullAndCreatedAtBefore(cutoff);
	    cartRepository.deleteAll(oldCarts);		
	}

	@Override
	@Transactional
	public void addItemToAnonymousCart(String sessionId, Long pizzaId, String pizzaSize, int quantity) {
	    // Получаем или создаём корзину для анонимного пользователя
	    Cart cart = getOrCreateCart(sessionId);

	    // Находим пиццу по ID
	    Pizza pizza = pizzaRepository.findById(pizzaId)
	            .orElseThrow(() -> new IllegalArgumentException("Pizza not found with id: " + pizzaId));

	    // Преобразуем размер пиццы в enum
	    PizzaSize size = PizzaSize.valueOf(pizzaSize.toUpperCase());

	    // Проверяем, есть ли уже такая пицца в корзине
	    Optional<Item> existingItem = cart.getItems().stream()
	            .filter(item -> item.getPizza().getId().equals(pizzaId) && item.getPizzaSize() == size)
	            .findFirst();

	    if (existingItem.isPresent()) {
	        // Увеличиваем количество, если такой элемент уже есть
	        existingItem.get().increaseQuantity(quantity);
	    } else {
	        // Создаём новый элемент, если такой отсутствует
	        Item newItem = new Item(quantity, pizza, size);
	        cart.addItem(newItem);
	        itemRepository.save(newItem);
	    }

	    // Обновляем общее количество элементов в корзине
	    cart.setQuantity(cart.getTotalItems());
	    cartRepository.save(cart);
	}

	@Transactional
	public Optional<Cart> getCartBySessionId(String sessionId) {
	    return cartRepository.findBySessionId(sessionId);
	}
}
