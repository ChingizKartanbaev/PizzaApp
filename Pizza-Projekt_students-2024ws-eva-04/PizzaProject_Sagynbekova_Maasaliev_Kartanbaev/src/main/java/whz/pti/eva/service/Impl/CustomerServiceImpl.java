package whz.pti.eva.service.Impl;

import java.util.List;	
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import whz.pti.eva.domain.Customer;
import whz.pti.eva.domain.DeliveryAddress;
import whz.pti.eva.domain.enums.Role;
import whz.pti.eva.domain.request.CustomerAddressRequest;
import whz.pti.eva.repository.CustomerRepository;
import whz.pti.eva.service.CustomerService;
import whz.pti.eva.service.DeliveryAddressService;

@Service
public class CustomerServiceImpl implements CustomerService {
    
	@Autowired
	private CustomerRepository customerRepository;
    @Autowired
	private DeliveryAddressService deliveryAddressService;
    @Autowired
    private PasswordEncoder passwordEncoder;


	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);	
    }

	@Override
	public Customer update(Long id, Customer customerDetails) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();            
            existingCustomer.setFirstName(customerDetails.getFirstName());
            existingCustomer.setLastName(customerDetails.getLastName());
            existingCustomer.setLoginName(customerDetails.getLoginName());
            existingCustomer.setPasswordHash(customerDetails.getPasswordHash());
            return customerRepository.save(existingCustomer);
        } else {
            return null;
        }
	}

	@Override
	public boolean deleteById(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
	}

	@Override
	public Customer create(CustomerAddressRequest customerAddressRequest) {
		Customer customer = new Customer();
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		
		deliveryAddress.setHouseNumber(customerAddressRequest.getHouseNumber());
		deliveryAddress.setStreet(customerAddressRequest.getStreet());
		deliveryAddress.setTown(customerAddressRequest.getTown());
		deliveryAddress.setPostalCode(customerAddressRequest.getPostalCode());
		customer.setAddresses(deliveryAddressService.save(deliveryAddress));
		
		return save(updateCustomerFields(customer, customerAddressRequest));
	}

	@Override
	public Customer updateAddress(Long id, CustomerAddressRequest customerAddressRequest) {
	    Customer customer = getById(id);

	    DeliveryAddress deliveryAddress = customer.getAddresses();
	    if (deliveryAddress == null) {
	        deliveryAddress = new DeliveryAddress();
	        customer.setAddresses(deliveryAddress);
	    }

	    deliveryAddress.setStreet(customerAddressRequest.getStreet());
	    deliveryAddress.setHouseNumber(customerAddressRequest.getHouseNumber());
	    deliveryAddress.setTown(customerAddressRequest.getTown());
	    deliveryAddress.setPostalCode(customerAddressRequest.getPostalCode());

	    deliveryAddressService.update(deliveryAddress.getId(), deliveryAddress);

	    return customerRepository.save(updateCustomerFields(customer, customerAddressRequest));
	}

	private Customer updateCustomerFields(Customer existingCustomer, CustomerAddressRequest customerAddressRequest) {
	    existingCustomer.setFirstName(customerAddressRequest.getFirstName());
	    existingCustomer.setLastName(customerAddressRequest.getLastName());
	    existingCustomer.setLoginName(customerAddressRequest.getLoginName());
	    if(customerAddressRequest.getPasswordHash() != null) {
	    existingCustomer.setPasswordHash(passwordEncoder.encode(customerAddressRequest.getPasswordHash()));
	    }
	    existingCustomer.setRole(Role.USER);
	    return existingCustomer;
	}

	@Override
	public Optional<Customer> findByLoginName(String loginName) {
		return customerRepository.findByLoginName(loginName);
	}
	
}
