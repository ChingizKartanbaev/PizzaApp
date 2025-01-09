package whz.pti.eva.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import whz.pti.eva.domain.CurrentUser;
import whz.pti.eva.domain.Customer;
import whz.pti.eva.domain.request.CustomerAddressRequest;
import whz.pti.eva.service.CustomerService;
import whz.pti.eva.service.DeliveryAddressService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
    @Autowired
    private CustomerService customerService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new CustomerAddressRequest());
        return "customer/create_customer";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute CustomerAddressRequest customerAddressRequest) {
    	customerService.create(customerAddressRequest);
        return "redirect:/customers";
    }

    @GetMapping
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers); 
        return "customer/list_customers";
    }

    
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
    	Customer customer = customerService.getById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customer/edit_customer";
        } else {
            return "redirect:/customers";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute("customer") CustomerAddressRequest customer) {
    	customerService.updateAddress(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
    	customerService.deleteById(id);
        return "redirect:/customers";
    }
    

}
