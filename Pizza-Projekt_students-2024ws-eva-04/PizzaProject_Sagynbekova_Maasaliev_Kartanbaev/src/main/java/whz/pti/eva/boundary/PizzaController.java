package whz.pti.eva.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import whz.pti.eva.domain.Cart;
import whz.pti.eva.domain.CurrentUser;
import whz.pti.eva.domain.Pizza;
import whz.pti.eva.service.CartService;
import whz.pti.eva.service.PizzaService;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizza/create_pizza";
    }

    @PostMapping("/create")
    public String createPizza(@ModelAttribute Pizza pizza) {
        pizzaService.save(pizza);
        return "redirect:/pizzas";
    }

    @GetMapping
    public String getAllPizzas(HttpSession session, Model model) {
        // Получение списка всех пицц
        List<Pizza> pizzas = pizzaService.getAll();
        model.addAttribute("pizzas", pizzas);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            // Пользователь авторизован
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            model.addAttribute("currentUser", currentUser);

            // Получение корзины авторизованного пользователя
            Cart cart = cartService.getCartByCustomer(currentUser.getId());
            model.addAttribute("cart", cart);

            // Добавляем количество и сумму в модель
            model.addAttribute("cartQuantity", cart.getQuantity());
            model.addAttribute("cartTotalPrice", cart.getTotalPrice());
        } else {
            // Пользователь не авторизован (анонимный)
            String sessionId = (String) session.getAttribute("ANONYMOUS_CART_ID");
            Cart cart = null;

            if (sessionId != null) {
                cart = cartService.getCartBySessionId(sessionId).orElseThrow(() -> new IllegalArgumentException("Cart not found f"));
            }

            model.addAttribute("cart", cart);
            model.addAttribute("cartQuantity", cart != null ? cart.getQuantity() : 0);
            model.addAttribute("cartTotalPrice", cart != null ? cart.getTotalPrice() : 0);
        }

        return "pizza/list_pizzas";
    }

    
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        Pizza pizza = pizzaService.getById(id);
        if (pizza != null) {
            model.addAttribute("pizza", pizza);
            return "pizza/edit_pizza";
        } else {
            return "redirect:/pizzas";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePizza(@PathVariable("id") Long id, @ModelAttribute("pizza") Pizza pizza) {
        pizzaService.update(id, pizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/delete/{id}")
    public String deletePizza(@PathVariable("id") Long id) {
        pizzaService.deleteById(id);
        return "redirect:/pizzas";
    }
}
