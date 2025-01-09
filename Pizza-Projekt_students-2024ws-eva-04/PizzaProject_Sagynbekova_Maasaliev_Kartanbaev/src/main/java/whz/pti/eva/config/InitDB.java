package whz.pti.eva.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import whz.pti.eva.domain.Customer;
import whz.pti.eva.domain.DeliveryAddress;
import whz.pti.eva.domain.Pizza;
import whz.pti.eva.domain.enums.Role;
import whz.pti.eva.repository.CustomerRepository;
import whz.pti.eva.repository.DeliveryAddressRepository;
import whz.pti.eva.service.PizzaService;

@Configuration
public class InitDB {

    @Bean
    CommandLineRunner initData(PizzaService pizzaService, CustomerRepository customerRepository, DeliveryAddressRepository deliveryAddressRepository, PasswordEncoder passwordEncoder) {
        return args -> {
        	
        	DeliveryAddress deliveryAddress = new DeliveryAddress();
        	
        	deliveryAddress.setHouseNumber("123");
        	deliveryAddress.setStreet("test");
        	deliveryAddress.setTown("test");
        	deliveryAddress.setTown("test");
        	
        	DeliveryAddress saved = deliveryAddressRepository.save(deliveryAddress);

            Customer admin = new Customer();
            admin.setFirstName("Chingiz");
            admin.setLastName("Kartanbaev");
            admin.setLoginName("admin");
            admin.setAddresses(saved);
            admin.setPasswordHash(passwordEncoder.encode("a1"));
            admin.setRole(Role.ADMIN);
            customerRepository.save(admin);
            
            Customer user = new Customer();
            user.setFirstName("Aiana");
            user.setLastName("Sagynbekova");
            user.setLoginName("bnutz");
            user.setAddresses(saved);
            user.setPasswordHash(passwordEncoder.encode("n1"));
            user.setRole(Role.USER);
            customerRepository.save(user);
            
            Pizza pepperoni = new Pizza();
            pepperoni.setName("Pepperoni");
            pepperoni.setPriceSmall(BigDecimal.valueOf(14));
            pepperoni.setPriceMedium(BigDecimal.valueOf(20));
            pepperoni.setPriceLarge(BigDecimal.valueOf(26));            
            pizzaService.save(pepperoni);
            
            Pizza margherita = new Pizza();
            margherita.setName("Margherita");
            margherita.setPriceSmall(BigDecimal.valueOf(10));
            margherita.setPriceMedium(BigDecimal.valueOf(15));
            margherita.setPriceLarge(BigDecimal.valueOf(20));         
            pizzaService.save(margherita);
            
            Pizza capricciosa = new Pizza();
            capricciosa.setName("Capricciosa");
            capricciosa.setPriceSmall(BigDecimal.valueOf(15));
            capricciosa.setPriceMedium(BigDecimal.valueOf(21));
            capricciosa.setPriceLarge(BigDecimal.valueOf(27));      
            pizzaService.save(capricciosa);
        };
    }
}