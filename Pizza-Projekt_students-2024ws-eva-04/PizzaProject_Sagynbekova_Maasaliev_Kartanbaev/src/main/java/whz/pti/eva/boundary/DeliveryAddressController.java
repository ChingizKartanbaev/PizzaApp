//package whz.pti.eva.boundary;
//
//import java.util.List;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import whz.pti.eva.domain.DeliveryAddress;
//import whz.pti.eva.service.DeliveryAddressService;
//
//@Controller
//@RequestMapping("/deliveryAddress")
//public class DeliveryAddressController {
//	
//	private DeliveryAddressService deliveryAddressService;
//	
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("deliveryAddress", new DeliveryAddress());
//        return "customer/create_customer";
//    }
//
//    @PostMapping("/create")
//    public String createDeliveryAddress(@ModelAttribute DeliveryAddress customer) {
//    	deliveryAddressService.createDeliveryAddress(customer);
//        return "redirect:/deliveryAddress";
//    }
//
//    @GetMapping
//    public String getAllDeliveryAddress(Model model) {
//        List<DeliveryAddress> customers = deliveryAddressService.getAllDeliveryAddress();
//        model.addAttribute("customers", customers);
//        return "customer/list_customers";
//    }
//
//    
//    @GetMapping("/edit/{id}")
//    public String showEditForm(Model model, @PathVariable("id") Long id) {
//    	DeliveryAddress deliveryAddress = deliveryAddressService.getDeliveryAddressById(id);
//        if (deliveryAddress != null) {
//            model.addAttribute("deliveryAddress", deliveryAddress);
//            return "customer/edit_customer";
//        } else {
//            return "redirect:/deliveryAddress";
//        }
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updateDeliveryAddress(@PathVariable("id") Long id, @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress) {
//    	deliveryAddressService.updateDeliveryAddress(id, deliveryAddress);
//        return "redirect:/deliveryAddress";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteDeliveryAddress(@PathVariable("id") Long id) {
//    	deliveryAddressService.deleteDeliveryAddress(id);
//        return "redirect:/customers";
//    }
//	
//}
