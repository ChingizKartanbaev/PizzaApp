package whz.pti.eva.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import whz.pti.eva.domain.CurrentUser;
import whz.pti.eva.domain.request.CustomerAddressRequest;
import whz.pti.eva.service.CartService;
import whz.pti.eva.service.CustomerService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CurrentUser) {
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            cartService.mergeCarts((String) session.getAttribute("ANONYMOUS_CART_ID"), currentUser.getId());
        }
        return "auth/login";
    }
	
    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("customer") CustomerAddressRequest customer) {
        return "auth/registration";
    }
    
    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("customer") CustomerAddressRequest customer) {
    	customerService.create(customer);
        return "redirect:/login";
    }
    
}
