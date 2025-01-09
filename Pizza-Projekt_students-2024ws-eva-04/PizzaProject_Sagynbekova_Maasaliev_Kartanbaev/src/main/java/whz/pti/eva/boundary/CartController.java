package whz.pti.eva.boundary;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import whz.pti.eva.domain.Cart;
import whz.pti.eva.domain.CurrentUser;
import whz.pti.eva.service.CartService;
import whz.pti.eva.service.ItemService;

import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;

    public CartController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }

   
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        String sessionId = (String) session.getAttribute("ANONYMOUS_CART_ID");
        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            cart = cartService.getCartByCustomer(currentUser.getId());
        } else {
            if (sessionId == null) {
                sessionId = UUID.randomUUID().toString();
                session.setAttribute("ANONYMOUS_CART_ID", sessionId);
            }
            cart = cartService.getOrCreateCart(sessionId);
        }

        model.addAttribute("cart", cart);
        return "cart/view_cart";
    }



    @PostMapping("/add")
    public String addItemToCart(HttpSession session,
                                @RequestParam("pizzaId") Long pizzaId,
                                @RequestParam("pizzaSize") String pizzaSize,
                                @RequestParam("quantity") int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            cartService.addItemToCart(currentUser.getId(), pizzaId, pizzaSize, quantity);
        } else {
            String sessionId = (String) session.getAttribute("ANONYMOUS_CART_ID");
            if (sessionId == null) {
                sessionId = UUID.randomUUID().toString();
                session.setAttribute("ANONYMOUS_CART_ID", sessionId);
            }
            cartService.addItemToAnonymousCart(sessionId, pizzaId, pizzaSize, quantity);
        }
        return "redirect:/pizzas";
    }


    @PostMapping("/update")
    public String updateItemQuantity(
        HttpSession session,
        @RequestParam("itemId") UUID itemId,
        @RequestParam("quantity") int quantity,
        @RequestParam(value = "cartId", required = false) String cartId) {

        if (cartId != null && cartId.matches("\\d+")) {
            // Если пользователь авторизован, cartId - это customerId
            Long customerId = Long.parseLong(cartId);
            cartService.updateItemQuantityForCustomer(customerId, itemId, quantity);
        } else {
            // Если пользователь не авторизован, используем sessionId
            String sessionId = (String) session.getAttribute("ANONYMOUS_CART_ID");
            if (sessionId == null) {
                throw new IllegalArgumentException("Session ID is missing for anonymous cart.");
            }
            cartService.updateItemQuantityForAnonymous(sessionId, itemId, quantity);
        }

        return "redirect:/cart";
    }


    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam("itemId") UUID itemId, @RequestParam("customerId") Long customerId) {
        itemService.removeItem(itemId);
        return "redirect:/cart/" + customerId;
    }

    @PostMapping("/clear")
    public String clearCart(@PathVariable("customerId") Long customerId) {
        cartService.clearCart(customerId);
        return "redirect:/cart/" + customerId;
    }
}
